package ca.pivotree.treecommerce.dao.impl;

import ca.pivotree.treecommerce.core.dao.mongo.AbstractMongoDao;
import ca.pivotree.treecommerce.core.exceptions.model.TreeSaveModelException;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.dao.CartDao;
import ca.pivotree.treecommerce.model.Cart;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.model.definitions.CartModelDefinition;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by cexojo on 12/04/2020
 */

@SuppressWarnings({"unused"})
@ApplicationScoped
public class CartDaoImpl extends AbstractMongoDao<Cart> implements CartDao {
	private static final String ERR_MESSAGE_OWNER_NULL = "The owner cannot be null";
	private static final String ERR_MESSAGE_SITE_NULL = "The site cannot be null";

	private static final String QUERY_BY_OWNER_AND_SITE_AND_STATUS =
			CartModelDefinition._OWNERID + " = :" + CartModelDefinition._OWNERID + " and " +
					CartModelDefinition._SITE + " = :" + CartModelDefinition._SITE + " and " +
					CartModelDefinition._STATUS + " = :" + CartModelDefinition._STATUS;

	private static final String QUERY_BY_PUBLIC_CODE = CartModelDefinition._PUBLICCODE + " = :" + CartModelDefinition._PUBLICCODE;

	public CartDaoImpl() {
		super(Cart::new);
	}

	@Override
	public List<Cart> getLiveCartsByOwnerAndSite(Person person, Site site) {
		TreeValidationUtils.throwIfNull(person, ERR_MESSAGE_OWNER_NULL);
		TreeValidationUtils.throwIfNull(site, ERR_MESSAGE_SITE_NULL);

		return find(QUERY_BY_OWNER_AND_SITE_AND_STATUS,
				ImmutableMap
						.of(CartModelDefinition._OWNERID, person.getPk(),
								CartModelDefinition._SITE, site.getPk(),
								CartModelDefinition._STATUS, CartModelDefinition.__STATUS_LIVE)).list();
	}

	@Override
	public List<Cart> getPurgeableCarts(Person person, Site site) {
		return null;
	}

	@Override
	public Optional<Cart> getByPublicCode(String publicCode) {
		return queryOne(QUERY_BY_PUBLIC_CODE, ImmutableMap.of(CartModelDefinition._PUBLICCODE, publicCode));
	}

	@Override
	public void onSave(Cart cart) throws TreeSaveModelException {
		TreeValidationUtils.throwIfNull(cart.getOwnerId(), ERR_MESSAGE_OWNER_NULL);
		TreeValidationUtils.throwIfNull(cart.getSite(), ERR_MESSAGE_SITE_NULL);
	}
}
