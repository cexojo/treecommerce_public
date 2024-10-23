package ca.pivotree.treecommerce.facade.cart;

import ca.pivotree.treecommerce.model.Cart;
import java.util.Optional;

/**
 * Created by cexojo on 12/04/2020
 */

public interface CartFacade {
	Cart createLiveCart();
	Optional<Cart> getCartByPublicCode(String publicCode);
	Cart getLiveCart();
}
