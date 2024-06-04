package org.example.faza222.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.faza222.JdbcUtils;
import org.example.faza222.domain.Car;
import org.example.faza222.domain.Client;
import org.example.faza222.domain.Rent;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class RentRepository implements RentRepositoryInterface{

    public JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private ClientRepository clientRepository ;
    private CarRepository carRepository;
    public RentRepository(Properties properties){
        logger.info("Initializing AdminDBRerpository with properties {}",properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public Optional<Rent> findOne(Long id) {
        logger.traceEntry("Find Rent by ID: {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Rent WHERE id = ?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long clientId = result.getLong("client_id");
                    Long carId = result.getLong("car_id");
                    String startDate1 = result.getString("startDate");
                    double price = result.getDouble("price");
                    String rentalStatus = result.getString("resultStatus");
                    String note = result.getString("note");
                    String uniqueCode = result.getString("uniqueCode");
                    LocalDateTime startDate = LocalDateTime.parse(startDate1);

                    Optional<Client> client = clientRepository.findOne(clientId);
                    Optional<Car> car = carRepository.findOne(carId);

                    // Check if both flight and client are present
                    if (client.isPresent() && car.isPresent()) {
                        Rent rent = new Rent(client.get(), car.get(),startDate,price,rentalStatus,note,uniqueCode);
                        rent.setId(id);
                        logger.traceExit(rent);
                        return Optional.of(rent);
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Error while finding ticket by ID: {}", id, ex);
        }
        logger.traceExit("Ticket not found with ID: {}", id);
        return Optional.empty();

    }

    @Override
    public Iterable<Rent> findAll() {
        logger.traceEntry("Get all rents");
        Connection con = dbUtils.getConnection();
        ArrayList<Rent> listRent = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Rent")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("id");
                    Long clientId = result.getLong("client_id");
                    Long carId = result.getLong("car_id");
                    String startDate1 = result.getString("startDate");
                    double price = result.getDouble("price");
                    String rentalStatus = result.getString("resultStatus");
                    String note = result.getString("note");
                    String uniqueCode = result.getString("uniqueCode");
                    LocalDateTime startDate = LocalDateTime.parse(startDate1);

                    Optional<Client> client = clientRepository.findOne(clientId);
                    Optional<Car> car = carRepository.findOne(carId);

                    // Check if both flight and client are present
                    if (client.isPresent() && car.isPresent()) {
                        Rent rent = new Rent(client.get(), car.get(),startDate,price,rentalStatus,note,uniqueCode);
                        rent.setId(id);
                        listRent.add(rent);

                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("Error BD "+ex);
        }
        logger.traceExit(listRent);
        return listRent;
    }

    @Override
    public Optional<Rent> save(Rent entity) {
    logger.traceEntry("Saving Rent{}",entity);
    Connection con = dbUtils.getConnection();

    Long clientId = clientRepository.findClientUniqueCode(entity.getClient().getUniqueCode());
    Long carId = carRepository.findCarUniqueCode(entity.getCar().getuniqueCode());

    try(PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Rent(client_id,car_id,startDate,price,rentalStatus,note,uniqueCode) values (?,?,?,?,?,?,?)"))
    {
        preparedStatement.setLong(1,clientId);
        preparedStatement.setLong(2,carId);
        preparedStatement.setString(3,String.valueOf(entity.getStartDate()));
        preparedStatement.setDouble(4,entity.getPrice());
        preparedStatement.setString(5, entity.getRentalStatus());
        preparedStatement.setString(6, entity.getNote());
        preparedStatement.setString(7, entity.getUniqueCode());

        int result = preparedStatement.executeUpdate();
        logger.trace("Saved {} instances",result);
        return Optional.of(entity);
    }
    catch (SQLException ex) {
        logger.error(ex);
        System.err.println("Error DB " + ex);
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<Rent> delete(Rent entity) {
        logger.traceEntry("Deleting Rent: {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Admin WHERE uniqueCode = ?")) {
            preStmt.setString(1, entity.getUniqueCode());

            int rowsDeleted = preStmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.trace("Rent {} deleted successfully", entity);
                // Return the deleted Rent entity if needed
                return Optional.of(entity);
            } else {
                logger.warn("No Rent found with username: {}", entity.getUniqueCode());
                return Optional.empty();
            }
        } catch (SQLException ex) {
            logger.error("Error while deleting Rent: {}", entity, ex);
            System.err.println("Error DB " + ex);
            return Optional.empty();
        }       }

    @Override
    public Optional<Rent> update(Rent entity) {
        return Optional.empty();
    }
}
