package org.example.faza222.domain;

import java.util.Objects;

public class Car  extends Entity<Long>{
    private String uniqueCode;
    private String carMake;
    private String carModel;
    private String fuel;
    private String vin;
    private Long year;
    private Long km;
    private String status;
    private String color;
    private Long numberOfSeats;
    private Long power;

    public Car(String uniqueCode, String carMake, String carModel, String fuel, String vin, Long year, Long km, String status, String color, Long numberOfSeats, Long power) {
        this.uniqueCode = uniqueCode;
        this.carMake = carMake;
        this.carModel = carModel;
        this.fuel = fuel;
        this.vin = vin;
        this.year = year;
        this.km = km;
        this.status = status;
        this.color = color;
        this.numberOfSeats = numberOfSeats;
        this.power = power;
    }

    public String getuniqueCode() {
        return uniqueCode;
    }

    public void setuniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getKm() {
        return km;
    }

    public void setKm(Long km) {
        this.km = km;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Car{" +
                "uniqueCode='" + uniqueCode + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", fuel='" + fuel + '\'' +
                ", vin='" + vin + '\'' +
                ", year=" + year +
                ", km=" + km +
                ", status='" + status + '\'' +
                ", color='" + color + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", power=" + power +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return Objects.equals(uniqueCode, car.uniqueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uniqueCode);
    }

}
