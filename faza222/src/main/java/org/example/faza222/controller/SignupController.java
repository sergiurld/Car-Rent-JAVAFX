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
import org.example.faza222.controller.AdminController;
import org.example.faza222.controller.ClientController;
import org.example.faza222.domain.Admin;
import org.example.faza222.domain.Client;
import org.example.faza222.service.Service;

import java.io.IOException;
public class SignupController {
    Service service;
    @FXML
    private TextField nameFieldSignup;
    @FXML
    private TextField usernameFieldSignup;

    @FXML
    private TextField addressFieldSignup;

    @FXML
    private TextField phoneFieldSignup;

    @FXML
    private TextField cnpFieldSignup;

    @FXML
    private PasswordField passwordFieldSignup;

    @FXML
    private PasswordField passwordFieldSignup2;

    @FXML
    private ChoiceBox<String> roleChoiceBoxSignup;

    @FXML
    private Button signupButton;
    @FXML
    private ChoiceBox<String> choiceBoxSignup;

    @FXML
    private Button backButton;

    public void initialize() {
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Client");
        choiceBoxSignup.setItems(roles);
    }
    public void handleSignup(ActionEvent actionEvent){
        String password1 = passwordFieldSignup.getText();
        String password2 = passwordFieldSignup2.getText();
        String name = nameFieldSignup.getText();
        String cnp = cnpFieldSignup.getText();
        String phone = phoneFieldSignup.getText();
        String username = usernameFieldSignup.getText();
        String address = addressFieldSignup.getText();
        String role = choiceBoxSignup.getValue();
        if(!password1.equals(password2)){
            showAlert(Alert.AlertType.ERROR, "Signup Error", "Passwords do not match!");
            return;
        }

        if(password1.isEmpty() || password2.isEmpty() || name.isEmpty() || cnp.isEmpty() || phone.isEmpty() || username.isEmpty() || address.isEmpty() || role.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Signup Error", "Please fill in all fields!");
            return;
        }

        try {
            if ("Admin".equals(role)) {
                service.saveAdmin(username, password1, cnp, name, address, phone);
            } else if ("Client".equals(role)) {
                service.saveClient(username, password1, cnp, name, address, phone);
            }
            showAlert(Alert.AlertType.INFORMATION, "Signup Success", "User added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Signup Error", "An error occurred while signing up the user.");
        }

    }

    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/faza222/login.fxml"));
            Parent signupPage = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(signupPage));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    public void setService(Service service) {
        this.service = service;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


