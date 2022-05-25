package ui.login;

import ui.Model;

import java.util.ArrayList;

public class ModelController implements LogInModel, Model {

	@Override public ArrayList<User> getUserList() {
		return null;
	}

	@Override public boolean validUser() {
		return false;
	}
}
