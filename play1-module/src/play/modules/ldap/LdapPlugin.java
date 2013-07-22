/**
 * LdapPlugin 04.05.2012
 *
 * @author Philipp Haussleiter
 *
 */
package play.modules.ldap;

import com.google.gson.JsonObject;
import com.innoq.ldap.connector.LdapHelper;
import com.innoq.liqid.model.Node;
import com.innoq.liqid.utils.Configuration;
import java.util.Set;
import play.Logger;
import play.Play;
import play.PlayPlugin;

public class LdapPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        Logger.info("LdapPlugin loaded");
        String propertiesFile = Play.applicationPath.getAbsolutePath() + "/conf/ldap.properties";
        Logger.info("\tProperties: " + propertiesFile);
        Configuration.getInstance().setPropertiesFile(propertiesFile);
        String tmpDir = Play.tmpDir.getAbsolutePath();
        Logger.info("\ttmpDir: " + tmpDir);
        Configuration.getInstance().setTmpDir(tmpDir);
        Logger.info("\tconnect to LDAP");
        LdapHelper.getInstance();
    }

    @Override
    public String getStatus() {
        LdapHelper instance;
        String output = "\nLDAP Plugin:";
        output += "\n~~~~~~~~~~~~";
        output += "\nldap.user.objectClasses:\n\t " + Configuration.getProperty("ldap.user.objectClasses").replace(",", ",\n\t") + "\n";
        output += "\nldap.group.objectClasses:\n\t " + Configuration.getProperty("ldap.group.objectClasses").replace(",", ",\n\t") + "\n";
        String defaultLdap = Configuration.getProperty("default.ldap");
        Set<Node> users = null;
        Set<Node> groups = null;
        int i = 1;
        output += "\nconnected LDAPs:";
        for (String ldap : LdapHelper.getLdaps()) {
            output += "\n\n\tLdap: " + ldap;
            if (ldap.equals(defaultLdap)) {
                output += " (read/write)";
            } else {
                output += " (write)";
            }
            output += "\n\tUrl: " + Configuration.getProperty(ldap + ".url");
            output += "\n\tPrincipal: " + Configuration.getProperty(ldap + ".principal");
            users = LdapHelper.getInstance(ldap).findUsers("*");
            if (users != null) {
                output += "\n\tUsers: " + users.size();
            }
            groups = LdapHelper.getInstance(ldap).findGroups("*");
            if (groups != null) {
                output += "\n\tGroups: " + groups.size();
            }
            output += "\n\tADDs/REMs/MODs: " + LdapHelper.getInstance(ldap).getCreationCount();
            output += "/";
            output += LdapHelper.getInstance(ldap).getDeletionCount();
            output += "/";
            output += LdapHelper.getInstance(ldap).getModificationCount();
            output += "\n\tVALs: " + LdapHelper.getInstance(ldap).getValidationCount();
            output += "\n\tQRYs: " + LdapHelper.getInstance(ldap).getQueryCount();
            i++;
            output += "\n";
        }
        output += "\n\n";
        return output;
    }

    @Override
    public JsonObject getJsonStatus() {
        JsonObject obj = new JsonObject();
        return obj;
    }
}
