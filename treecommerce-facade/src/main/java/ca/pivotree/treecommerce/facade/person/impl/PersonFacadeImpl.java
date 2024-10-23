package ca.pivotree.treecommerce.facade.person.impl;

import ca.pivotree.treecommerce.core.exceptions.person.TreeInvalidPersonDataException;
import ca.pivotree.treecommerce.facade.person.PersonFacade;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.service.person.PersonService;
import java.time.LocalDate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by cexojo on 20/04/2020
 */

@ApplicationScoped
public class PersonFacadeImpl implements PersonFacade {
	@Inject
	PersonService personService;

	@Override
	public Person create(String firstName, String lastName, String email, LocalDate birthDate, String username, String password) {
		// Verify there's no other person with the same email
		if (personService.getByEmail(email).isPresent()) {
			throw new TreeInvalidPersonDataException();
		}

		return personService.create(firstName, lastName, email, birthDate, username, password);
	}
}
