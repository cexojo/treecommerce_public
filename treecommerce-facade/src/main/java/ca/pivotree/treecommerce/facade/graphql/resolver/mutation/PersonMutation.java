package ca.pivotree.treecommerce.facade.graphql.resolver.mutation;

import ca.pivotree.treecommerce.model.Person;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

/**
 * Created by cexojo on 05/04/2020
 */

public class PersonMutation implements GraphQLMutationResolver {

	//@Inject
	//PersonDao personDao;

	public Person createPerson(String firstName, String lastName, String email) {
		Person person = new Person();
		//personDao.save(person);

		return person;
	}
}