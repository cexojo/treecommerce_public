package ca.pivotree.treecommerce.model;

import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.core.model.mongo.attribs.LocalisedString;
import ca.pivotree.treecommerce.model.definitions.CartModelDefinition;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Created by cexojo on 30/03/2020
 */

@RegisterForReflection
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "ptc_cart")
@Schema(name = "Cart", description = "POJO with the representation of a cart/order.")
public class Cart extends AbstractMongoModel {
	private static final CartModelDefinition DEFINITION = new CartModelDefinition();

	@NotNull
	@Schema(description = "Owner of the cart", required = true)
	private String ownerId;

	@NotNull
	@Schema(description = "Code of the cart", required = true, example = "ABCDEF0123456")
	private String code;

	@NotNull
	@Schema(description = "Code of the cart for public usage", required = true, example = "ABCDEF0123456")
	private String publicCode;

	@NotNull
	@Schema(description = "Site of the cart", required = true)
	private String site;

	@Schema(description = "Saving time of the cart")
	private LocalDateTime userSavingTime;

	@Schema(description = "Name given by the user to the cart")
	private LocalisedString cartName;

	@Schema(description = "Status of the cart")
	@Max(32)
	private String status;

	@NotNull
	@Schema(description = "Flag to indicate if the cart has been fulfilled as an order", required = true)
	private boolean checkedOut;

	@Schema(description = "Total price of the cart", required = true)
	private BigDecimal total;

	@Schema(description = "Lines of the cart")
	private List<CartLine> lines;

	@Override
	public CartModelDefinition getDefinition() {
		return DEFINITION;
	}
}