package ca.pivotree.treecommerce.service.person.impl;

import ca.pivotree.treecommerce.dao.PersonDao;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.service.credential.CredentialService;
import ca.pivotree.treecommerce.service.person.PersonService;
import java.time.LocalDate;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Created by cexojo on 07/04/2020
 */

@ApplicationScoped
public class PersonServiceImpl implements PersonService {
	@Inject
	PersonDao personDao;

	@Inject
	CredentialService credentialService;

	@Override
	public Optional<Person> getByPk(String pk) {
		return personDao.getByPk(pk);
	}

	@Override
	public Optional<Person> getByEmail(String email) {
		return personDao.findByEmail(email);
	}

	@Override
	public Person create(String firstName, String lastName, String email, LocalDate birthDate, String username, String password) {
		Person person = Person.builder().firstName(firstName).lastName(lastName).birthDate(birthDate).email(email).build();
		personDao.save(person);

		if (ObjectUtils.allNotNull(username, password)) {
			credentialService.create(person, username, password);
		}

		return person;
	}
}
