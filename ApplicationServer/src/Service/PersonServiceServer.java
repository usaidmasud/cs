/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Person;
import Utiils.DatabaseUtilities;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author Bang Gosir
 */
public class PersonServiceServer extends UnicastRemoteObject implements PersonService{

    public PersonServiceServer() throws RemoteException {
        super();
    }
    
    @Override
    public Person insert(Person obj) throws RemoteException {
        System.out.println("Client calling insert process");
        
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO person (idPerson, name, address, tglTerbuat) values (null, ?, ?, null);";
            ps = DatabaseUtilities.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getAddress());
            Logger log = Logger.getLogger("Insert Person Logger");
            DatabaseUtilities handler = new DatabaseUtilities();
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                obj.setIdPerson(resultSet.getLong(1));
            }
            
            resultSet.close();
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void update(Person obj) throws RemoteException {
        System.out.println("Client calling update process");
        PreparedStatement ps = null;
        try {
            
            ps = DatabaseUtilities.getConnection().prepareStatement(
                    " UPDATE person SET name = ?, address = ? "
                    + "WHERE idPerson=?"
            );
            
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getAddress());
            ps.setLong(3, obj.getIdPerson());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(long id) throws RemoteException {
        System.out.println("Client calling delete process");
        PreparedStatement ps = null;
        try {
            ps = DatabaseUtilities.getConnection().prepareStatement(
                    "DELETE FROM person WHERE idPerson = '"+id+"';");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Person getPerson(String keyWord) throws RemoteException {
        System.out.println("Client calling getUser process");
        PreparedStatement ps = null;
        try {
            ps = DatabaseUtilities.getConnection().prepareStatement(
                    "SELECT * FROM tbl_user WHERE id= ? "
            );
            ResultSet rs = ps.executeQuery();
            Person user = null;
            if (rs.next()) {
                user = new Person();
                user.setIdPerson(rs.getInt("idPerson"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setTglTerbuat(rs.getDate("tglTerbuat"));
            }
            
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Person> getList() throws RemoteException {
        System.out.println("Client calling getRecordPerson process");
        Statement ps = null;
        try {
            ps = DatabaseUtilities.getConnection().createStatement();
            ResultSet rs = ps.executeQuery("SELECT * FROM person");
            List<Person> list = new ArrayList<>();
            while (rs.next()) {
                Person obj = new Person();
                obj.setIdPerson(rs.getLong("idPerson"));
                obj.setName(rs.getString("name"));
                obj.setAddress(rs.getString("address"));
                obj.setTglTerbuat(rs.getDate("tglTerbuat"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
