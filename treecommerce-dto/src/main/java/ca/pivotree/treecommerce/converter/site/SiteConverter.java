package ca.pivotree.treecommerce.converter.site;

import ca.pivotree.treecommerce.converter.AbstractModelConverter;
import ca.pivotree.treecommerce.core.exceptions.model.TreeUnknownModelFieldException;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.model.definitions.CartModelDefinition;
import ca.pivotree.treecommerce.model.definitions.SiteModelDefinition;
import javax.enterprise.context.ApplicationScoped;
import org.bson.Document;

/**
 * Created by cexojo on 21/04/2020
 */
@ApplicationScoped
public class SiteConverter extends AbstractModelConverter<Site> {
	@Override
	public Site convertToModel(Document source, Site target, String... fields) {
		return null;
	}

	@Override
	public Object getDtoFieldValue(Site model, String field, String format) {
		switch (field) {
			case SiteModelDefinition._UID:
				return model.getUid();
			case SiteModelDefinition._DESCRIPTION:
				return model.getDescription();
			default:
				throw new TreeUnknownModelFieldException(field, CartModelDefinition.__TYPE);
		}
	}

	@Override
	public Object getModelFieldValue(Site source, String field) {
		return null;
	}
}
