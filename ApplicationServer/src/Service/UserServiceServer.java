/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import Entity.User;
 
import Utiils.DatabaseUtilities;
 

/**
 *
 * @author Achmad Maulana
 */
public class UserServiceServer extends UnicastRemoteObject implements UserService {
    
    public UserServiceServer() throws RemoteException {
    }
    
    @Override
    public User insert(User user) throws RemoteException {
        System.out.println("Client calling insert process");
        
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO tbl_user (id, username, email, password) values (null, ?, ?, ?);";
            ps = DatabaseUtilities.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statement = DatabaseUtilities.getConnection().prepareStatement(
//                    "INSERT INTO tbl_user (id, username, email, password)"
//                    + " VALUES (null,'" + user.getUsername() + "', '" + user.getEmail() + "', MD5('" + user.getPassword() + "'))", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            Logger log = Logger.getLogger("Insert User Logger");
            DatabaseUtilities handler = new DatabaseUtilities();
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            
            resultSet.close();
            return user;
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
    public void update(User user) throws RemoteException {
        System.out.println("Client calling update process");
        PreparedStatement ps = null;
        try {
            
            ps = DatabaseUtilities.getConnection().prepareStatement(
                    " UPDATE tbl_user SET username=?"
                    + ",email=?"
                    + ",password=MD5(?)"
                    + "WHERE id=?"
            );
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getId());
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
    public void delete(Long id) throws RemoteException {
        System.out.println("Client calling delete process");
        PreparedStatement ps = null;
        try {
            ps = DatabaseUtilities.getConnection().prepareStatement(
                    "DELETE FROM tbl_user WHERE id=?");
            ps.setLong(1, id);
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
    public User getUser(Long id) throws RemoteException {
        System.out.println("Client calling getUser process");
        PreparedStatement ps = null;
        try {
            ps = DatabaseUtilities.getConnection().prepareStatement(
                    "SELECT * FROM tbl_user WHERE id= ? "
            );
            ResultSet resultSet = ps.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
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
    public List<User> getAllUser() throws RemoteException {
        System.out.println("Client calling getAllUser process");
        Statement ps = null;
        try {
            ps = DatabaseUtilities.getConnection().createStatement();
            ResultSet resultSet = ps.executeQuery("SELECT * FROM tbl_user ");
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                list.add(user);
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
