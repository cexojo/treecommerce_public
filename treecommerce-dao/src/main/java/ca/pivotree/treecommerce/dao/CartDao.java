package ca.pivotree.treecommerce.dao;

import ca.pivotree.treecommerce.core.dao.TreeDao;
import ca.pivotree.treecommerce.model.Cart;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import java.util.List;
import java.util.Optional;

/**
 * Created by cexojo on 04/04/2020
 */

public interface CartDao extends TreeDao<Cart> {
	List<Cart> getLiveCartsByOwnerAndSite(Person person, Site site);
	List<Cart> getPurgeableCarts(Person person, Site site);
	Optional<Cart> getByPublicCode(String publicCode);
}
