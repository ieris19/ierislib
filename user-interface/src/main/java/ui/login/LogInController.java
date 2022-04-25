package ui.login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.ViewController;

import java.util.ArrayList;

public class LogInController extends ViewController {
	LogInModel model = (LogInModel) control;
	@FXML private Label titleLabel;
	@FXML private Label errorLabel;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Button logInButton;
	@FXML private Button signUpButton;
	private final String errorMessage = "Username and Password do not match an existing user";
	private ArrayList<User> userList;

	public void logIn() {
		System.out.println("Logged in");
		/*String username = usernameField.getText();
		String password = passwordField.getText();
		if (model.validUser())
			view.openView(1);*/
	}

	public void signUp() {
		System.out.println("Logged");
	}
}
