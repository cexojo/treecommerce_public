package ca.pivotree.treecommerce.converter;

import ca.pivotree.treecommerce.core.exceptions.model.TreeUnknownModelFieldException;
import ca.pivotree.treecommerce.core.model.TreeModel;
import ca.pivotree.treecommerce.core.model.definition.AbstractModelDefinition;
import ca.pivotree.treecommerce.core.model.definition.AbstractModelDefinition.DTO_SETS;
import ca.pivotree.treecommerce.core.utils.TreeUtils;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

/**
 * Created by cexojo on 21/04/2020
 */

public abstract class AbstractModelConverter<MODEL extends TreeModel> {
	public Document toDto(MODEL source) {
		return toDto(source, TreeUtils.getRequestController().getFields());
	}

	public Document toDto(MODEL source, String fields) {
		AbstractModelDefinition definition = source.getDefinition();

		Document fieldsDocument;
		List<String> definitionFields;
		Map<String, String> definitionFormats = new HashMap<>();

		if (StringUtils.isBlank(fields)) {
			// No fields given, take the whole set of public fields
			definitionFields = definition.getAllPublicFields();
			definitionFields.forEach(field -> definitionFormats.put(field, DTO_SETS.FULL));
		} else if (DTO_SETS.DEFAULT_DTO_SETS.contains(fields)) {
			// A default DTO set, the "fields" value is not a document
			definitionFields = definition.dtoFields(fields);
			definitionFields.forEach(field -> definitionFormats.put(field, fields));
		} else {
			// If it's not a default DTO set, then it has to be a document with the field configuration
			fieldsDocument = Document.parse(fields);
			definitionFields = new ArrayList<>();

			for (Map.Entry<String, Object> entry : fieldsDocument.entrySet()) {
				definitionFields.add(entry.getKey());
				definitionFormats.put(entry.getKey(),
						entry.getValue() instanceof Document ? ((Document) entry.getValue()).toJson() : entry.getValue().toString());
			}
		}

		Document target = new Document();
		for (String definitionField : definitionFields) {
			Object value = getDtoFieldValueInternal(source, definitionField, definitionFormats.get(definitionField));

			if (Objects.nonNull(value)) {
				target.put(definitionField, value);
			}
		}

		return target;
	}

	public Object getDtoFieldValueInternal(MODEL model, String field, String format) {
		TreeValidationUtils.throwIfNull(model, "The model cannot be mull");
		TreeValidationUtils.throwIfBlank(field, "The field cannot be blank");
		TreeValidationUtils.throwIfFalse(model.getDefinition().getAllPublicFields().contains(field),
				new TreeUnknownModelFieldException(field, model.getClass().getSimpleName()));

		return getDtoFieldValue(model, field, format);
	}

	public abstract Object getDtoFieldValue(MODEL source, String field, String format);
	public abstract Object getModelFieldValue(MODEL source, String field);
	public abstract MODEL convertToModel(Document source, MODEL target, String... fields);
}
