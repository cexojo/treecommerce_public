package ca.pivotree.treecommerce.strategies.cart.impl;

import ca.pivotree.treecommerce.dao.CartDao;
import ca.pivotree.treecommerce.model.Cart;
import ca.pivotree.treecommerce.model.definitions.CartModelDefinition;
import ca.pivotree.treecommerce.strategies.cart.CartPurgeStrategy;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by cexojo on 20/04/2020
 */
@ApplicationScoped
public class CartPurgeStrategyImpl implements CartPurgeStrategy {
	private static final int MONTHS_OLD_TO_PURGE = 12;

	@Inject
	CartDao cartDao;

	@Override
	public boolean isPurgeable(Cart cart) {
		return !cart.isCheckedOut() && CartModelDefinition.__STATUS_LIVE.equals(cart.getStatus()) && cart.getModificationTime().plusMonths(MONTHS_OLD_TO_PURGE)
				.isAfter(
						LocalDateTime.now());
	}

	@Override
	public List<Cart> getPurgeableCarts() {
		return Collections.emptyList();
	}
}
