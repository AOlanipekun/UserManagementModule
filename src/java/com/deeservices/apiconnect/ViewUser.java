/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import com.deeservices.usermanagementmodule.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ADEDOYIN
 */
@ManagedBean(name = "jsonViews")
@ViewScoped
public class ViewUser implements Serializable {

    private boolean isDisplayCreateGroupPanel, isDisplayEditGroupPanel, isDisplayViewGroupPanel;
    private List<ViewItems> disList = new ArrayList<>();

    public boolean isIsDisplayCreateGroupPanel() {
        return isDisplayCreateGroupPanel;
    }

    public void setIsDisplayCreateGroupPanel(boolean isDisplayCreateGroupPanel) {
        this.isDisplayCreateGroupPanel = isDisplayCreateGroupPanel;
    }

    public boolean isIsDisplayEditGroupPanel() {
        return isDisplayEditGroupPanel;
    }

    public void setIsDisplayEditGroupPanel(boolean isDisplayEditGroupPanel) {
        this.isDisplayEditGroupPanel = isDisplayEditGroupPanel;
    }

    public boolean isIsDisplayViewGroupPanel() {
        return isDisplayViewGroupPanel;
    }

    public void setIsDisplayViewGroupPanel(boolean isDisplayViewGroupPanel) {
        this.isDisplayViewGroupPanel = isDisplayViewGroupPanel;
    }

    public void showEditGroupPanel() {
        this.setIsDisplayEditGroupPanel(true);
    }

    public void showViewGroupPanel() {
        this.setIsDisplayViewGroupPanel(true);
    }

    public void hideAllPanels() {
        this.setIsDisplayEditGroupPanel(false);
        this.setIsDisplayViewGroupPanel(false);
    }

    public List<HashMap<String, Object>> getAll() {
        List<HashMap<String, Object>> age = null;
        try {
            age = new ViewUserApi().viewUser();
        } catch (Exception e) {
        }
        return age;
    }

    public List<ViewItems> getDisList() {
        return disList;
    }

    public void setDisList(List<ViewItems> disList) {
        this.disList = disList;
    }

    @PostConstruct
    public void init() {
        try {
            ViewUserApi service = new ViewUserApi();
            disList = service.viewUseritems();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
