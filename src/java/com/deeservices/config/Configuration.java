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

    public String getJDBCUMMConnection() {
        if (prefs.get("JDBCUMMConnection", "").equalsIgnoreCase("")) {
            prefs.put("JDBCUMMConnection", "jdbc/umm");
        }

        System.out.println(prefs.get("JDBCUMMConnection", ""));
        return prefs.get("JDBCUMMConnection", "");
    }

}
