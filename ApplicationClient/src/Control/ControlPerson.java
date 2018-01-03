/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entity.Person;
import Model.TableModelPerson;
import Service.PersonService;
import View.FormPerson;
import View.FormPersonInput;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Bang Gosir
 */
public class ControlPerson {
    FormPerson form;
    FormPersonInput formInput;
    PersonService service;
    List<Person> record;
    TableModelPerson tableModel = new TableModelPerson();
    int indexTable = -1;
    long idPerson = 0;

    public ControlPerson(FormPerson form) throws RemoteException, NotBoundException {
        this.form = form;
        String host = "localhost";
        int port = 6789;
        Registry client = LocateRegistry.getRegistry(host, port);
        service = (PersonService) client.lookup("servicePerson");
    }
    
    public void statusAwal() {
        loadData();
        actionSearch();
        form.getTextCari().setText("");
    }
    
    private void loadData() {
        try {
            record = service.getList();
            tableModel.setData(record);
            form.getTable().setModel(tableModel);
        } catch (RemoteException ex) {
            Logger.getLogger(ControlPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void kosongInput() {
        formInput.getTextAddress().setText("");
        formInput.getTextName().setText("");
    }
    
    private void isiField(int index) {
        formInput.getTextName().setText(record.get(index).getName());
        formInput.getTextAddress().setText(record.get(index).getAddress());
        idPerson = record.get(index).getIdPerson();
    }
    
    public void ButtonTambah() {
        formInput = new FormPersonInput(this);
        formInput.setTitle("INPUT PERSON");
        formInput.getBtnSimpan().setText("SIMPAN");
        kosongInput();
        formInput.getTextName().requestFocus();
        formInput.setVisible(true);
    }

    public void ButtonSimpan() {
        String abc = formInput.getBtnSimpan().getText();
        if (abc.equalsIgnoreCase("SIMPAN")) 
        {
            try {
                Person obj = new Person();
                obj.setName(formInput.getTextName().getText());
                obj.setAddress(formInput.getTextAddress().getText());
                obj.setTglTerbuat(new Date());
                Person ps = service.insert(obj);
                tableModel.insert(ps);
                loadData();
                formInput.dispose();
            } catch (RemoteException ex) {
                Logger.getLogger(ControlPerson.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Person obj = new Person();
                obj.setIdPerson(idPerson);
                obj.setName(formInput.getTextName().getText());
                obj.setAddress(formInput.getTextAddress().getText());
                obj.setTglTerbuat(new Date());
                service.update(obj);
                tableModel.update(indexTable, obj);
                loadData();
                formInput.dispose();
            } catch (RemoteException ex) {
                Logger.getLogger(ControlPerson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void actionSearch() {
        TableRowSorter rowSorter = new TableRowSorter(tableModel);
        form.getTextCari().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                form.getTable().setRowSorter(rowSorter);
                rowSorter.setRowFilter(RowFilter.regexFilter(form.getTextCari().getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                form.getTable().setRowSorter(rowSorter);
                rowSorter.setRowFilter(RowFilter.regexFilter(form.getTextCari().getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                form.getTable().setRowSorter(rowSorter);
                rowSorter.setRowFilter(RowFilter.regexFilter(form.getTextCari().getText()));
            }
        });
    }

    public void ButtonUbah() {
        indexTable = form.getTable().getSelectedRow();
        if (indexTable >= 0) {
            formInput = new FormPersonInput(this);
            formInput.setTitle("UPDATE PERSON");
            formInput.getBtnSimpan().setText("UPDATE");
            isiField(indexTable);
            formInput.getTextName().requestFocus();
            formInput.setVisible(true);
        }
    }

    public void ButtonHapus() {
        indexTable = form.getTable().getSelectedRow();
        if (indexTable >= 0) {
            try {
                idPerson = record.get(indexTable).getIdPerson();
                service.delete(idPerson);
                tableModel.delete(indexTable);
                loadData();
            } catch (RemoteException ex) {
                Logger.getLogger(ControlPerson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
