/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Achmad Maulana
 */
public interface UserService extends Remote {

    User insert(User user) throws RemoteException;

    void update(User user) throws RemoteException;

    void delete(Long id) throws RemoteException;

    User getUser(Long id) throws RemoteException;

    List<User> getAllUser() throws RemoteException;
}
