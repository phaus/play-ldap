package controllers;

import models.User;

import com.innoq.ldap.connector.LdapHelper;
import com.innoq.ldap.connector.LdapNode;
import com.innoq.liqid.model.Node;
import com.innoq.liqid.utils.Configuration;

import play.Logger;
import play.Play;
import play.mvc.Before;
import play.mvc.Controller;

public class SecureApplication extends Controller {
	private final static LdapHelper HELPER = getLdapHelper();
	protected static User ActiveUser;
	@Before
	public static void checkLogin() {
		if (request.user == null || request.password == null
				|| !HELPER.checkCredentials(request.user, request.password)) {
			unauthorized("You need to Login first!");
		} else {
			ActiveUser = getUser(request.user);
		}
	}

	private static LdapHelper getLdapHelper() {
		Configuration.setPropertiesLocation(Play.applicationPath
				+ "/conf/application.conf");
		return LdapHelper.getInstance();
	}
	
	private static User getUser(String cn){
		LdapNode ldapNode = (LdapNode) HELPER.getUser(cn);
		return new User(cn, ldapNode.get("sn"), ldapNode.getDn());
	}
}
