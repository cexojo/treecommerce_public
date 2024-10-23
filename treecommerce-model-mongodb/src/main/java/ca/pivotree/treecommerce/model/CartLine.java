package ca.pivotree.treecommerce.model;

import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.model.definitions.CartLineModelDefinition;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
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
@Schema(name = "Cart", description = "POJO with the representation of a cart/order.")
public class CartLine extends AbstractMongoModel {
	private static final CartLineModelDefinition DEFINITION = new CartLineModelDefinition();

	@NotNull
	@Schema(description = "Line number", required = true)
	private int lineNumber;

	@NotNull
	@Schema(description = "Price before discounts", required = true)
	private BigDecimal priceBeforeDiscounts;

	@Override
	public CartLineModelDefinition getDefinition() {
		return DEFINITION;
	}
}