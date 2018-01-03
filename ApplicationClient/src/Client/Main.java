/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.SwingUtilities;
import View.FormUser;
import Service.UserService;

/**
 *
 * @author Achmad Maulana
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, NotBoundException {
//        Registry client = LocateRegistry.getRegistry("10.152.59.40", 6789);
        String host = "localhost";
        int port = 6789;
        Registry client = LocateRegistry.getRegistry(host, port);
        UserService service = (UserService) client.lookup("serviceUser");
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                FormUser formUser= new FormUser(service);
                formUser.setVisible(true);
            }
        });
    }
    
}
