package ca.pivotree.treecommerce.core.controller;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Created by cexojo on 07/04/2020
 */

// @formatter:off
public abstract class AbstractController {
	public static final String API_ROOT = "/tree-api";

	public static final List<String> SITE_SPECIFIC_ROLES = ImmutableList.of(ROLES.CUSTOMER);
	/**
	 * These roles are protected from being added/removed through the API.
	 */
	public static final List<String> PROTECTED_ROLES = ImmutableList.of(ROLES.ADMIN);
	/**
	 * These roles are audited when being added/removed through the API.
	 */
	public static final List<String> AUDITABLE_ROLES = ImmutableList.of(ROLES.CREDENTIAL_MANAGER, ROLES.SITE_ADMIN);
	/**
	 * These roles are audited when being added/removed through the API.
	 */
	public static final List<String> ALL_ROLES = ImmutableList.of(ROLES.ADMIN, ROLES.SITE_ADMIN, ROLES.CUSTOMER, ROLES.CREDENTIAL_MANAGER);

	public static final class ROLES {
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String SITE_ADMIN = "ROLE_SITE_ADMIN";
		public static final String CUSTOMER = "ROLE_CUSTOMER";
		public static final String CREDENTIAL_MANAGER = "ROLE_CREDENTIAL_MANAGER";
	}
}
// @formatter:on