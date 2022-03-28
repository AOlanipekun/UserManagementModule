/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deeservices.db.connection;

import com.deeservices.config.Configuration;
import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author user
 */
public class DatabaseConnection {

    public java.sql.Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(Configuration.INSTANCE.getJDBCUMMConnection());
        return ds.getConnection();
    }

}
