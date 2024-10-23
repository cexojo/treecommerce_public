package ca.pivotree.treecommerce.service.jwt.impl;

import static ca.pivotree.treecommerce.core.constants.TreeClaims.CLAIM_EFFECTIVE_USER_ID;
import static ca.pivotree.treecommerce.core.constants.TreeClaims.CLAIM_IS_IMPERSONATING;

import ca.pivotree.treecommerce.core.constants.TreeClaims;
import ca.pivotree.treecommerce.core.utils.TreeUtils;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.dao.BearerTokenDao;
import ca.pivotree.treecommerce.model.BearerToken;
import ca.pivotree.treecommerce.model.Credential;
import ca.pivotree.treecommerce.service.jwt.JwtService;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.drools.core.util.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Created by cexojo on 07/04/2020
 */
@ApplicationScoped
@Slf4j
public class JwtServiceImpl implements JwtService {
	private static final String ERR_MESSAGE_TOKEN_NULL = "Token is null";

	@ConfigProperty(name = "jwt.ttl", defaultValue = "1800")
	String ttl;

	@Inject
	JsonWebToken jwt;

	@Inject
	BearerTokenDao bearerTokenDao;

	@Override
	public String getToken(Credential credential, Credential impersonatedCredential, PrivateKey privateKey, String privateKeyId) {
		HashMap<String, Object> claimsMap = new HashMap<>();
		if (Objects.nonNull(impersonatedCredential)) {
			claimsMap.put(TreeClaims.CLAIM_IS_IMPERSONATING, true);
			claimsMap.put(TreeClaims.CLAIM_EFFECTIVE_USER_ID, impersonatedCredential.getPrincipalId().toString());
		} else {
			claimsMap.put(TreeClaims.CLAIM_EFFECTIVE_USER_ID, credential.getPrincipalId().toString());
		}

		long currentTimeInSecs = TreeUtils.currentTimeInSecs();
		long expirationTimeInSecs = currentTimeInSecs + Integer.parseInt(ttl);
		claimsMap.put(Claims.auth_time.name(), currentTimeInSecs);
		claimsMap.put(Claims.exp.name(), expirationTimeInSecs);

		List<String> groups = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(credential.getGroups())) {
			groups.addAll(credential.getGroups());
		}
		if (Objects.nonNull(impersonatedCredential) && CollectionUtils.isNotEmpty(impersonatedCredential.getGroups())) {
			groups.addAll(impersonatedCredential.getGroups());
		}

		JwtClaimsBuilder claims =
				Jwt.claims(claimsMap).issuer(credential.getIss()).upn(credential.getUpn())
						.groups(new HashSet<>(groups));

		String token = claims.jws().signatureKeyId(privateKeyId).sign(privateKey);

		// Save the token with the expiry date time and return its content
		BearerToken bearerToken = new BearerToken(UUID.randomUUID().toString(), token,
				LocalDateTime.ofInstant(Instant.ofEpochSecond(expirationTimeInSecs), ZoneId
						.systemDefault()));
		bearerTokenDao.save(bearerToken);

		return bearerToken.getUid();
	}
/*
	@Override
	@Scheduled(every = "600s")
	public void purgeExpiredTokens() {
		log.debug("Purging expired tokens...");

		List<BearerToken> tokensToBePurged = bearerTokenDao.findExpired();
		log.debug("Found {} tokens to be purged.", tokensToBePurged.size());

		bearerTokenDao.remove(tokensToBePurged);

		log.debug("Done purging expired tokens.");
	}*/

	@Override
	public Optional<BearerToken> getBearerTokenByUid(String uid) {
		return bearerTokenDao.findByUid(uid);
	}

	@Override
	public List<String> getUserGroups() {
		return new ArrayList<>(CollectionUtils.isEmpty(jwt.getGroups()) ? CollectionUtils.emptyCollection() : jwt.getGroups());
	}

	public Optional<String> getUserIdBeingImpersonated() {
		TreeValidationUtils.throwIfNull(jwt, ERR_MESSAGE_TOKEN_NULL);

		if (jwt.containsClaim(CLAIM_IS_IMPERSONATING)) {
			return jwt.claim(CLAIM_EFFECTIVE_USER_ID);
		}

		return Optional.empty();
	}

	public String getEffectiveUserPk() {
		TreeValidationUtils.throwIfNull(jwt, ERR_MESSAGE_TOKEN_NULL);

		if (jwt.claim(CLAIM_EFFECTIVE_USER_ID).isPresent()) {
			return jwt.claim(CLAIM_EFFECTIVE_USER_ID).get().toString();
		} else {
			return StringUtils.EMPTY;
		}
	}
}
