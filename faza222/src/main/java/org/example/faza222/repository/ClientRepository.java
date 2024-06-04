package org.example.faza222.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.faza222.JdbcUtils;
import org.example.faza222.domain.Client;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class ClientRepository implements ClientRepositoryInterface{
    public JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public ClientRepository(Properties properties){
        logger.info("Initializing clientDBRerpository with properties {}",properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public Optional<Client> findOne(Long id) {
        logger.traceEntry("Find Client by id:{},id");
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from Client where id = ?"))
        {
            preparedStatement.setLong(1,id);
            try(ResultSet result = preparedStatement.executeQuery())
            {
                while (result.next())
                {
                    String  username= result.getString("username");
                    String  password= result.getString("password");
                    String  uniqueCode= result.getString("uniqueCode");
                    String  cnp= result.getString("cnp");
                    String  name= result.getString("name");
                    String  address= result.getString("address");
                    String  phone= result.getString("phone");
                    Client client = new Client(username,password,uniqueCode,cnp,name,address,phone);
                    client.setId(id);
                    logger.traceExit(client);
                    return Optional.of(client);
                }
            }
        }
        catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return Optional.empty();    }

    @Override
    public Iterable<Client> findAll() {
        logger.traceEntry("Get all Clients");
        Connection con = dbUtils.getConnection();
        ArrayList<Client> listClient = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from Client"))
        {
            try(ResultSet result = preparedStatement.executeQuery())
            {
                while(result.next())
                {   Long id = result.getLong("id");
                    String  username= result.getString("username");
                    String  password= result.getString("password");
                    String  uniqueCode= result.getString("uniqueCode");
                    String  cnp= result.getString("cnp");
                    String  name= result.getString("name");
                    String  address= result.getString("address");
                    String  phone= result.getString("phone");
                    Client client = new Client(username,password,uniqueCode,cnp,name,address,phone);
                    client.setId(id);
                    listClient.add(client);
                }
            }
        }
        catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(listClient);
        return listClient;
    }

    @Override
    public Optional<Client> save(Client entity) {
        logger.traceEntry("Saving Client {}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Client(username,password,uniqueCode,cnp,name,address,phone)values(?,?,?,?,?,?,?)"))
        {
            preparedStatement.setString(1,entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3,entity.getUniqueCode());
            preparedStatement.setString(4,entity.getCnp());
            preparedStatement.setString(5,entity.getName());
            preparedStatement.setString(6,entity.getAddress());
            preparedStatement.setString(7,entity.getPhone());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} Client instances",result);
            return Optional.of(entity);

        }
        catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }
        logger.traceExit();
        return Optional.empty();    }

    @Override
    public Optional<Client> delete(Client entity) {
        logger.traceEntry("Deleting Client: {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Client WHERE username = ?")) {
            preStmt.setString(1, entity.getUsername());

            int rowsDeleted = preStmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.trace("Client {} deleted successfully", entity);
                // Return the deleted Agent entity if needed
                return Optional.of(entity);
            } else {
                logger.warn("No Client found with username: {}", entity.getUsername());
                return Optional.empty();
            }
        } catch (SQLException ex) {
            logger.error("Error while deleting Client: {}", entity, ex);
            System.err.println("Error DB " + ex);
            return Optional.empty();
        }       }

    @Override
    public Optional<Client> update(Client entity) {
        return Optional.empty();
    }

    @Override
    public Long findClientUniqueCode(String uniqueCode) {
        logger.traceEntry("Find Client by uniqueCode{}",uniqueCode);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select id from Client where uniqueCode = ?"))
        {
            preparedStatement.setString(1,uniqueCode);
            try(ResultSet result = preparedStatement.executeQuery())
            {
                if(result.next())
                {   Long id = result.getLong("id");
                    return id;
                }
            }
        }
        catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }
}
