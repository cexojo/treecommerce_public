package ca.pivotree.treecommerce.service.jwt;

import ca.pivotree.treecommerce.model.BearerToken;
import ca.pivotree.treecommerce.model.Credential;
import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

/**
 * Created by cexojo on 07/04/2020
 */

public interface JwtService {
	String getToken(Credential credential, Credential impersonatedCredential, PrivateKey privateKey, String privateKeyId);
	Optional<String> getUserIdBeingImpersonated();
	String getEffectiveUserPk();
	Optional<BearerToken> getBearerTokenByUid(String uid);
	List<String> getUserGroups();
}
