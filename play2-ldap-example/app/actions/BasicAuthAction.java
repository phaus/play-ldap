package actions;

import com.ning.http.util.Base64;

import models.User;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public class BasicAuthAction extends Action<Object> {

	private static final String AUTHORIZATION = "authorization";
	private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	private static final String REALM = "Basic realm=\"play2-ldap-example\"";

	@Override
	public Result call(Context context) throws Throwable {

		String authHeader = context.request().getHeader(AUTHORIZATION);
		if (authHeader == null) {
			return sendAuthRequest(context);
		}

		String auth = authHeader.substring(6);

		byte[] decodedAuth = Base64.decode(auth);
		String[] credString = new String(decodedAuth, "UTF-8").split(":");

		if (credString == null || credString.length != 2) {
			return sendAuthRequest(context);
		}

		String username = credString[0];
		String password = credString[1];
		User authUser = User.authenticate(username, password);
		if (authUser == null) {
			return sendAuthRequest(context);
		}
		context.request().setUsername(username);
		return delegate.call(context);
	}

	private Result sendAuthRequest(Context context) {
		context.response().setHeader(WWW_AUTHENTICATE, REALM);
		return unauthorized();
	}
}