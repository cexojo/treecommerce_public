package ca.pivotree.treecommerce.dao.impl;

import ca.pivotree.treecommerce.core.dao.mongo.AbstractMongoDao;
import ca.pivotree.treecommerce.dao.PersonDao;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.definitions.PersonModelDefinition;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by cexojo on 30/03/2020
 */

@SuppressWarnings({"unused"})
@ApplicationScoped
public class PersonDaoImpl extends AbstractMongoDao<Person> implements PersonDao {
	public PersonDaoImpl() {
		super(Person::new);
	}

	public List<Person> findByFirstName(String name) {
		return find(PersonModelDefinition._FIRSTNAME, name).list();
	}

	public Optional<Person> findByEmail(String email) {
		return find(PersonModelDefinition._EMAIL, email).firstResultOptional();
	}

	public Optional<Person> findByPk(String pk) {
		return find(PersonModelDefinition._PK, pk).firstResultOptional();
	}
}