package models;

import play.Play;

import com.innoq.ldap.connector.LdapHelper;
import com.innoq.ldap.connector.LdapUser;
import com.innoq.liqid.utils.Configuration;

public class User {

	private static LdapHelper HELPER = getHelper();

	public String sn;
	public String cn;
	public String dn;

	public User(String cn) {
		this.cn = cn;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("cn: ").append(cn).append("\n");
		sb.append("sn: ").append(sn).append("\n");
		sb.append("dn: ").append(dn).append("\n");
		return sb.toString();
	}

	public static User authenticate(String username, String password) {
		if (HELPER.checkCredentials(username, password)) {
			return new User(username);
		}
		return null;
	}

	public static User getUser(String username) {
		LdapUser ldapUser = (LdapUser) LdapHelper.getInstance().getUser(
				username);
		User user = new User(username);
		user.cn = ldapUser.get("cn");
		user.sn = ldapUser.get("sn");
		user.dn = ldapUser.getDn();
		return user;
	}

	private static LdapHelper getHelper() {
		Configuration.setPropertiesLocation(Play.application().path()
				.getAbsolutePath()
				+ "/conf/ldap.properties");
		return LdapHelper.getInstance();
	}
}
