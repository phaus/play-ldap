package controllers;

import models.User;
import actions.BasicAuth;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Admin.index;

@BasicAuth
public class Admin extends Controller {
    public static Result index() {
    	User u = User.getUser(request().username());
        return ok(index.render("Hello Admin!", u));
    }
}
