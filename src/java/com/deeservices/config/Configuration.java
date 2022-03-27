/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deeservices.config;

import java.util.prefs.Preferences;

/**
 *
 * @author user
 */
public enum Configuration {
    INSTANCE;
    private final Preferences prefs = Preferences.systemRoot().node(this.getClass().getName());

    public String getJDBCIMaxConnection() {
        if (prefs.get("JDBCMICAConnection", "").equalsIgnoreCase("")) {
            prefs.put("JDBCMICAConnection", "jdbc/mica");
        }

        System.out.println(prefs.get("JDBCMICAConnection", ""));
        return prefs.get("JDBCMICAConnection", "");
    }

}
