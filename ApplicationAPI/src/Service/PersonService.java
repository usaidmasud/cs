/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Person;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Bang Gosir
 */
public interface PersonService extends Remote{
    Person insert (Person obj) throws RemoteException;
    void update (Person obj) throws RemoteException;
    void delete (long id) throws RemoteException;
    Person getPerson (String keyWord) throws RemoteException;
    List<Person> getList () throws RemoteException;
}
