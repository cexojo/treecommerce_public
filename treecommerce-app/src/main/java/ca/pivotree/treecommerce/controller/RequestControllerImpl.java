package ca.pivotree.treecommerce.controller;

import ca.pivotree.treecommerce.core.controller.AbstractController;
import ca.pivotree.treecommerce.core.controller.RequestController;
import ca.pivotree.treecommerce.core.exceptions.TreeException;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.service.jwt.JwtService;
import ca.pivotree.treecommerce.service.person.PersonService;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cexojo on 18/04/2020
 */
@RequestScoped
public class RequestControllerImpl extends AbstractController implements RequestController {
	@Inject
	JwtService jwtService;

	@Inject
	PersonService personService;

	private String fields;
	private Locale locale;
	private Site site;
	private Person person;
	private List<String> userGroups;
	private String effectiveUserPk;

	@Override
	public Locale getLocale() {
		return this.locale;
	}

	@Override
	public void setLocale(String locale) {
		if (StringUtils.isNotBlank(locale)) {
			this.locale = new Locale(locale);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void setSite(Object site) {
		this.site = (Site) site;
	}

	@Override
	public Person getPerson() {
		if (Objects.isNull(person)) {
			Optional<Person> optionalPerson = personService.getByPk(jwtService.getEffectiveUserPk());
			if (optionalPerson.isEmpty()) {
				throw new TreeException("Unable to gather the current person");
			}

			person = optionalPerson.get();
		}

		return person;
	}

	@Override
	public List<String> getUserGroups() {
		if (Objects.isNull(userGroups)) {
			userGroups = jwtService.getUserGroups();
		}

		return userGroups;
	}

	@Override
	public String getEffectiveUserPk() {
		if (Objects.isNull(effectiveUserPk)) {
			effectiveUserPk = jwtService.getEffectiveUserPk();
		}

		return effectiveUserPk;
	}

	@Override
	public String getFields() {
		return this.fields;
	}

	@Override
	public void setFields(String fields) {
		this.fields = fields;
	}
}
