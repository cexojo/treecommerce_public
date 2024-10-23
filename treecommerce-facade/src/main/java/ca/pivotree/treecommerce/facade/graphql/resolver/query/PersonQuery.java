package ca.pivotree.treecommerce.facade.graphql.resolver.query;

import ca.pivotree.treecommerce.model.Person;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import java.util.List;
import java.util.Optional;

/**
 * Created by cexojo on 05/04/2020
 */


public class PersonQuery implements GraphQLQueryResolver {

	//@Inject
	//PersonDao personDao;


	public List<Person> getPeople(final String firstName) {
		return null;//personDao.findByFirstName(firstName);
	}

	public Optional<Person> getPerson(final String email) {
		return null;/*personDao.findByEmail(email);*/
	}
}