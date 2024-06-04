package org.example.faza222;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.faza222.controller.LoginController;
import org.example.faza222.repository.AdminRepository;
import org.example.faza222.repository.CarRepository;
import org.example.faza222.repository.ClientRepository;
import org.example.faza222.repository.RentRepository;
import org.example.faza222.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Correct the resource path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/faza222/login.fxml"));
            if (loader.getLocation() == null) {
                throw new IllegalStateException("FXML file not found: /org/example/faza222/login.fxml");
            }
            Parent root = loader.load();

            LoginController Logincontroller = loader.getController();

            Properties props = new Properties();
            try (FileReader reader = new FileReader("bd.config")) {
                props.load(reader);
            } catch (IOException e) {
                System.out.println("Cannot find bd.config: " + e);
                return; // Exit if properties cannot be loaded
            }

            AdminRepository adminRepository = new AdminRepository(props);
            CarRepository carRepository = new CarRepository(props);
            ClientRepository clientRepository = new ClientRepository(props);
            RentRepository rentRepository = new RentRepository(props);
            Service service = new Service(adminRepository, carRepository, clientRepository, rentRepository);

            Logincontroller.setService(service);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Log In");
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error while starting app: " + e);
            e.printStackTrace(); // Print stack trace for detailed debugging
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
