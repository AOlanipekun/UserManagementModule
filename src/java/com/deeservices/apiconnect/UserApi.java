/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

//import com.sun.xml.internal.messaging.saaj.util.Base64;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

/**
 *
 * @author ADEDOYIN
 */
public class UserApi {

// previous method without DRY principle
    public List<ViewItems> viewUseritemsold() throws Exception {
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
//            byte[] auth = (Base64.encode("Basic:RVFxTmwxOTdXTzhPekMzak54Z0R5bTBqcTVvYTp0TV9jYVZEQ1ZqQ2Nsd2NFeGt TTHZBNTc0Tmth".getBytes()));
//
//            con.setRequestProperty("Authorization",
//                    String.format("Basic %s", new String(auth)));
//
            String auth = (Base64.getEncoder().encodeToString("Basic:RVFxTmwxOTdXTzhPekMzak54Z0R5bTBqcTVvYTp0TV9jYVZEQ1ZqQ2Nsd2NFeGt TTHZBNTc0Tmth".getBytes()));

            con.setRequestProperty("Authorization",
                    String.format("Basic %s", (auth)));

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

    public List<ViewItems> viewUseritems(String value, String body) throws Exception {
        List temp = new ArrayList();
        try {

            AllUserAPI AUP = new AllUserAPI();
            ViewItems vi = new ViewItems();
            String response = AUP.getValues(value, body);
            if (response == "") {

                String sresponse = "{\"StatusCode\": \"92\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"Response is empty\"}";
                System.out.println(sresponse);
                vi.setsResponse("No reply received from the API");
                temp.add(vi);
            } else if (response.contains("error")) {
                String sresponse = "{\"StatusCode\": \"93\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"" + response + "\"}";
                System.out.println(sresponse);
                vi.setsResponse("Error 93: User API");
                temp.add(vi);
            } else if (response.contains("User created Successfully")) {
                String sresponse = "{\"StatusCode\": \"93\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"" + response + "\"}";
                System.out.println(sresponse);
                vi.setsResponse("User created Successfully");
                temp.add(vi);
            } else if (response.contains("User Updated Successfully")) {
                String sresponse = "{\"StatusCode\": \"93\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"" + response + "\"}";
                System.out.println(sresponse);
                vi.setsResponse("User Updated Successfully");
                temp.add(vi);

            } else if (response.contains("Error")) {
                String sresponse = "{\"StatusCode\": \"94\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"" + response + "\"}";
                System.out.println(sresponse);
                vi.setsResponse("Error 94: User API");
                temp.add(vi);
            } else if (response.contains("No Record Found")) {
                String sresponse = "{\"StatusCode\": \"95\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"" + response + "\"}";
                System.out.println(sresponse);
                vi.setsResponse("No Record Found");
                temp.add(vi);
            } else {
                switch (value) {
                    case "view":
                        temp = AUP.formatToList(response, 1);
                        break;
                    case "find":
                        temp = AUP.formatToList(response, 2);
                        break;
                    case "create":
                    case "edit":
                    case "Deactivate":
                        temp = AUP.formatToList(response, 3);
                        break;
                }
                // temp = AUP.formatToList(response);

                System.out.println("\nSending 'Get' request to URL : View");
                System.out.println("Response msg : " + response);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"An Error Occured\",\"StatusResponse\":\"" + ex + "\"}";
        }

        return temp;
    }

}
