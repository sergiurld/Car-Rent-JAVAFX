package org.example.faza222.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.faza222.Observer;
import org.example.faza222.domain.Admin;
import org.example.faza222.domain.Car;
import org.example.faza222.domain.Client;
import org.example.faza222.service.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable, Observer {
    private Service service;
    private Admin admin;
    @FXML
    private Button AdminLogoutButton;
    @FXML
    private Button AdminAddCar;
    @FXML
    private TableView<Car> table1;

    @FXML
    private TableView<Car> table2;
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
    private ObservableList<Car> allCarsAvailable = FXCollections.observableArrayList();
    private ObservableList<Car> allCarsUnavailable = FXCollections.observableArrayList();




    public void setService(Service service, Admin admin){
        this.service = service;
        this.admin = admin;
        this.service.addObserver(this);
        update();
    }

    @Override
    public void update() {
        allCarsAvailable = FXCollections.observableArrayList(service.getAvailableCars());
        allCarsUnavailable = FXCollections.observableArrayList(service.getUnavailableCars());
        table1.setItems(allCarsAvailable);
        table1.refresh();
        table2.setItems(allCarsUnavailable);
        table2.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //table available
        col1.setCellValueFactory(new PropertyValueFactory<Car,String>("carMake"));
        col2.setCellValueFactory(new PropertyValueFactory<Car,String>("carModel"));
        col3.setCellValueFactory(new PropertyValueFactory<Car,String>("fuel"));
        col4.setCellValueFactory(new PropertyValueFactory<Car,Long>("year"));
        col5.setCellValueFactory(new PropertyValueFactory<Car,Long>("km"));
        col6.setCellValueFactory(new PropertyValueFactory<Car,String>("color"));
        col7.setCellValueFactory(new PropertyValueFactory<Car,Long>("numberOfSeats"));
        col8.setCellValueFactory(new PropertyValueFactory<Car,Long>("power"));
        table1.setItems(allCarsAvailable);
        //table unavailable
        col9.setCellValueFactory(new PropertyValueFactory<Car,String>("carMake"));
        col10.setCellValueFactory(new PropertyValueFactory<Car,String>("carModel"));
        col11.setCellValueFactory(new PropertyValueFactory<Car,String>("fuel"));
        col12.setCellValueFactory(new PropertyValueFactory<Car,Long>("year"));
        col13.setCellValueFactory(new PropertyValueFactory<Car,Long>("km"));
        col14.setCellValueFactory(new PropertyValueFactory<Car,String>("color"));
        col15.setCellValueFactory(new PropertyValueFactory<Car,Long>("numberOfSeats"));
        col16.setCellValueFactory(new PropertyValueFactory<Car,Long>("power"));
        table2.setItems(allCarsUnavailable);
    }

    public void handleAdminLogout(ActionEvent actionEvent) {
        Stage stage = (Stage) AdminLogoutButton.getScene().getWindow();
        stage.close();
    }

    public void handleAdminAddCar(ActionEvent actionEvent) {
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/faza222/addcar.fxml"));
                Parent root = fxmlLoader.load();
                AddCarController addCarController = fxmlLoader.getController();
                addCarController.setService(service);
                addCarController.setAdminController(this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Adauga o Masina bogatule" );
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("Error casting controller: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAdminDeleteCar(ActionEvent actionEvent) {
        Car selectedCar = table1.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            service.deleteCar(selectedCar);
            System.out.println("Selected Car was deleted");
        } else {
            System.out.println("No car selected");
        }
        update();
    }

    public void handleAdminRefresh(ActionEvent actionEvent) {
        update();
    }

    public void handleAdminReturn(ActionEvent actionEvent) {
        Car selectedCar = table2.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            service.deleteRent(selectedCar);
            System.out.println("Selected Car was returned");
        } else {
            System.out.println("No car selected");
        }
        update();
        table2.getSelectionModel().clearSelection();


    }

    public void handleAdminModifyCar(ActionEvent actionEvent) {
        try {
            Car selectedCar = table1.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/faza222/modifycar.fxml"));
            Parent root = fxmlLoader.load();
            ModifyCarController modifyCarController = fxmlLoader.getController();
            modifyCarController.setService(service,selectedCar);
            modifyCarController.setAdminController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifica o Masina bogatule" );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("Error casting controller: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
