/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ADEDOYIN
 */
@ManagedBean(name = "create")
@RequestScoped
public class CreateUser implements Serializable {

    public ViewItems vi;

    public ViewItems getVi() {
        return vi;
    }

    public void setVi(ViewItems vi) {
        this.vi = vi;
    }

    public String createRecord() {
        String sResponse = "";
        HashMap<String, Object> map = new HashMap<>();
        try {
            CreateUserAPI service = new CreateUserAPI();
            service.vi = vi;
            map = service.createUser(vi);

            if (map.size() > 0) {
                sResponse = map.get("Errormessage").toString();
            } else {
                sResponse = "Record not saved";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sResponse = "An error occured";
        }
        return sResponse;
    }

    public String searchRecord() {
        String sResponse = "";
        HashMap<String, Object> map = new HashMap<>();
        try {
            CreateUserAPI service = new CreateUserAPI();
            service.vi = vi;
            map = service.createUser(vi);

            if (map.size() > 0) {
                sResponse = map.get("Errormessage").toString();
            } else {
                sResponse = "Record not saved";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sResponse = "An error occured";
        }
        return sResponse;
    }

    public String editRecord() {
        String sResponse = "";
        HashMap<String, Object> map = new HashMap<>();
        try {
            CreateUserAPI service = new CreateUserAPI();
            service.vi = vi;
            map = service.createUser(vi);

            if (map.size() > 0) {
                sResponse = map.get("Errormessage").toString();
            } else {
                sResponse = "Record not saved";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sResponse = "An error occured";
        }
        return sResponse;
    }

}
