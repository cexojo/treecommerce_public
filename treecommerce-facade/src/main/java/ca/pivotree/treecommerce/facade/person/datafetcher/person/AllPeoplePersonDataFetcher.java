package ca.pivotree.treecommerce.facade.person.datafetcher.person;

import ca.pivotree.treecommerce.dao.PersonDao;
import ca.pivotree.treecommerce.model.Person;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by cexojo on 05/04/2020
 */

@ApplicationScoped
public class AllPeoplePersonDataFetcher implements DataFetcher<List<Person>> {

	@Inject
	PersonDao personDao;

	@Override
	public List<Person> get(DataFetchingEnvironment dataFetchingEnvironment) {
		return personDao.findByFirstName("");
	}
}
