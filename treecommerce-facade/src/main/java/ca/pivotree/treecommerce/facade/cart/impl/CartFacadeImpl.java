package ca.pivotree.treecommerce.facade.cart.impl;

import ca.pivotree.treecommerce.facade.AbstractFacade;
import ca.pivotree.treecommerce.facade.cart.CartFacade;
import ca.pivotree.treecommerce.model.Cart;
import ca.pivotree.treecommerce.service.cart.CartService;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Created by cexojo on 12/04/2020
 */
@Slf4j
@ApplicationScoped
public class CartFacadeImpl extends AbstractFacade implements CartFacade {
	@Inject
	CartService cartService;

	@Override
	public Cart createLiveCart() {
		// Delete all the existing live carts
		List<Cart> liveCarts = cartService.getLiveCartsByOwnerAndSite(getPerson(), getSite());
		for (Cart cart : liveCarts) {
			cartService.remove(cart);
		}

		// Create a new live cart
		return cartService.createLiveCart(getPerson(), getSite());
	}

	@Override
	public Optional<Cart> getCartByPublicCode(String publicCode) {
		return cartService.getByPublicCode(publicCode);
	}

	@Override
	public Cart getLiveCart() {
		List<Cart> liveCart = cartService.getLiveCartsByOwnerAndSite(getPerson(), getSite());
		if (CollectionUtils.isEmpty(liveCart)) {
			return cartService.createLiveCart(getPerson(), getSite());
		} else {
			return liveCart.get(0);
		}
	}
}
