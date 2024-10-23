package ca.pivotree.treecommerce.converter.cart;

import ca.pivotree.treecommerce.converter.AbstractModelConverter;
import ca.pivotree.treecommerce.core.exceptions.model.TreeUnknownModelFieldException;
import ca.pivotree.treecommerce.dao.SiteDao;
import ca.pivotree.treecommerce.model.Cart;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.model.definitions.CartModelDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.bson.Document;

/**
 * Created by cexojo on 21/04/2020
 */
@ApplicationScoped
public class CartConverter extends AbstractModelConverter<Cart> {
	@Inject
	AbstractModelConverter<Site> siteConverter;

	@Inject
	SiteDao siteDao;

	@Override
	public Cart convertToModel(Document source, Cart target, String... fields) {
		return null;
	}

	@Override
	public Object getDtoFieldValue(Cart model, String field, String format) {
		switch (field) {
			case CartModelDefinition._CODE:
				return model.getCode();
			case CartModelDefinition._SITE:
				return siteConverter.toDto(siteDao.getByPk(model.getSite()).get(), format);
			case CartModelDefinition._PUBLICCODE:
				return model.getPublicCode();
			case CartModelDefinition._OWNERID:
				return model.getOwnerId().toString();
			case CartModelDefinition._STATUS:
				return model.getStatus();
			default:
				throw new TreeUnknownModelFieldException(field, CartModelDefinition.__TYPE);
		}
	}

	@Override
	public Object getModelFieldValue(Cart source, String field) {
		return null;
	}
}
