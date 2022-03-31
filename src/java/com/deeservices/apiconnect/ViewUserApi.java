/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import org.kohsuke.rngom.parse.host.Base;

/**
 *
 * @author ADEDOYIN
 */
public class ViewUserApi {

    public List<ViewItems> viewUseritems() throws Exception {
        String data = "http://localhost:8080/UserManagementModule/api/viewusers/";
        List temp = new ArrayList();
        HashMap<String, Object> map = new HashMap<>();
        data = data.replaceAll(" ", "%20");
        try {

            URL obj = new URL(data);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("POST");

            //String charset = "UTF-8";
            //add request header
            String charset = "UTF-8";
            con.setRequestProperty("Accept-Charset", charset);
            byte[] auth = (Base64.encode("Basic:RVFxTmwxOTdXTzhPekMzak54Z0R5bTBqcTVvYTp0TV9jYVZEQ1ZqQ2Nsd2NFeGt TTHZBNTc0Tmth".getBytes()));

            con.setRequestProperty("Authorization",
                    String.format("Basic %s", new String(auth)));

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'Get' request to URL : " + data);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonReader jsonReaderOutput = Json.createReader(new StringReader(response.toString()));
            JsonArray jsonArray = jsonReaderOutput.readArray();
            System.out.println("response size" + jsonArray.size());

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject json = jsonArray.getJsonObject(i);
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
                temp.add(vi);
//                map.put("errormessage", json.getString("statusResponse"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return temp;
    }

    public List<HashMap<String, Object>> viewUser() throws Exception {
        String data = "http://localhost:8080/UserManagementModule/api/viewusers/";
        List temp = new ArrayList();
        HashMap<String, Object> map = new HashMap<>();
        data = data.replaceAll(" ", "%20");
        try {

            URL obj = new URL(data);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("POST");

            //String charset = "UTF-8";
            //add request header
            String charset = "UTF-8";
            con.setRequestProperty("Accept-Charset", charset);
            byte[] auth = (Base64.encode("Basic:RVFxTmwxOTdXTzhPekMzak54Z0R5bTBqcTVvYTp0TV9jYVZEQ1ZqQ2Nsd2NFeGt TTHZBNTc0Tmth".getBytes()));

            con.setRequestProperty("Authorization",
                    String.format("Basic %s", new String(auth)));

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + data);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonReader jsonReaderOutput = Json.createReader(new StringReader(response.toString()));
            JsonArray jsonArray = jsonReaderOutput.readArray();
            System.out.println("response size" + jsonArray.size());

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject json = jsonArray.getJsonObject(i);
//                map.put("errormessage", json.getString("statusResponse"));
            }
            temp.add(map);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return temp;
    }

}
