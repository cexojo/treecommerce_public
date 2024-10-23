package ca.pivotree.treecommerce.facade.person.datafetcher.person;

import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.dao.PersonDao;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.definitions.PersonModelDefinition;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by cexojo on 05/04/2020
 */

@ApplicationScoped
public class FindByEmailPersonDataFetcher implements DataFetcher<Person> {

	private static final String ERR_MESSAGE_EMAIL_MISSING = "Email cannot be missing";
	private static final String ERR_MESSAGE_EMAIL_BLANK = "Email cannot be blank";

	@Inject
	PersonDao personDao;

	@Override
	public Person get(DataFetchingEnvironment dataFetchingEnvironment) {
		TreeValidationUtils
				.throwIfFalse(dataFetchingEnvironment.containsArgument(PersonModelDefinition._EMAIL),
						ERR_MESSAGE_EMAIL_MISSING);

		String email = dataFetchingEnvironment.getArgument(PersonModelDefinition._EMAIL);
		TreeValidationUtils.throwIfBlank(email, ERR_MESSAGE_EMAIL_BLANK);

		return personDao.findByEmail(email).orElse(null);
	}
}
