package ca.pivotree.treecommerce.facade.person.datafetcher.person;

import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.definitions.CredentialModelDefinition;
import ca.pivotree.treecommerce.model.definitions.PersonModelDefinition;
import ca.pivotree.treecommerce.service.person.PersonService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.time.LocalDate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by cexojo on 05/04/2020
 */

@ApplicationScoped
public class CreatePersonDataFetcher implements DataFetcher<Person> {
	@Inject
	PersonService personService;

	@Override
	public Person get(DataFetchingEnvironment dataFetchingEnvironment) {
		TreeValidationUtils.throwIfFalse(dataFetchingEnvironment.containsArgument(PersonModelDefinition._FIRSTNAME), "");
		TreeValidationUtils.throwIfFalse(dataFetchingEnvironment.containsArgument(PersonModelDefinition._LASTNAME), "");
		TreeValidationUtils.throwIfFalse(dataFetchingEnvironment.containsArgument(PersonModelDefinition._EMAIL), "");
		TreeValidationUtils.throwIfFalse(dataFetchingEnvironment.containsArgument(PersonModelDefinition._BIRTHDATE), "");

		String firstName = dataFetchingEnvironment.getArgument(PersonModelDefinition._FIRSTNAME);
		String lastName = dataFetchingEnvironment.getArgument(PersonModelDefinition._LASTNAME);
		LocalDate birthDate = dataFetchingEnvironment.getArgument(PersonModelDefinition._BIRTHDATE);
		String email = dataFetchingEnvironment.getArgument(PersonModelDefinition._EMAIL);
		String username = dataFetchingEnvironment.getArgument(CredentialModelDefinition._USERNAME);
		String password = dataFetchingEnvironment.getArgument(CredentialModelDefinition._PASSWORD);

		return personService.create(firstName, lastName, email, birthDate, username, password);
	}
}
