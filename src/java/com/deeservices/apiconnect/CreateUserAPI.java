/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.json.JSONObject;

/**
 *
 * @author ADEDOYIN
 */
public class CreateUserAPI {

    ViewItems vi = new ViewItems();

    public HashMap<String, Object> createUser(ViewItems v) throws Exception {
        String data = "http://localhost:8080/UserManagementModule/api/createuser/";
//        List temp = new ArrayList();
        HashMap<String, Object> map = new HashMap<>();
        data = data.replaceAll(" ", "%20");
        try {

            URL obj = new URL(data);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is POST
            con.setRequestMethod("POST");

            //String charset = "UTF-8";
            //add request header
            String charset = "UTF-8";
            con.setRequestProperty("Accept-Charset", charset);
            con.setRequestProperty("Accept", "application/json");

            con.setRequestProperty("Content-Type", "application/json");

            Properties prop = new Properties();
            String sfile = "allconfig.properties";
            InputStream istream = getClass().getClassLoader().getResourceAsStream(sfile);

            if (istream != null) {
                prop.load(istream);
                String AuthPassword = prop.getProperty("AuthPassword");
//                byte[] auth = (Base64.encode("Basic:RVFxTmwxOTdXTzhPekMzak54Z0R5bTBqcTVvYTp0TV9jYVZEQ1ZqQ2Nsd2NFeGt TTHZBNTc0Tmth".getBytes()));
                String auth = (Base64.getEncoder().encodeToString(AuthPassword.getBytes()));

                con.setRequestProperty("Authorization",
                        String.format("Basic %s", (auth)));

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

                StringBuilder postData = new StringBuilder();

                JSONObject jsonIn = new JSONObject(mBodies);
//            postData.append('{');
//            for (Map.Entry<String, Object> mBody : mBodies.entrySet()) {
//
//                postData.append(URLEncoder.encode(mBody.getKey(), "UTF-8"));
//                postData.append(':');
//                postData.append(URLEncoder.encode(String.valueOf(mBody.getValue()), "UTF-8"));
//                postData.append(',');
//
//            }
//            postData.append('}');
//            byte[] postDataBytes = postData.toString().replace(",}", "}").getBytes("UTF-8");
                byte[] postDataBytes = jsonIn.toString().getBytes("UTF-8");

                con.setDoOutput(true);
                con.getOutputStream().write(postDataBytes);
                con.getOutputStream().flush();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + data);
                System.out.println("Response Code : " + responseCode);
                if (responseCode != 200) {
                    map.put("Errormessage", responseCode);

                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JsonReader jsonReaderOutput = Json.createReader(new StringReader(response.toString()));

                    JsonObject json = jsonReaderOutput.readObject();
                    map.put("Errormessage", json.getString("StatusResponse"));

                }
            } else {
                String sresponse = "{\"StatusCode\": \"94\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"Incorrect Authorization\"}";
                map.put("Errormessage", sresponse);

            }
//            temp.add(map);

        } catch (Exception ex) {
            ex.printStackTrace();
            map.put("Errormessage", ex.getMessage());

        }

        return map;
    }

}
