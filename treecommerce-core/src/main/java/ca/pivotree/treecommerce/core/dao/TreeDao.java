package ca.pivotree.treecommerce.core.dao;

import ca.pivotree.treecommerce.core.model.TreeModel;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by cexojo on 22/04/2020
 */

public interface TreeDao<T extends TreeModel> {
	void onSave(T model);
	void save(T model);
	void onDelete(T model);
	void delete(T model);
	void onQuery(String query, Map<String, Object> params);
	List<T> query(String query, Map<String, Object> params);
	Optional<T> queryOne(String query, Map<String, Object> params);
	Optional<T> getByPk(String pk);
}
