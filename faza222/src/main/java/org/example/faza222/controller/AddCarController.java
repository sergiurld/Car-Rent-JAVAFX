package org.example.faza222.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.faza222.domain.Admin;
import org.example.faza222.service.Service;

public class AddCarController {
    private Service service;

    @FXML
    private TextField Make;
    @FXML
    private TextField Model;
    @FXML
    private TextField Fuel;
    @FXML
    private TextField Vin;
    @FXML
    private TextField Year;
    @FXML
    private TextField Km;
    @FXML
    private TextField Color;
    @FXML
    private TextField Seats;
    @FXML
    private TextField Power;
    @FXML
    private Button AddCarButton;
    private AdminController adminController; // Add this line



    public void setService(Service service){
        this.service = service;
    }

    public void initialize(){

    }
    public void handleAddCarFromCar(ActionEvent actionEvent){
        //Preluam valorile din textfield pentru masina
        String make = Make.getText();
        String model = Model.getText();
        String fuel = Fuel.getText();
        String vin = Vin.getText();
        String color = Color.getText();
        Long year = null;
        Long km = null;
        Long seats = null;
        Long power = null;
        try {
            year = Long.parseLong(Year.getText());
            km = Long.parseLong(Km.getText());
            seats = Long.parseLong(Seats.getText());
            power = Long.parseLong(Power.getText());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric values: " + e.getMessage());
        }
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Fuel: " + fuel);
        System.out.println("VIN: " + vin);
        System.out.println("Color: " + color);
        System.out.println("Year: " + year);
        System.out.println("Kilometers: " + km);
        System.out.println("Seats: " + seats);
        System.out.println("Power: " + power);

        // Check for non-null values
        if (make != null && model != null && fuel != null && vin != null && color != null && year != null && km != null && seats != null && power != null) {
            // Check additional constraints
            if (km > 0 && year >= 1950 && seats > 0 && power > 0) {
                service.saveCar(make,model,fuel,vin,year,km,"available",color,seats,power);
                if (adminController != null) {
                    adminController.update();
                }
                System.out.println("All values are valid.");
            } else {
                // Invalid values found, show alert
                showAlert("Invalid values detected", "Check kilometer, year, seats, or power.");
            }
        } else {
            // Null values found, show alert
            showAlert("Null values detected", "Make sure all fields are filled.");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setAdminController(AdminController adminController) { // Add this method
        this.adminController = adminController;
    }
}
