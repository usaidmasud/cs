/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Service.PersonServiceServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import Service.UserServiceServer;

/**
 *
 * @author Achmad Maulana
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        // TODO code application logic here
        Registry server = LocateRegistry.createRegistry(6789);
        UserServiceServer userService = new UserServiceServer();
        server.rebind("serviceUser", userService);
        
        PersonServiceServer personService = new PersonServiceServer();
        server.rebind("servicePerson", personService);
        
        System.out.println("Server berjalan");

    }
    
}
