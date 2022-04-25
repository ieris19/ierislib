package ui.login;

import ui.Model;

import java.util.ArrayList;

public interface LogInModel extends Model {

	ArrayList<User> getUserList();
	boolean validUser();
}
