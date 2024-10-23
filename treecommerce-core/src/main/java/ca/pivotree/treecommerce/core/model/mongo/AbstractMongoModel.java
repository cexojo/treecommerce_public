package ca.pivotree.treecommerce.core.model.mongo;

import ca.pivotree.treecommerce.core.model.TreeModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Created by cexojo on 06/04/2020
 */
@Data
public abstract class AbstractMongoModel extends PanacheMongoEntity implements TreeModel {
	@JsonIgnore
	private ObjectId id;

	@Schema(description = "PK of the model", required = true, uniqueItems = true)
	private String pk;

	@NotNull
	@Schema(description = "Creation time of the model", required = true)
	private LocalDateTime creationTime;

	@Schema(description = "Modification time of the model")
	private LocalDateTime modificationTime;

	public AbstractMongoModel() {
		pk = UUID.randomUUID().toString();
	}
}
