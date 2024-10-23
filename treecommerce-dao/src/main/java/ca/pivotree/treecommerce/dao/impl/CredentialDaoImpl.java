package ca.pivotree.treecommerce.dao.impl;

import ca.pivotree.treecommerce.core.dao.mongo.AbstractMongoDao;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.dao.CredentialDao;
import ca.pivotree.treecommerce.model.Credential;
import ca.pivotree.treecommerce.model.definitions.CredentialModelDefinition;
import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by cexojo on 30/03/2020
 */

@SuppressWarnings({"unused"})
@ApplicationScoped
public class CredentialDaoImpl extends AbstractMongoDao<Credential> implements CredentialDao {
	private static final String ERR_MESSAGE_USERNAME_BLANK = "The username cannot be blank";
	private static final String ERR_MESSAGE_PASSWORD_BLANK = "The password cannot be blank";
	private static final String ERR_MESSAGE_PRINCIPAL_ID_BLANK = "The principal ID cannot be blank";

	private static final String QUERY_BY_USERNAME_AND_PASSWORD =
			CredentialModelDefinition._USERNAME + " = :" + CredentialModelDefinition._USERNAME + " and "
					+ CredentialModelDefinition._PASSWORD + " = :" + CredentialModelDefinition._PASSWORD;

	public CredentialDaoImpl() {
		super(Credential::new);
	}

	@Override
	public Optional<Credential> getByUsername(String username) {
		TreeValidationUtils.throwIfBlank(username, ERR_MESSAGE_USERNAME_BLANK);

		return find(CredentialModelDefinition._USERNAME, username).firstResultOptional();
	}

	@Override
	public Optional<Credential> getByUsernameAndPassword(String username, String password) {
		TreeValidationUtils.throwIfBlank(username, ERR_MESSAGE_USERNAME_BLANK);
		TreeValidationUtils.throwIfBlank(password, ERR_MESSAGE_PASSWORD_BLANK);

		return find(QUERY_BY_USERNAME_AND_PASSWORD,
				ImmutableMap
						.of(CredentialModelDefinition._USERNAME, username, CredentialModelDefinition._PASSWORD,
								password)).firstResultOptional();
	}

	@Override
	public Optional<Credential> getByPrincipalId(String principalId) {
		TreeValidationUtils.throwIfBlank(principalId, ERR_MESSAGE_USERNAME_BLANK);

		return find(CredentialModelDefinition._PRINCIPALID, principalId).firstResultOptional();
	}
}