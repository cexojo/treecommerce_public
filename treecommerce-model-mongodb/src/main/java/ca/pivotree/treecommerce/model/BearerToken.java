package ca.pivotree.treecommerce.model;

import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.model.definitions.BearerTokenModelDefinition;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.LocalDateTime;
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
@MongoEntity(collection = "ptc_token")
@Schema(name = "Bearer token", description = "POJO with the representation of a token.")
public class BearerToken extends AbstractMongoModel {
	private static final BearerTokenModelDefinition DEFINITION = new BearerTokenModelDefinition();

	@NotNull
	@Schema(description = "UID of the token", required = true, example = "AB012AA362AA515")
	private String uid;

	@NotNull
	@Schema(description = "Content of the token", required = true)
	private String content;

	@NotNull
	@Schema(description = "Expiration date and time of the token", required = true)
	private LocalDateTime expiryDateTime;

	@Override
	public BearerTokenModelDefinition getDefinition() {
		return DEFINITION;
	}
}