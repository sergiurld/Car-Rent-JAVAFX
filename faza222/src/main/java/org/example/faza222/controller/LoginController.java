package org.example.faza222.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.faza222.HelloApplication;
import org.example.faza222.domain.Admin;
import org.example.faza222.domain.Client;
import org.example.faza222.service.Service;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonCreateNewUserLogin;
    @FXML
    private TextField usernameFieldLogin;
    @FXML
    private PasswordField passwordFieldLogin;
    @FXML
    private ChoiceBox<String> choiceBoxLogin;

    private Service service;

    public void initialize() {
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Client");
        choiceBoxLogin.setItems(roles);
    }

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        try {
            String username = usernameFieldLogin.getText();
            String password = passwordFieldLogin.getText();
            String selectedRole = choiceBoxLogin.getValue();
            if (username.isEmpty() || password.isEmpty() || selectedRole == null || selectedRole.isEmpty()) {
                showAlert("Login Error", "Please fill in all fields!");
                return;
            }
            if ("Admin".equals(selectedRole)) {
                System.out.println("username: " + username);
                System.out.println("password: " + password);
                Admin a = service.getAdminByUsername(username, password);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/faza222/adminwindow.fxml"));
                Parent root = fxmlLoader.load();
                AdminController adminController = fxmlLoader.getController();
                adminController.setService(service, a);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Salut Adminule: " + a.getName());
                stage.show();
            } else if ("Client".equals(selectedRole)) {
                System.out.println("username: " + username);
                System.out.println("password: " + password);
                Client c = service.getClientByUsername(username, password);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/faza222/abonatwindow.fxml"));
                Parent root = fxmlLoader.load();
                ClientController clientController = fxmlLoader.getController();
                clientController.setService(service, c);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Salut Clientule: " + c.getName());
                stage.show();
            }
        } catch (IOException e) {
            showAlert("Login Error", "An error occurred while loading the window.");
        } catch (ClassCastException e) {
            showAlert("Login Error", "Invalid user type.");
        } catch (Exception e) {
            showAlert("Login Error", "Invalid username or password.");
        }
    }

    @FXML
    public void handleSignupFromLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/faza222/signup.fxml"));
            Parent signupPage = fxmlLoader.load();
            SignupController signupController = fxmlLoader.getController();
            signupController.setService(service);
            Stage stage = new Stage();
            stage.setScene(new Scene(signupPage));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
