package ca.pivotree.treecommerce.core.dao.mongo;

import ca.pivotree.treecommerce.core.controller.AbstractController;
import ca.pivotree.treecommerce.core.dao.TreeDao;
import ca.pivotree.treecommerce.core.exceptions.model.TreeDeleteModelException;
import ca.pivotree.treecommerce.core.exceptions.model.TreeSaveModelException;
import ca.pivotree.treecommerce.core.model.definition.AbstractModelDefinition;
import ca.pivotree.treecommerce.core.model.mongo.AbstractMongoModel;
import ca.pivotree.treecommerce.core.utils.TreeUtils;
import com.google.common.collect.ImmutableMap;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * Created by cexojo on 22/04/2020
 */
@Slf4j
public abstract class AbstractMongoDao<T extends AbstractMongoModel> implements TreeDao<T>, PanacheMongoRepository<T> {
	private static final String QUERY_RESTRICTION_PATTERN = "query.restriction.%s.%s";
	private static final String QUERY_BY_PK = AbstractModelDefinition._PK + " = :" + AbstractModelDefinition._PK;

	private final Map<String, String> QUERY_RESTRICTIONS = new HashMap<>();

	public AbstractMongoDao(Supplier<T> supplier) {
		for (String role : AbstractController.ALL_ROLES) {
			Optional<String> queryRestriction = ConfigProvider.getConfig().getOptionalValue(String.format(QUERY_RESTRICTION_PATTERN,
					supplier.get().getDefinition().getType(), role),
					String.class);

			if (queryRestriction.isPresent()) {
				QUERY_RESTRICTIONS.put(role, queryRestriction.get());
			}
		}
	}

	@Override
	public void onSave(T model) {

	}

	@Override
	public void onDelete(T model) {

	}

	@Override
	public void onQuery(String query, Map<String, Object> params) {

	}

	@Override
	public void save(T model) {
		LocalDateTime now = LocalDateTime.now();

		model.setModificationTime(now);
		if (Objects.isNull(model.getCreationTime())) {
			model.setCreationTime(now);
		}

		try {
			onSave(model);
			model.persistOrUpdate();

			log.debug("Model '{}' ({}) saved.", model.getDefinition().getType(), model.getPk());
		} catch (Exception e) {
			throw new TreeSaveModelException(String.format("Model '{}' ({}) couldn't be saved.", model.getDefinition().getType(), model.getPk()), e);
		}
	}

	@Override
	public void delete(T model) {
		try {
			onDelete(model);
			model.delete();

			log.debug("Model '{}' ({}) deleted.", model.getDefinition().getType(), model.getPk());
		} catch (Exception e) {
			throw new TreeDeleteModelException(String.format("Model '{}' ({}) couldn't be deleted.", model.getDefinition().getType(), model.getPk()), e);
		}
	}

	@Override
	public List<T> query(String query, Map<String, Object> params) {
		onQuery(query, params);

		// Append the query restrictions for the current user to the query
		String queryWithRestrictions = query;
		Map<String, Object> paramsWithRestrictions = new HashMap<>(params);

		for (String role : TreeUtils.getRequestController().getUserGroups()) {
			String queryRestriction = QUERY_RESTRICTIONS.get(role);
			if (StringUtils.isNotBlank(queryRestriction)) {
				queryWithRestrictions += " AND " + queryRestriction + " ";
			}
		}

		paramsWithRestrictions.put("REQUEST_CURRENT_USER", TreeUtils.getRequestController().getEffectiveUserPk());

		return find(queryWithRestrictions, paramsWithRestrictions).list();
	}

	@Override
	public Optional<T> queryOne(String query, Map<String, Object> params) {
		List<T> result = query(query, params);
		return CollectionUtils.isNotEmpty(result) ? Optional.of(result.get(0)) : Optional.empty();
	}

	@Override
	public Optional<T> getByPk(String pk) {
		return queryOne(QUERY_BY_PK, ImmutableMap.of(AbstractModelDefinition._PK, pk));
	}
}
