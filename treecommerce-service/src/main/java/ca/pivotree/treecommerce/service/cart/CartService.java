package ca.pivotree.treecommerce.service.cart;

import ca.pivotree.treecommerce.model.Cart;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import java.util.List;
import java.util.Optional;

/**
 * Created by cexojo on 12/04/2020
 */

public interface CartService {
	List<Cart> getLiveCartsByOwnerAndSite(Person person, Site site);
	Optional<Cart> getByPublicCode(String publicCode);
	Cart createLiveCart(Person person, Site site);
	void remove(Cart cart);
}
