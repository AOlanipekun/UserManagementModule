/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.connection.Exchange;
import org.json.JSONObject;

/**
 *
 * @author ADEDOYIN
 */
public class AllUserAPI {

    public String getValues(String value, String body) throws IOException, Exception {
        String endpoint = "";
        String sReturn = "";

        Properties prop = new Properties();
        String sfile = "allconfig.properties";
        InputStream istream = getClass().getClassLoader().getResourceAsStream(sfile);

        if (istream != null) {
            prop.load(istream);
            String AuthPassword = prop.getProperty("AuthPassword");
            String auth = (Base64.getEncoder().encodeToString(AuthPassword.getBytes()));
            switch (value) {
                case "view":

                    endpoint = prop.getProperty("ViewEndPoint");
                    break;
                case "create":

                    endpoint = prop.getProperty("CreatEndPoint");
                    break;
                case "find":

                    endpoint = prop.getProperty("FindEndPoint");
                    break;
                case "edit":

                    endpoint = prop.getProperty("EditEndPoint");
                    break;
                case "Deactivate":

                    endpoint = prop.getProperty("DeactivateEndPoint");
                    break;
            }
            sReturn = viewUseritems(endpoint, body, auth);
        }
        return sReturn;
    }

    public String viewUseritems(String endpoint, String jsonbody, String auth) throws Exception {

        try {
            if (endpoint.length() <= 0) {
                String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"An Error Occured\",\"StatusResponse\":\"Invalid Endpoint\"}";
                return sresponse;
            } else if (auth.length() <= 0) {
                String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"An Error Occured\",\"StatusResponse\":\"Authorization can not be empty\"}";
                return sresponse;
            }

            RequestBody body = RequestBody.create(jsonbody.getBytes(), MediaType.parse("application/json"));

            OkHttpClient con = new OkHttpClient();
            Request sRequest = new Request.Builder()
                    .url(endpoint)
                    .addHeader("Authorization", String.format("Basic %s", (auth)))
                    .addHeader("Content-Type", "application/json")
                    .method("POST", body)
                    .build();
            Response sResponse = con.newCall(sRequest).execute();
            if (sResponse.code() != 200) {
                String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"An Error Occured\",\"StatusResponse\":\"" + sResponse.body().string() + "\"}";
                return sresponse;
            } else {
                return sResponse.body().string();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"An Error Occured\",\"StatusResponse\":\"" + ex + "\"}";
            return sresponse;
        }
    }

    public List<ViewItems> formatToList(String sResponse, int type) {
        if (sResponse.length() <= 0) {
            String isresponse = "{\"StatusCode\": \"99\",\"Code\":\"An Error Occured\",\"StatusResponse\":\"Invalid Endpoint\"}";
//            return isresponse;
        }
        List temp = new ArrayList();
        JsonReader jsonReaderOutput = Json.createReader(new StringReader(sResponse));

        if (type == 1) {
            JsonArray jsonArray = jsonReaderOutput.readArray();
            System.out.println("response size" + jsonArray.size());

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject json = jsonArray.getJsonObject(i);
                temp.add(getobject(json));
            }
        } else if (type == 2) {
            JsonObject json = jsonReaderOutput.readObject();

            System.out.println("response size" + json.size());
            temp.add(getobject(json));
        } else if (type == 3) {
            ViewItems vi = new ViewItems();
            vi.setsResponse(sResponse);
            temp.add(vi);
        }
        return temp;
    }

    private ViewItems getobject(JsonObject json) {

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
