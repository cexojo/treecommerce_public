package ca.pivotree.treecommerce.service.cart.impl;

import ca.pivotree.treecommerce.core.model.mongo.attribs.LocalisedString;
import ca.pivotree.treecommerce.dao.CartDao;
import ca.pivotree.treecommerce.model.Cart;
import ca.pivotree.treecommerce.model.CartLine;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.model.definitions.CartModelDefinition;
import ca.pivotree.treecommerce.service.cart.CartService;
import com.google.common.collect.ImmutableList;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.kogito.rules.KieRuntimeBuilder;

/**
 * Created by cexojo on 12/04/2020
 */
@Slf4j
@ApplicationScoped
public class CartServiceImpl implements CartService {
	@Inject
	CartDao cartDao;

	@Inject
	KieRuntimeBuilder runtimeBuilder;

	private InternalKnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase();

	public static byte[] createKJar(KieServices ks,
			ReleaseId releaseId,
			Resource pom,
			Resource... resources) {
		KieFileSystem kfs = ks.newKieFileSystem();
		if (pom != null) {
			kfs.write(pom);
		} else {
			kfs.generateAndWritePomXML(releaseId);
		}
		for (int i = 0; i < resources.length; i++) {
			if (resources[i] != null) {
				kfs.write(resources[i]);
			}
		}
		ks.newKieBuilder(kfs).buildAll();
		InternalKieModule kieModule = (InternalKieModule) ks.getRepository()
				.getKieModule(releaseId);
		byte[] jar = kieModule.getBytes();
		return jar;
	}

	public static byte[] createKJar(ReleaseId releaseId, List<String> drls) {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.generateAndWritePomXML(releaseId);
		drls.forEach(drl -> kfs.write("src/main/resources/org/foo/bar/" + drl.hashCode() + ".drl", drl));
		KieBuilder kb = kieServices.newKieBuilder(kfs).buildAll();
		if (kb.getResults().hasMessages(Message.Level.ERROR)) {
			for (Message result : kb.getResults().getMessages()) {
				System.out.println(result.getText());
			}
			return null;
		}
		InternalKieModule kieModule = (InternalKieModule) kieServices.getRepository().getKieModule(releaseId);
		byte[] jar = kieModule.getBytes();
		return jar;
	}

	@Override
	public List<Cart> getLiveCartsByOwnerAndSite(Person person, Site site) {
		return cartDao.getLiveCartsByOwnerAndSite(person, site);
	}

	@Override
	public Optional<Cart> getByPublicCode(String publicCode) {
		return cartDao.getByPublicCode(publicCode);
	}

	@Override
	public Cart createLiveCart(Person person, Site site) {
		log.debug("Creating new {} for user {}.", CartModelDefinition.__TYPE, person.getPk());

		Cart cart = new Cart();
		cart.setStatus(CartModelDefinition.__STATUS_LIVE);
		cart.setCreationTime(LocalDateTime.now());
		cart.setTotal(BigDecimal.ZERO);
		cart.setCartName(new LocalisedString("es", "Hola", "fr", "Bonsuá"));
		cart.setLines(ImmutableList.of(new CartLine()));
		cart.setSite(site.getPk());
		cart.setOwnerId(person.getPk());
		cart.setPublicCode(UUID.randomUUID().toString());
		cartDao.save(cart);

		return cart;
	}


	public void testLoadOrderAfterRuleRemoval() {
		final String drl2 = "package foo\n" +
				"rule R1 when\n" +
				"   $m : String()\n" +
				"then\n" +
				"   System.out.println(123)\n" +
				"end\n";

		loadRule(drl2);

		final KieServices ks = KieServices.Factory.get();
		final KieContainer kieContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
		final KieSession ksession = kieContainer.newKieSession();

		int aaa = ksession.fireAllRules();
	}

	private void loadRule(final String rule) {
		String prefix = getPrefix();
		prefix += rule;

		final KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		builder.add(ResourceFactory.newReaderResource(new StringReader(prefix)), ResourceType.DRL);
		final Collection<KiePackage> pkgs = this.buildKnowledge(builder);
		this.addKnowledgeToBase(pkgs);
	}

	private void addKnowledgeToBase(final Collection<KiePackage> pkgs) {
		this.base.addPackages(pkgs);
	}

	private Collection<KiePackage> buildKnowledge(final KnowledgeBuilder builder) {
		if (builder.hasErrors()) {
			throw new RuntimeException();
		}
		return builder.getKnowledgePackages();
	}

	private String getPrefix() {
		return "package ca.pivotree.treecommerce \n" +
				"import java.util.Map;\n" +
				"import java.util.HashMap;\n" +
				"import org.slf4j.Logger;\n" +
				"import java.util.Date;\n" +

				"declare Counter \n" +
				"@role(event)\n" +
				" id : int \n" +
				"\n" +
				"end\n\n";
	}

	@Override
	public void remove(Cart cart) {
		log.debug("Removing {} {}." + CartModelDefinition.__TYPE, cart.getPublicCode());
		cartDao.delete(cart);
	}
}
