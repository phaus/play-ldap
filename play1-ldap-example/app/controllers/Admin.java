package controllers;

import models.User;

public class Admin extends SecureApplication {
	public static void index(){
		User u = ActiveUser;
		render(u);
	}
}
