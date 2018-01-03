/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Bang Gosir
 */
public class Person implements Serializable{
    private long idPerson;
    private String name;
    private String address;
    private Date tglTerbuat;

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(long id) {
        this.idPerson = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getTglTerbuat() {
        return tglTerbuat;
    }

    public void setTglTerbuat(Date tglTerbuat) {
        this.tglTerbuat = tglTerbuat;
    }
    
    
}
