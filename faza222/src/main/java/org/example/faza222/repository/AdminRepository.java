package org.example.faza222.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.faza222.JdbcUtils;
import org.example.faza222.domain.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class AdminRepository implements AdminRepositoryInterface{
    public JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public AdminRepository(Properties properties){
        logger.info("Initializing AdminDBRerpository with properties {}",properties);
        dbUtils = new JdbcUtils(properties);
    }
    @Override
    public Optional<Admin> findOne(Long id) {
        logger.traceEntry("Find Admin by id:{},id");
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from Admin where id = ?"))
        {
            preparedStatement.setLong(1,id);
            try(ResultSet result = preparedStatement.executeQuery())
            {
                if(result.next())
                {
                 String  username= result.getString("username");
                 String  password= result.getString("password");
                 String  uniqueCode= result.getString("uniqueCode");
                 String  cnp= result.getString("cnp");
                 String  name= result.getString("name");
                 String  address= result.getString("address");
                 String  phone= result.getString("phone");
                 Admin admin = new Admin(username,password,uniqueCode,cnp,name,address,phone);
                 admin.setId(id);
                 logger.traceExit(admin);
                 return Optional.of(admin);
                }
            }
        }
        catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Iterable<Admin> findAll() {
        logger.traceEntry("Get all Admins");
        Connection con = dbUtils.getConnection();
        ArrayList<Admin> listAdmin = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from Admin"))
        {
            try(ResultSet result = preparedStatement.executeQuery())
            {
                while (result.next())
                {   Long id = result.getLong("id");
                    String  username= result.getString("username");
                    String  password= result.getString("password");
                    String  uniqueCode= result.getString("uniqueCode");
                    String  cnp= result.getString("cnp");
                    String  name= result.getString("name");
                    String  address= result.getString("address");
                    String  phone= result.getString("phone");
                    Admin admin = new Admin(username,password,uniqueCode,cnp,name,address,phone);
                    admin.setId(id);
                    listAdmin.add(admin);
                }
            }
        }
        catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit(listAdmin);
        return listAdmin;

    }

    @Override
    public Optional<Admin> save(Admin entity) {
        logger.traceEntry("Saving Admin {}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into Admin(username,password,uniqueCode,cnp,name,address,phone)values(?,?,?,?,?,?,?)"))
        {
            preparedStatement.setString(1,entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3,entity.getUniqueCode());
            preparedStatement.setString(4,entity.getCnp());
            preparedStatement.setString(5,entity.getName());
            preparedStatement.setString(6,entity.getAddress());
            preparedStatement.setString(7,entity.getPhone());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} Admin instances",result);
            return Optional.of(entity);

        }
        catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }
        logger.traceExit();
        return Optional.empty();

    }

    @Override
    public Optional<Admin> delete(Admin entity) {
        logger.traceEntry("Deleting Admin: {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM Admin WHERE username = ?")) {
            preStmt.setString(1, entity.getUsername());

            int rowsDeleted = preStmt.executeUpdate();
            if (rowsDeleted > 0) {
                logger.trace("Admin {} deleted successfully", entity);
                // Return the deleted Agent entity if needed
                return Optional.of(entity);
            } else {
                logger.warn("No Admin found with username: {}", entity.getUsername());
                return Optional.empty();
            }
        } catch (SQLException ex) {
            logger.error("Error while deleting Admin: {}", entity, ex);
            System.err.println("Error DB " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Admin> update(Admin entity) {
        return Optional.empty();
    }

    @Override
    public Long findAdminUniqueCode(String uniqueCode) {
        logger.traceEntry("Find Admin by uniqueCode{}",uniqueCode);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select id from Admin where uniqueCode = ?"))
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
