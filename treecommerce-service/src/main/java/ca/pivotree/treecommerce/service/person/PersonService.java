package ca.pivotree.treecommerce.service.person;

import ca.pivotree.treecommerce.model.Person;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by cexojo on 07/04/2020
 */

public interface PersonService {
	Optional<Person> getByPk(String pk);
	Optional<Person> getByEmail(String email);
	Person create(String firstName, String lastName, String email, LocalDate birthDate, String username, String password);
}
