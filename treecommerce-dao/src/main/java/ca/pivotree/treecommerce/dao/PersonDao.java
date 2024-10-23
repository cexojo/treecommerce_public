package ca.pivotree.treecommerce.dao;

import ca.pivotree.treecommerce.core.dao.TreeDao;
import ca.pivotree.treecommerce.model.Person;
import java.util.List;
import java.util.Optional;

/**
 * Created by cexojo on 04/04/2020
 */

public interface PersonDao extends TreeDao<Person> {
	List<Person> findByFirstName(String name);
	Optional<Person> findByEmail(String email);
}
