package ca.pivotree.treecommerce.service.credential.impl;

import static ca.pivotree.treecommerce.core.controller.AbstractController.ALL_ROLES;
import static ca.pivotree.treecommerce.core.controller.AbstractController.PROTECTED_ROLES;

import ca.pivotree.treecommerce.core.controller.AbstractController;
import ca.pivotree.treecommerce.core.strategies.credential.CredentialStrategy;
import ca.pivotree.treecommerce.core.utils.TreeValidationUtils;
import ca.pivotree.treecommerce.dao.CredentialDao;
import ca.pivotree.treecommerce.dao.SiteDao;
import ca.pivotree.treecommerce.model.Credential;
import ca.pivotree.treecommerce.model.Person;
import ca.pivotree.treecommerce.model.Site;
import ca.pivotree.treecommerce.service.credential.CredentialService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Created by cexojo on 07/04/2020
 */

@ApplicationScoped
public class CredentialServiceImpl implements CredentialService {
	private static final String ERR_MESSAGE_CREDENTIAL_NULL = "The credential cannot be null";
	private static final String ERR_MESSAGE_EXPIRY_DATE_NULL = "The expiry date cannot be null";
	private static final String ERR_MESSAGE_SITE_NULL = "The site cannot be null";
	private static final String ERR_MESSAGE_INVALID_ROLES = "At least one of the roles is invalid";
	private static final String ERR_MESSAGE_INVALID_SITES = "At least one of the sites is invalid";

	@ConfigProperty(name = "mp.jwt.verify.issuer")
	String credentialIssuer;

	@Inject
	CredentialStrategy credentialStrategy;

	@Inject
	CredentialDao credentialDao;

	@Inject
	SiteDao siteDao;

	@Override
	public Optional<Credential> getByUsername(String username) {
		return credentialDao.getByUsername(username);
	}

	@Override
	public Optional<Credential> getByUsernameAndPassword(String username, String password) {
		return credentialDao.getByUsernameAndPassword(username, credentialStrategy.hashPassword(password));
	}

	@Override
	public Optional<Credential> getByPrincipalId(String principalId) {
		return credentialDao.getByPrincipalId(principalId);
	}

	@Override
	public boolean isExpired(Credential credential) {
		TreeValidationUtils.throwIfNull(credential, ERR_MESSAGE_CREDENTIAL_NULL);
		TreeValidationUtils.throwIfNull(credential.getPasswordExpire(), ERR_MESSAGE_EXPIRY_DATE_NULL);

		return LocalDateTime.now().isAfter(credential.getPasswordExpire());
	}

	@Override
	public boolean isGrantedForSite(Credential credential, Site site) {
		if (!isSiteSpecific(credential)) {
			return true;
		}

		TreeValidationUtils.throwIfNull(site, ERR_MESSAGE_SITE_NULL);
		return CollectionUtils.isNotEmpty(credential.getSites()) && credential.getSites().contains(site.getPk());
	}

	@Override
	public void addRoles(Credential credential, List<String> roles) {
		if (!CollectionUtils.containsAll(ALL_ROLES, roles) || CollectionUtils.containsAny(PROTECTED_ROLES, roles)) {
			throw new IllegalArgumentException(ERR_MESSAGE_INVALID_ROLES);
		}

		if (CollectionUtils.isEmpty(credential.getGroups())) {
			credential.setGroups(new ArrayList<>());
		}

		roles.stream().filter(r -> credential.getGroups().contains(r)).forEach(r -> credential.getGroups().add(r));

		save(credential);
	}

	@Override
	public void removeRoles(Credential credential, List<String> roles) {
		if (CollectionUtils.isEmpty(credential.getGroups())) {
			return;
		}

		if (!CollectionUtils.containsAll(ALL_ROLES, roles) || CollectionUtils.containsAny(PROTECTED_ROLES, roles)) {
			throw new IllegalArgumentException(ERR_MESSAGE_INVALID_ROLES);
		}

		credential.getGroups().removeAll(roles);

		save(credential);
	}

	@Override
	public void addSites(Credential credential, List<String> sites) {
		List<String> sitePks = new ArrayList<>();

		for (String siteUid : sites) {
			Optional<Site> site = siteDao.findByUid(siteUid);
			TreeValidationUtils.throwIfFalse(site.isPresent(), ERR_MESSAGE_INVALID_SITES);

			sitePks.add(site.get().getPk());
		}

		if (CollectionUtils.isEmpty(credential.getSites())) {
			credential.setSites(new ArrayList<>());
		}

		credential.getSites().addAll(CollectionUtils.disjunction(credential.getSites(), sitePks));

		save(credential);
	}

	@Override
	public void removeSites(Credential credential, List<String> sites) {

	}

	@Override
	public void create(Person person, String username, String password) {
		Credential credential =
				Credential.builder().userName(username).password(credentialStrategy.hashPassword(password)).iss(credentialIssuer).principalId(person.getPk())
						.passwordExpire(credentialStrategy.getExpiryDateTime(LocalDateTime.now())).build();

		credentialDao.save(credential);
	}

	@Override
	public void save(Credential credential) {
		credentialDao.save(credential);
	}

	private boolean isSiteSpecific(Credential credential) {
		return CollectionUtils.isNotEmpty(credential.getGroups()) && CollectionUtils
				.containsAny(credential.getGroups(), AbstractController.SITE_SPECIFIC_ROLES);
	}
}
