package ca.pivotree.treecommerce.core.model;

import ca.pivotree.treecommerce.core.model.definition.AbstractModelDefinition;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by cexojo on 22/04/2020
 */

public interface TreeModel {
	@JsonIgnore
	AbstractModelDefinition getDefinition();
}
