package ca.pivotree.treecommerce.model;

import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.core.model.mongo.attribs.LocalisedString;
import ca.pivotree.treecommerce.model.definitions.ProductModelDefinition;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Created by cexojo on 30/03/2020
 */

@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "ptc_product")
@Schema(name = "Product", description = "POJO with the representation of a product.")
public class Product extends AbstractMongoModel {
	private static final ProductModelDefinition DEFINITION = new ProductModelDefinition();

	@NotNull
	@Schema(description = "Stock keeping unit of the product", required = true, example = "123123123")
	private String sku;

	@NotNull
	@Schema(description = "Description of the product", required = true, example = "Brand new product!")
	private LocalisedString description;

	@Override
	public ProductModelDefinition getDefinition() {
		return DEFINITION;
	}
}