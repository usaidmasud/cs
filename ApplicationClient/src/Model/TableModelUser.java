/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Entity.User;

/**
 *
 * @author Achmad Maulana
 */
public class TableModelUser extends AbstractTableModel {
    
    private List<User> list = new ArrayList<User>();
    
    public TableModelUser() {
    }
    
    public User get(int row) {
        return list.get(row);
    }
    
    public void insert(User user) {
        list.add(user);
        fireTableDataChanged();
    }
    
    public void update(int row, User user) {
        list.set(row, user);
        fireTableDataChanged();
    }
    
    public void delete(int row) {
        list.remove(row);
        fireTableDataChanged();
    }
    
    public void setData(List<User> list) {
        this.list = list;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }
    
    @Override
    public int getColumnCount() {
        return 4;
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Id";
            case 1:
                return "Username";
            case 2:
                return "Email";
            case 3:
                return "Password";
            default:
                return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getId();
            case 1:
                return list.get(rowIndex).getUsername();
            case 2:
                return list.get(rowIndex).getEmail();
            case 3:
                return list.get(rowIndex).getPassword();
            default:
                return null;
        }
    }
    
}
