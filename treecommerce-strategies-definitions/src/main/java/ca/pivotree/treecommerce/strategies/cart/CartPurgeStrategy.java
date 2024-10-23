package ca.pivotree.treecommerce.strategies.cart;

import ca.pivotree.treecommerce.model.Cart;
import java.util.List;

/**
 * Created by cexojo on 20/04/2020
 */

public interface CartPurgeStrategy {
	boolean isPurgeable(Cart cart);
	List<Cart> getPurgeableCarts();
}
