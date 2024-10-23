package ca.pivotree.treecommerce.facade.credential.impl;

import ca.pivotree.treecommerce.converter.cart.CartConverter;
import ca.pivotree.treecommerce.core.utils.TreeUtils;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.facade.AbstractFacade;
import ca.pivotree.treecommerce.facade.credential.CredentialFacade;
import ca.pivotree.treecommerce.facade.credential.utils.TreePrivateKeyUtils;
import ca.pivotree.treecommerce.model.Credential;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.service.credential.CredentialService;
import ca.pivotree.treecommerce.service.credential.exception.ExpiredPasswordCredentialsException;
import ca.pivotree.treecommerce.service.credential.exception.InvalidCredentialsException;
import ca.pivotree.treecommerce.service.credential.exception.MalformedCredentialsException;
import ca.pivotree.treecommerce.service.jwt.JwtService;
import ca.pivotree.treecommerce.service.person.PersonService;
import java.security.PrivateKey;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Created by cexojo on 07/04/2020
 */

@ApplicationScoped
@Slf4j
public class CredentialFacadeImpl extends AbstractFacade implements CredentialFacade {
	private static final String ERR_MESSAGE_NO_IMPERSONATED_USER = "No impersonated user";
	private static final String ERR_MESSAGE_NO_CREDENTIAL_FOUND = "No credential could be found";

	@ConfigProperty(name = "mp.jwt.verify.privatekey.location")
	String privateKeyId;

	@Inject
	CredentialService credentialService;

	@Inject
	PersonService personService;

	@Inject
	JwtService jwtService;

	@Inject
	CartConverter cartConverter;

	private PrivateKey privateKey;

	@Override
	public String doLogin(String username, String password, Site site) {
		Credential credential = getCredential(username, password, site);

		return getToken(credential, null, privateKey, privateKeyId);
	}

	@Override
	public String doImpersonate(String username, String password, String usernameToImpersonate, Site site) {
		Credential credential = getCredential(username, password, null);
		Credential impersonatedCredential = getCredential(usernameToImpersonate, site);

		return getToken(credential, impersonatedCredential, privateKey, privateKeyId);
	}

	@Override
	public Optional<String> isImpersonating() {
		Optional<String> principalIdImpersonated = jwtService.getUserIdBeingImpersonated();
		TreeValidationUtils.throwIfTrue(principalIdImpersonated.isEmpty(), ERR_MESSAGE_NO_IMPERSONATED_USER);

		Optional<Credential> credentialImpersonated = credentialService.getByPrincipalId(principalIdImpersonated.get());
		TreeValidationUtils.throwIfTrue(credentialImpersonated.isEmpty(), ERR_MESSAGE_NO_CREDENTIAL_FOUND);

		return Optional.of(credentialImpersonated.get().getUserName());
	}

	/**
	 * Adds a list of roles to the credential of the given username.
	 *
	 * @param username the username
	 * @param roles    the list of roles to add
	 */
	@Override
	public void addRoles(String username, String roles) {
		log.debug("Adding roles '{}' for user '{}'.", roles, username);

		Optional<Credential> credential = credentialService.getByUsername(username);
		TreeValidationUtils.throwIfTrue(credential.isEmpty(), ERR_MESSAGE_NO_CREDENTIAL_FOUND);

		credentialService.addRoles(credential.get(), TreeUtils.splitByComma(roles));
	}

	/**
	 * Removes a list of roles from the credential of the given username.
	 *
	 * @param username the username
	 * @param roles    the list of roles to remove, separated by comma
	 */
	@Override
	public void removeRoles(String username, String roles) {
		log.debug("Removing roles '{}' for user '{}'.", roles, username);

		Optional<Credential> credential = credentialService.getByUsername(username);
		TreeValidationUtils.throwIfTrue(credential.isEmpty(), ERR_MESSAGE_NO_CREDENTIAL_FOUND);

		credentialService.removeRoles(credential.get(), TreeUtils.splitByComma(roles));
	}

	/**
	 * Adds a list of roles to the credential of the given username.
	 *
	 * @param username the username
	 * @param sites    the list of sites to add, separated by comma
	 */
	@Override
	public void addSites(String username, String sites) {
		Optional<Credential> credential = credentialService.getByUsername(username);
		TreeValidationUtils.throwIfTrue(credential.isEmpty(), ERR_MESSAGE_NO_CREDENTIAL_FOUND);

		credentialService.addSites(credential.get(), TreeUtils.splitByComma(sites));
	}

	private Credential getCredential(String username, String password, Site site) {
		Optional<Credential> credential = credentialService.getByUsernameAndPassword(username, password);
		validateCredentialAndSite(credential, username, site);

		Optional<Person> person = personService.getByPk(credential.get().getPrincipalId());
		TreeValidationUtils.throwIfTrue(person.isEmpty(), new InvalidCredentialsException(username));

		return credential.get();
	}

	private Credential getCredential(String username, Site site) {
		Optional<Credential> credential = credentialService.getByUsername(username);
		validateCredentialAndSite(credential, username, site);

		Optional<Person> person = personService.getByPk(credential.get().getPrincipalId());
		TreeValidationUtils.throwIfTrue(person.isEmpty(), new InvalidCredentialsException(username));

		return credential.get();
	}

	private void validateCredentialAndSite(Optional<Credential> credential, String username, Site site) {
		TreeValidationUtils.throwIfFalse(credential.isPresent(), new InvalidCredentialsException(username));
		TreeValidationUtils.throwIfTrue(credentialService.isExpired(credential.get()), new ExpiredPasswordCredentialsException(username));
		TreeValidationUtils.throwIfTrue(CollectionUtils.isEmpty(credential.get().getGroups()), new MalformedCredentialsException(username));
		TreeValidationUtils.throwIfFalse(credentialService.isGrantedForSite(credential.get(), site), new InvalidCredentialsException(username, site));
	}

	@PostConstruct
	public void init() throws Exception {
		privateKey = TreePrivateKeyUtils.readPrivateKey(privateKeyId);
	}
}
