/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Entity.Person;
import java.text.SimpleDateFormat;

/**
 *
 * @author Achmad Maulana
 */
public class TableModelPerson extends AbstractTableModel {
    
    List<Person> list = new ArrayList<Person>();

    public TableModelPerson() {
    }

    public Person get(int row) {
        return list.get(row);
    }
    
    public void insert(Person user) {
        list.add(user);
        fireTableDataChanged();
    }
    
    public void update(int row, Person user) {
        list.set(row, user);
        fireTableDataChanged();
    }
    
    public void delete(int row) {
        list.remove(row);
        fireTableDataChanged();
    }
    
    public void setData(List<Person> list) {
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
                return "Name";
            case 2:
                return "Address";
            case 3:
                return "Date";
            default:
                return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        switch (columnIndex) {
            case 0:
                return rowIndex+1;
            case 1:
                return list.get(rowIndex).getName();
            case 2:
                return list.get(rowIndex).getAddress();
            case 3:
                return sdf.format(list.get(rowIndex).getTglTerbuat());
            default:
                return null;
        }
    }
    
}
