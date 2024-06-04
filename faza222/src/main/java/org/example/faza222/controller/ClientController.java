package org.example.faza222.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.faza222.Observer;
import org.example.faza222.domain.Car;
import org.example.faza222.domain.Client;
import org.example.faza222.service.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ClientController implements Initializable, Observer {

    private Service service;
    private Client client;
    @FXML
    private Button ClientLogoutButton;
    @FXML
    private TableView<Car> table11;

    @FXML
    private TableView<Car> table22;
    @FXML
    private TableColumn<Car,String> col1;
    @FXML
    private TableColumn<Car,String> col2;
    @FXML
    private TableColumn<Car,String> col3;
    @FXML
    private TableColumn<Car,Long> col4;
    @FXML
    private TableColumn<Car,Long> col5;
    @FXML
    private TableColumn<Car,String> col6;
    @FXML
    private TableColumn<Car,Long> col7;
    @FXML
    private TableColumn<Car,Long> col8;
    @FXML
    private TableColumn<Car,String> col9;
    @FXML
    private TableColumn<Car,String> col10;
    @FXML
    private TableColumn<Car,String> col11;
    @FXML
    private TableColumn<Car,Long> col12;
    @FXML
    private TableColumn<Car,Long> col13;
    @FXML
    private TableColumn<Car,String> col14;
    @FXML
    private TableColumn<Car,Long> col15;
    @FXML
    private TableColumn<Car,Long> col16;
    @FXML
    private DatePicker ClientDatePicker;
    @FXML
    private TextField ClientNote;
    private ObservableList<Car> allCarsAvailable = FXCollections.observableArrayList();
    private ObservableList<Car> allCarsUnavailable = FXCollections.observableArrayList();

    public void setService(Service service, Client client){
        this.service = service;
        this.client = client;
        this.service.addObserver(this);
        update();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col1.setCellValueFactory(new PropertyValueFactory<Car,String>("carMake"));
        col2.setCellValueFactory(new PropertyValueFactory<Car,String>("carModel"));
        col3.setCellValueFactory(new PropertyValueFactory<Car,String>("fuel"));
        col4.setCellValueFactory(new PropertyValueFactory<Car,Long>("year"));
        col5.setCellValueFactory(new PropertyValueFactory<Car,Long>("km"));
        col6.setCellValueFactory(new PropertyValueFactory<Car,String>("color"));
        col7.setCellValueFactory(new PropertyValueFactory<Car,Long>("numberOfSeats"));
        col8.setCellValueFactory(new PropertyValueFactory<Car,Long>("power"));
        table11.setItems(allCarsAvailable);
        //table unavailable
        col9.setCellValueFactory(new PropertyValueFactory<Car,String>("carMake"));
        col10.setCellValueFactory(new PropertyValueFactory<Car,String>("carModel"));
        col11.setCellValueFactory(new PropertyValueFactory<Car,String>("fuel"));
        col12.setCellValueFactory(new PropertyValueFactory<Car,Long>("year"));
        col13.setCellValueFactory(new PropertyValueFactory<Car,Long>("km"));
        col14.setCellValueFactory(new PropertyValueFactory<Car,String>("color"));
        col15.setCellValueFactory(new PropertyValueFactory<Car,Long>("numberOfSeats"));
        col16.setCellValueFactory(new PropertyValueFactory<Car,Long>("power"));
        table22.setItems(allCarsUnavailable);
    }

    @Override
    public void update() {
        allCarsAvailable = FXCollections.observableArrayList(service.getAvailableCars());
        allCarsUnavailable = FXCollections.observableArrayList(service.getUnavailableCars());
        table11.setItems(allCarsAvailable);
        table11.refresh();
        table22.setItems(allCarsUnavailable);
        table22.refresh();
    }

    public void handleClientLogout(ActionEvent actionEvent) {
        Stage stage = (Stage) ClientLogoutButton.getScene().getWindow();
        stage.close();
    }
    public void handleClientRefresh(ActionEvent actionEvent) {
        update();
    }

    public void handleRentAction(ActionEvent actionEvent) {
        // Get the selected car from the available cars table
        Car selectedCar = table11.getSelectionModel().getSelectedItem();

        // Check if a car is selected
        if (selectedCar == null) {
            showAlert("No car selected", "Please select a car to rent.");
            return;
        }

        // Check if the date selected in the DatePicker is in the future
        LocalDateTime selectedDate = ClientDatePicker.getValue().atStartOfDay();
        if (selectedDate == null || selectedDate.isBefore(LocalDate.now().atStartOfDay())) {
            showAlert("Invalid date", "Please select a future date for renting the car.");
            return;
        }

        // Note can be null, so no need to validate it
        String note = ClientNote.getText();

        // Move the car to the unavailable cars list
        service.saveRent(selectedCar, client, selectedDate, note);
        update();

        // Optionally, clear the selection and input fields
        table11.getSelectionModel().clearSelection();
        ClientDatePicker.setValue(null);
        ClientNote.clear();
        update();
        showAlert("Success", "Car has been successfully rented.");
    }

    // Utility method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
