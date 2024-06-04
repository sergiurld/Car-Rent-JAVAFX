package org.example.faza222.repository;


import org.example.faza222.domain.Car;

import java.util.Optional;

public interface CarRepositoryInterface extends Repository<Long, Car>{
    Optional<Car> update(Car entity, String make, String model, String fuel, String vin, Long year, Long km, String color, Long seats, Long power);

    public Long findCarUniqueCode(String uniqueCode);

}
