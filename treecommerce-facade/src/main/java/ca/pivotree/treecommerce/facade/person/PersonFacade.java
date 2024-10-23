package ca.pivotree.treecommerce.facade.person;

import ca.pivotree.treecommerce.model.Person;
import java.time.LocalDate;

/**
 * Created by cexojo on 12/04/2020
 */

public interface PersonFacade {
	Person create(String firstName, String lastName, String email, LocalDate birthDate, String username, String password);
}
