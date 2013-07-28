package models;

public class User {
	public String cn;
	public String sn;
	public String dn;

	public User(String cn, String sn, String dn) {
		this.cn = cn;
		this.sn = sn;
		this.dn = dn;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("cn: ").append(cn).append("\n");
		sb.append("sn: ").append(sn).append("\n");
		sb.append("dn: ").append(dn).append("\n");
		return sb.toString();
	}
}
