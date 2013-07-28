package controllers;

import play.mvc.*;

import views.html.Application.index;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
  
}
