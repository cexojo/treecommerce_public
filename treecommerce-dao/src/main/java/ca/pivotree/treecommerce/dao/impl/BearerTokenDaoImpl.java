package ca.pivotree.treecommerce.dao.impl;

import ca.pivotree.treecommerce.core.dao.mongo.AbstractMongoDao;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.dao.BearerTokenDao;
import ca.pivotree.treecommerce.model.BearerToken;
import ca.pivotree.treecommerce.model.definitions.BearerTokenModelDefinition;
import com.google.common.collect.ImmutableMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by cexojo on 18/04/2020
 */

@SuppressWarnings({"unused"})
@ApplicationScoped
public class BearerTokenDaoImpl extends AbstractMongoDao<BearerToken> implements BearerTokenDao {
	private static final String ERR_MESSAGE_UID_BLANK = "The UID cannot be blank";
	private static final String QUERY_EXPIRED = BearerTokenModelDefinition._EXPIRYDATETIME + " < :" + BearerTokenModelDefinition._EXPIRYDATETIME;

	public BearerTokenDaoImpl() {
		super(BearerToken::new);
	}

	@Override
	public Optional<BearerToken> findByUid(String uid) {
		TreeValidationUtils.throwIfBlank(uid, ERR_MESSAGE_UID_BLANK);

		return find(BearerTokenModelDefinition._UID, uid).firstResultOptional();
	}

	@Override
	public List<BearerToken> findExpired() {
		return query(QUERY_EXPIRED, ImmutableMap.of(BearerTokenModelDefinition._EXPIRYDATETIME, LocalDateTime.now()));
	}
}