package ca.pivotree.treecommerce.model;

import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.model.definitions.PersonModelDefinition;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.LocalDate;
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
@MongoEntity(collection = "ptc_person")
@Schema(name = "Person", description = "POJO with the representation of a person.")
public class Person extends AbstractMongoModel {
	private static final PersonModelDefinition DEFINITION = new PersonModelDefinition();

	@NotNull
	@Schema(description = "First name of the person", required = true, example = "John")
	private String firstName;

	@NotNull
	@Schema(description = "Last name of the person", required = true, example = "Smith")
	private String lastName;

	@Schema(description = "Birth date of the person")
	private LocalDate birthDate;

	@NotNull
	@Schema(description = "Email of the person", example = "john@smith.com")
	private String email;

	@Override
	public PersonModelDefinition getDefinition() {
		return DEFINITION;
	}
}