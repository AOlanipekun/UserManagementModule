/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import java.io.InputStream;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.connection.Exchange;

/**
 *
 * @author ADEDOYIN
 */
public class AllUserAPI {

    public String viewUseritems(String endpoint, String jsonbody) throws Exception {

        try {
            Properties prop = new Properties();
            String sfile = "allconfig.properties";
            InputStream istream = getClass().getClassLoader().getResourceAsStream(sfile);

            if (istream != null) {
                prop.load(istream);
                String AuthPassword = prop.getProperty("AuthPassword");
                String auth = (Base64.getEncoder().encodeToString(AuthPassword.getBytes()));

                RequestBody body = RequestBody.create(jsonbody.getBytes());

                OkHttpClient con = new OkHttpClient();
                Request sRequest = new Request.Builder()
                        .url(endpoint)
                        .addHeader("Authorization", String.format("Basic %s", (auth)))
                        .addHeader("Content-Type", "application/json")
                        .method("POST", body)
                        .build();
                Response sResponse = con.newCall(sRequest).execute();
                return sResponse.body().toString();
            } else {
                String sresponse = "{\"StatusCode\": \"94\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"Incorrect Authorization\"}";
                return sresponse;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"An Error Occured\",\"StatusResponse\":\""+ex+"\"}";
            return sresponse;
        }
    }

}
