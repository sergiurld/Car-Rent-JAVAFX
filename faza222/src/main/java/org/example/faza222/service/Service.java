package org.example.faza222.service;

import org.example.faza222.Observer;
import org.example.faza222.domain.Admin;
import org.example.faza222.domain.Car;
import org.example.faza222.domain.Client;
import org.example.faza222.domain.Rent;
import org.example.faza222.repository.AdminRepository;
import org.example.faza222.repository.CarRepository;
import org.example.faza222.repository.ClientRepository;
import org.example.faza222.repository.RentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Service {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    private final AdminRepository adminRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final RentRepository rentRepository;

    public Service(AdminRepository adminRepository, CarRepository carRepository, ClientRepository clientRepository, RentRepository rentRepository){
        this.adminRepository = adminRepository;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.rentRepository = rentRepository;
    }


    public List<Car> getAvailableCars() {
        List<Car> allCars = (List<Car>) carRepository.findAll();
        List<Car> availableCars = new ArrayList<>();

        for (Car car : allCars) {
            if ("available".equals(car.getStatus())) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
    public List<Car> getUnavailableCars() {
        List<Car> allCars = (List<Car>) carRepository.findAll();
        List<Car> unavailableCars = new ArrayList<>();

        for (Car car : allCars) {
            if ("unavailable".equals(car.getStatus())) {
                unavailableCars.add(car);
            }
        }
        return unavailableCars;
    }

    public List<Car> getAllCars(){
        List <Car> allCars = (List<Car>) carRepository.findAll();
        return allCars;
    }
    public Optional<Car> saveCar(String carMake, String carModel, String fuel, String vin, Long year, Long km, String status, String color, Long numberOfSeats, Long power) {
        String uniqueCode = UUID.randomUUID().toString();
        Car newCar = new Car(uniqueCode, carMake, carModel, fuel, vin, year, km, status, color, numberOfSeats, power);
        Optional<Car> result = carRepository.save(newCar);
        notifyObservers();
        return result;
    }
    public Optional<Car>deleteCar(Car car){
        Optional<Car> result = carRepository.delete(car);
        notifyObservers();
        return result;
    }
    public Optional<Admin>saveAdmin(String username, String password, String cnp, String name, String address, String phone){
        String uniqueCode = UUID.randomUUID().toString();
        Admin newAdmin = new Admin(username,password,uniqueCode,cnp,name,address,phone);
        Optional<Admin> result = adminRepository.save(newAdmin);
        notifyObservers();
        return result;
    }
    public Optional<Client>saveClient(String username, String password, String cnp, String name, String address, String phone){
        String uniqueCode = UUID.randomUUID().toString();
        Client newClient = new Client(username,password,uniqueCode,cnp,name,address,phone);
        Optional<Client> result = clientRepository.save(newClient);
        notifyObservers();
        return result;
    }
    public Optional<Rent>saveRent(Car car, Client client, LocalDateTime date, String note){
        //String uniqueCode = UUID.randomUUID().toString();
        //Rent newRent = new Rent(client,car,date,0,"started",note,uniqueCode);
        //Optional<Rent> result = rentRepository.save(newRent);
        carRepository.rentCarStatus(car);
        notifyObservers();
        return Optional.empty();

    }
    public Optional<Rent> deleteRent(Car car) {
        // Retrieve all rent records from the repository

        List<Car> allCars = (List<Car>) carRepository.findAll();
        Optional<Car> result;
        for (Car carc : allCars) {
            // Check if the rent record matches the given car and client

            if (carc.equals(car)) {
                // Update the car status to indicate it is available
                System.out.println(car);

                result = carRepository.returnCarStatus(car);

            }
        }
        notifyObservers();
        // If no matching rent record is found, return an empty optional
        return Optional.empty();

    }

    public Admin getAdminByUsername(String username, String password) {
        List<Admin> allAdmins = (List<Admin>) adminRepository.findAll();
        for (Admin admin : allAdmins){
            if(username.equals(admin.getUsername()) && password.equals(admin.getPassword()))
            {
                return admin;
            }
        }
        return null;
    }
    public Client getClientByUsername(String username, String password){
        List<Client> allClients = (List<Client>) clientRepository.findAll();
        for(Client client : allClients){
            if(username.equals(client.getUsername()) && password.equals(client.getPassword()))
            {
                return client;
            }
        }
        return null;
    }


    public void modifyCar(Car car, String make, String model, String fuel, String vin, Long year, Long km, String available, String color, Long seats, Long power) {
        carRepository.update(car,make,model,fuel,vin,year,km,color,seats,power);
        notifyObservers();

    }
}
