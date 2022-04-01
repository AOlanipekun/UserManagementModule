/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.usermanagementmodule;

import com.deeservices.db.connection.DatabaseConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ADEDOYIN
 */
public class User {

    private String UserName,
            Password, FName,
            LName,
            Email,
            PhoneNo,
            Gender,
            DOB,
            Nationality,
            PasswordEDate,
            SetUpDate;
    private int Permissions;

    private boolean Retired,
            Activated;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public String getPasswordEDate() {
        return PasswordEDate;
    }

    public void setPasswordEDate(String PasswordEDate) {
        this.PasswordEDate = PasswordEDate;
    }

    public String getSetUpDate() {
        return SetUpDate;
    }

    public void setSetUpDate(String SetUpDate) {
        this.SetUpDate = SetUpDate;
    }

    public int getPermissions() {
        return Permissions;
    }

    public void setPermissions(int Permissions) {
        this.Permissions = Permissions;
    }

    public boolean isRetired() {
        return Retired;
    }

    public void setRetired(boolean Retired) {
        this.Retired = Retired;
    }

    public boolean isActivated() {
        return Activated;
    }

    public void setActivated(boolean Activated) {
        this.Activated = Activated;
    }

    public JSONObject createUser(String UserName,
            String Password,
            int Permissions,
            String FName,
            String LName,
            String Email,
            String PhoneNo,
            String Gender,
            String DOB,
            String Nationality,
            String PasswordEDate,
            boolean Retired,
            boolean Activated,
            int LogOnOperatorID) throws Exception {

        JSONObject map = new JSONObject();
        DatabaseConnection dao = new DatabaseConnection();
        Connection con = dao.getConnection();
        CallableStatement cs = null;
        //System.out.println("username "+username +" password "+password);
        String sp_userlogin = "{CALL [dbo].[createusers] (?,?,?,?,?,?,?,?,?,?,?,?)} ";
        System.out.println(sp_userlogin);
        cs = con.prepareCall(sp_userlogin);
        cs.setString("username", UserName);
        cs.setString("password", Password);
        cs.setString("fname", FName);
        cs.setString("lname", LName);
        cs.setInt("permission", Permissions);
        cs.setString("email", Email);
        cs.setString("phoneno", PhoneNo);
        cs.setString("gender", Gender);
        cs.setString("dob", DOB);
        cs.setString("nationality", Nationality);
        cs.setString("passwordenddate", PasswordEDate);

//        cs.setBoolean("retired", Retired);
        cs.setBoolean("activated", Activated);
        try {
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i < columnCount + 1; i++) {
                    String name = rsmd.getColumnName(i);
                    // Do stuff with name

                    map.put(name, rs.getString(name));
                }
            }
            if (rs != null) {
                rs.close();
            }

        } finally {

            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return map;
    }

    public List<User> getUser() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        DatabaseConnection dao = new DatabaseConnection();
        Connection con = dao.getConnection();
        List<User> groupList = new ArrayList<>();

        String query = ("{call [dbo].[ViewUser]}");

        java.sql.PreparedStatement cs = con.prepareStatement(query);

        boolean result = cs.execute();
        while (!result) {
            result = cs.getMoreResults();
        }
        try {
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {
                User mr = new User();

                mr.setUserName(rs.getString("username"));
                mr.setPermissions(rs.getInt("permissions"));
                mr.setFName(rs.getString("fname"));
                mr.setLName(rs.getString("lname"));
                mr.setEmail(rs.getString("email"));
                mr.setPhoneNo(rs.getString("phoneno"));
                mr.setGender(rs.getString("gender"));
                mr.setDOB(rs.getString("dob"));
                mr.setNationality(rs.getString("nationality"));
                mr.setSetUpDate(rs.getString("setupdate"));
                mr.setActivated(rs.getBoolean("activated"));
                groupList.add(mr);
            }

            if (rs != null) {
                rs.close();
            }
        } finally {

            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return groupList;
    }

    public List< Map<String, String>> getUsernew() throws Exception {
        DatabaseConnection dao = new DatabaseConnection();
        Connection con = dao.getConnection();
        List< Map<String, String>> groupList = new ArrayList<>();

        String query = ("{call [dbo].[ViewUser]}");

        java.sql.PreparedStatement cs = con.prepareStatement(query);

        boolean result = cs.execute();
        while (!result) {
            result = cs.getMoreResults();
        }
        try {
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {

                Map<String, String> mr = new HashMap<>();

                mr.put("username", rs.getString("username"));
                mr.put("permissions", rs.getString("permissions"));
                mr.put("fname", rs.getString("fname"));
                mr.put("lname", rs.getString("lname"));
                mr.put("email", rs.getString("email"));
                mr.put("phoneno", rs.getString("phoneno"));
                mr.put("gender", rs.getString("gender"));
                mr.put("dob", rs.getString("dob"));
                mr.put("nationality", rs.getString("nationality"));
                mr.put("setupdate", rs.getString("setupdate"));
                mr.put("activated", rs.getString("activated"));
                groupList.add(mr);
            }

            if (rs != null) {
                rs.close();
            }
        } finally {

            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return groupList;
    }

    public JSONObject searchUser(String username) throws Exception {
        DatabaseConnection dao = new DatabaseConnection();
        Connection con = dao.getConnection();
        JSONObject groupList = new JSONObject();

        String query = ("{call [dbo].[FindUser] (?)}");

        CallableStatement cs = con.prepareCall(query);
        cs.setString("username", username);

        boolean result = cs.execute();
        while (!result) {
            result = cs.getMoreResults();
        }
        try {
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {

                Map<String, String> mr = new HashMap<>();
                mr.put("userid", rs.getString("userid"));
                mr.put("username", rs.getString("username"));
                mr.put("permissions", rs.getString("permissions"));
                mr.put("fname", rs.getString("fname"));
                mr.put("lname", rs.getString("lname"));
                mr.put("email", rs.getString("email"));
                mr.put("phoneno", rs.getString("phoneno"));
                mr.put("gender", rs.getString("gender"));
                mr.put("dob", rs.getString("dob"));
                mr.put("nationality", rs.getString("nationality"));
                mr.put("setupdate", rs.getString("setupdate"));
                mr.put("activated", rs.getString("activated"));
                groupList = new JSONObject(mr);
            }

            if (rs != null) {
                rs.close();
            }
        } finally {

            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return groupList;
    }

  public JSONObject editUser(String UserName,
            String Password,
            int Permissions,
            String FName,
            String LName,
            String Email,
            String PhoneNo,
            String Gender,
            String DOB,
            String Nationality,
            String PasswordEDate,
            boolean Retired,
            boolean Activated,
            int LogOnOperatorID) throws Exception {

        JSONObject map = new JSONObject();
        DatabaseConnection dao = new DatabaseConnection();
        Connection con = dao.getConnection();
        CallableStatement cs = null;
        //System.out.println("username "+username +" password "+password);
        String sp_userlogin = "{CALL [dbo].[edituser] (?,?,?,?,?,?,?,?,?,?,?,?)} ";
        System.out.println(sp_userlogin);
        cs = con.prepareCall(sp_userlogin);
        cs.setString("username", UserName);
        cs.setString("password", Password);
        cs.setString("fname", FName);
        cs.setString("lname", LName);
        cs.setInt("permission", Permissions);
        cs.setString("email", Email);
        cs.setString("phoneno", PhoneNo);
        cs.setString("gender", Gender);
        cs.setString("dob", DOB);
        cs.setString("nationality", Nationality);
        cs.setString("passwordenddate", PasswordEDate);

//        cs.setBoolean("retired", Retired);
        cs.setBoolean("activated", Activated);
        try {
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i < columnCount + 1; i++) {
                    String name = rsmd.getColumnName(i);
                    // Do stuff with name

                    map.put(name, rs.getString(name));
                }
            }
            if (rs != null) {
                rs.close();
            }

        } finally {

            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return map;
    }


    public JSONArray getUserfinal() throws Exception {
        DatabaseConnection dao = new DatabaseConnection();
        Connection con = dao.getConnection();
        JSONArray groupList = new JSONArray();

        String query = ("{call [dbo].[ViewUser]}");

        java.sql.PreparedStatement cs = con.prepareStatement(query);

        boolean result = cs.execute();
        while (!result) {
            result = cs.getMoreResults();
        }
        try {
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {

                Map<String, String> mr = new HashMap<>();
                mr.put("userid", rs.getString("userid"));
                mr.put("username", rs.getString("username"));
                mr.put("permissions", rs.getString("permissions"));
                mr.put("fname", rs.getString("fname"));
                mr.put("lname", rs.getString("lname"));
                mr.put("email", rs.getString("email"));
                mr.put("phoneno", rs.getString("phoneno"));
                mr.put("gender", rs.getString("gender"));
                mr.put("dob", rs.getString("dob"));
                mr.put("nationality", rs.getString("nationality"));
                mr.put("setupdate", rs.getString("setupdate"));
                mr.put("activated", rs.getString("activated"));
                groupList.put(mr);
            }

            if (rs != null) {
                rs.close();
            }
        } finally {

            if (cs != null) {
                cs.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return groupList;
    }

}
