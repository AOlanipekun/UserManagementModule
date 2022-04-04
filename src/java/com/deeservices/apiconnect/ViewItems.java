/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.json.JsonObject;
import org.json.JSONObject;

/**
 *
 * @author ADEDOYIN
 */
public class ViewItems {

    private String UserName,
            Password, FName,
            LName,
            Email,
            PhoneNo,
            Gender,
            DOB,
            Nationality,
            PasswordEndDate,
            SetUpDate, Permissions, userid, Retired,
            Activated, LogOnOperatorID, sResponse;

    public String getsResponse() {
        return sResponse;
    }

    public void setsResponse(String sResponse) {
        this.sResponse = sResponse;
    }


    public String getUserName() {
        return UserName;
    }

    public String getLogOnOperatorID() {
        return LogOnOperatorID;
    }

    public void setLogOnOperatorID(String LogOnOperatorID) {
        this.LogOnOperatorID = LogOnOperatorID;
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
        return PasswordEndDate;
    }

    public void setPasswordEDate(String PasswordEDate) {
        this.PasswordEndDate = PasswordEDate;
    }

    public String getSetUpDate() {
        return SetUpDate;
    }

    public void setSetUpDate(String SetUpDate) {
        this.SetUpDate = SetUpDate;
    }

    public String getPermissions() {
        return Permissions;
    }

    public void setPermissions(String Permissions) {
        this.Permissions = Permissions;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRetired() {
        return Retired;
    }

    public void setRetired(String Retired) {
        this.Retired = Retired;
    }

    public String getActivated() {
        return Activated;
    }

    public void setActivated(String Activated) {
        this.Activated = Activated;
    }

   
    public ViewItems getobject(JsonObject json) {

        ViewItems vi = new ViewItems();
        vi.setFName(json.getString("fname"));
        vi.setLName(json.getString("lname"));
        vi.setUserName(json.getString("username"));
        vi.setPermissions(json.getString("permissions"));
        vi.setEmail(json.getString("email"));
        vi.setPhoneNo(json.getString("phoneno"));
        vi.setGender(json.getString("gender"));
        vi.setDOB(json.getString("dob"));
        vi.setNationality(json.getString("nationality"));
        vi.setSetUpDate(json.getString("setupdate"));
        vi.setActivated(json.getString("activated"));
        vi.setUserid(json.getString("userid"));
        vi.setsResponse("Success");
        return vi;
    }

    public String getobjectToString(ViewItems v) {

        //START::::Add the POST body content
        Map<String, Object> mBodies = new LinkedHashMap<>();

        mBodies.put("username", v.getUserName());
        mBodies.put("password", v.getPassword());
        mBodies.put("permissions", v.getPermissions());
        mBodies.put("fname", v.getFName());
        mBodies.put("lname", v.getLName());
        mBodies.put("email", v.getEmail());
        mBodies.put("phoneno", v.getPhoneNo());
        mBodies.put("gender", v.getGender());
        mBodies.put("dob", v.getDOB());
        mBodies.put("nationality", v.getNationality());
        mBodies.put("activated", v.getActivated());
        mBodies.put("retired", v.getRetired());
        mBodies.put("passwordenddate", v.getPasswordEDate());

        mBodies.put("LogOnOperatorID", v.getLogOnOperatorID());

        JSONObject jsonIn = new JSONObject(mBodies);
        return jsonIn.toString();
    }


}
