package ca.pivotree.treecommerce.core.controller;

import java.util.List;
import java.util.Locale;

/**
 * Created by cexojo on 18/04/2020
 */

public interface RequestController {
	Locale getLocale();
	void setLocale(String locale);
	Object getSite();
	void setSite(Object site);
	Object getPerson();
	List<String> getUserGroups();
	String getEffectiveUserPk();
	String getFields();
	void setFields(String fields);
}
