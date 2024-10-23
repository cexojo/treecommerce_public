package ca.pivotree.treecommerce.core.strategies.credential;

import java.time.LocalDateTime;

/**
 * Created by cexojo on 20/04/2020
 */

public interface CredentialStrategy {
	String hashPassword(String password);
	LocalDateTime getExpiryDateTime(LocalDateTime referenceDateTime);
}
