package ca.pivotree.treecommerce.model;

import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.core.model.mongo.attribs.LocalisedString;
import ca.pivotree.treecommerce.model.definitions.SiteModelDefinition;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import java.util.Locale;
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
@MongoEntity(collection = "ptc_site")
@Schema(name = "Site", description = "POJO with the representation of a site.")
public class Site extends AbstractMongoModel {
	private static final SiteModelDefinition DEFINITION = new SiteModelDefinition();

	@NotNull
	@Schema(description = "UID of the site", required = true, example = "esTree")
	private String uid;

	@NotNull
	@Schema(description = "Description of the site", required = true, example = "Tree Site for Spain")
	private LocalisedString description;

	@NotNull
	@Schema(description = "Default language of the site", required = true, example = "es_ES")
	private Locale defaultLanguage;

	@NotNull
	@Schema(description = "Set of supported languages of the site", required = true, example = "[es_ES, en, fr, de_CH]")
	private List<Locale> supportedLanguages;

	@Override
	public SiteModelDefinition getDefinition() {
		return DEFINITION;
	}
}