package ca.pivotree.treecommerce.model;

import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.model.definitions.CredentialModelDefinition;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.LocalDateTime;
import java.util.List;
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
@MongoEntity(collection = "ptc_credential")
public class Credential extends AbstractMongoModel {
	private static final CredentialModelDefinition DEFINITION = new CredentialModelDefinition();

	private String principalId;
	private String userName;
	private String password;
	private LocalDateTime passwordExpire;
	private String iss;
	private String upn;
	private List<String> roleMappings;
	private List<String> groups;
	@NotNull
	@Schema(description = "List of sites the credential gives access to", example = "[\"Site1\", \"Site2\"]")
	private List<String> sites;

	@Override
	public CredentialModelDefinition getDefinition() {
		return DEFINITION;
	}
}