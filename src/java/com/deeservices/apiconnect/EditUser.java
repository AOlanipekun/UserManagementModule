/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ADEDOYIN
 */
@ManagedBean(name = "edit")
@RequestScoped
public class EditUser implements Serializable {

    public ViewItems vi = new ViewItems();

    private List<ViewItems> disList = new ArrayList<>();
    private String username;

    public ViewItems getVi() {
        return vi;
    }

    public void setVi(ViewItems vi) {
        this.vi = vi;
    }

    public List<ViewItems> getDisList() {
        return disList;
    }

    public void setDisList(List<ViewItems> disList) {
        this.disList = disList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String searchRecord() {
        String sResponse = "";
        HashMap<String, Object> map = new HashMap<>();

        try {
            UserApi service = new UserApi();
            if ("".equals(vi.getUserName())) {
                System.err.println("Username is Empty");
            } else if ((vi.getUserName()) == null) {
                System.err.println("Username is Empty");
            } else {
                System.err.println(vi.getUserName());
                disList = service.viewUseritems("find", "{\"username\":\"" + vi.getUserName() + "\"}");
                for (int i = 0; i < disList.size(); i++) {
                    this.vi = disList.get(i);
                    setVi(disList.get(i));

                }
                if (!"success".equals(vi.getsResponse())) {

                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("responsemessage", vi.getsResponse());
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("styleclass", "alert_error");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sResponse = "An error occured";

            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("responsemessage", ex.getMessage());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("styleclass", "alert_error");
        }
        return sResponse;
    }

    public String editRecord() {
        String sResponse = "";
        HashMap<String, Object> map = new HashMap<>();
        try {
            UserApi service = new UserApi();
            if (vi == null) {
                System.err.println("List is Empty");

                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("responsemessage", "List is empty");
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("styleclass", "alert_error");
            } else {
                System.err.println(vi.getUserName());
                vi.setActivated("1");
                vi.setRetired("0");
                vi.setPasswordEDate("12/12/2028");
                vi.setLogOnOperatorID("1");
                disList = service.viewUseritems("edit", new ViewItems().getobjectToString(vi));
                for (int i = 0; i < disList.size(); i++) {
                    this.vi = disList.get(i);
                }
                if (!"success".equals(vi.getsResponse())) {

                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("responsemessage", vi.getsResponse());
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("styleclass", "alert_error");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sResponse = "An error occured";
        }
        return sResponse;
    }

}
