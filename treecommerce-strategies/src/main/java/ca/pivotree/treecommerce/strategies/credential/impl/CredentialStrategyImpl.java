package ca.pivotree.treecommerce.strategies.credential.impl;

import ca.pivotree.treecommerce.core.strategies.credential.CredentialStrategy;
import ca.pivotree.treecommerce.core.utils.TreePbkdf2HashUtils;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by cexojo on 20/04/2020
 */
@ApplicationScoped
public class CredentialStrategyImpl implements CredentialStrategy {
	private static final int PLUS_YEARS = 1;

	@Override
	public String hashPassword(String password) {
		return TreePbkdf2HashUtils.hashPassword(password);
	}

	@Override
	public LocalDateTime getExpiryDateTime(LocalDateTime localDateTime) {
		TreeValidationUtils.throwIfNull(localDateTime, "The reference date cannot be null");

		return localDateTime.plusYears(PLUS_YEARS);
	}
}
