package org.example.faza222.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Rent  extends Entity<Long>{
    private Client client;
    private Car car;
    private LocalDateTime startDate;
    private double price;
    private String rentalStatus;
    private String note;
    private String uniqueCode;

    public Rent(Client client, Car car, LocalDateTime startDate, double price, String rentalStatus, String note, String uniqueCode) {
        this.client = client;
        this.car = car;
        this.startDate = startDate;
        this.price = price;
        this.rentalStatus = rentalStatus;
        this.note = note;
        this.uniqueCode = uniqueCode;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "client=" + client +
                ", car=" + car +
                ", startDate=" + startDate +
                ", price=" + price +
                ", rentalStatus='" + rentalStatus + '\'' +
                ", note='" + note + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rent rent = (Rent) o;
        return Objects.equals(uniqueCode, rent.uniqueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uniqueCode);
    }

}
