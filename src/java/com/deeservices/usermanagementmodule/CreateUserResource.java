/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package com.deeservices.usermanagementmodule;

import com.deeservices.hashing.HashPassword;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author ADEDOYIN
 */
@Path("createuser")
public class CreateUserResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CreateUser
     */
    public CreateUserResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(@Context HttpServletRequest headers, String content) {
        try {
            HttpServletRequest temp = headers;

            String i = temp.getHeader("Authorization");
            byte[] keypri = i.getBytes();

            if ("admin123".equals(i)) {
                JsonReader jsonReader = Json.createReader(new StringReader(content));
                JsonObject json = jsonReader.readObject();
                String UserName = json.getString("UserName");
                String Password = json.getString("password");
                int Permissions = json.getInt("Permissions");
                String FName = json.getString("FName");
                String LName = json.getString("LName");
                String Email = json.getString("Email");
                String PhoneNo = json.getString("PhoneNo");
                String Gender = json.getString("Gender");
                String DOB = json.getString("DOB");
                String Nationality = json.getString("Nationality");
                String PasswordEDate = json.getString("PasswordEndDate");
                String SetUpDate = json.getString("SetUpDate");
                boolean Retired = json.getBoolean("Retired");
                boolean Activated = json.getBoolean("Activated");

                System.out.println("Username:" + UserName);
                System.out.println("Password:" + Password);
                //hash key for "admin =$31$24$9q4JUX3cWC6CjhUs_Cn0srxNwFQpq1TX17cdV9d6zsM

                /**
                 * ***********************Do resource Logic
                 * here******************
                 */
                User sresponse = new User();
                HashPassword hp = new HashPassword();

                Map<String, String> mapResponse = new HashMap<>();
                List<Map<String, String>> mapList = new ArrayList<>();
                Map<String, String> map = new LinkedHashMap<>();

                String hpPassword = hp.createpassword(Password);

                mapResponse = sresponse.createUser(UserName.trim(),
                        hpPassword.trim(),
                        Permissions,
                        FName.trim(),
                        LName.trim(),
                        Email.trim(),
                        PhoneNo.trim(),
                        Gender.trim(),
                        DOB.trim(),
                        Nationality.trim(),
                        PasswordEDate.trim(),
                        SetUpDate.trim(),
                        Retired,
                        Activated);
                if (mapResponse.size() > 0) {
                    map.put("StatusCode", "0");
                    map.put("StatusResponse", "User Created Successfully");
                    mapList.add(map);
                } else {
                    map.put("StatusCode", "0");
                    map.put("StatusResponse", "Username does not exist");
                    mapList.add(map);
                }

                JsonReader jsonReaderOutput = Json.createReader(new StringReader(mapList.toString()));
                JsonObject jsonOutput = jsonReaderOutput.readObject();
                System.out.println(jsonOutput);

                /**
                 * ***************************End*********************************
                 */
                return Response.status(Response.Status.OK).entity(jsonOutput.toString()).build();
            } else {
            String sresponse = "{\"StatusCode\": \"97\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"Incorrect Authorization\"}";
            return Response.status(Response.Status.valueOf("Json EXCEPTION")).entity(sresponse).build();

            }
        } catch (JsonException ex) {
            String sresponse = "{\"StatusCode\": \"98\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
            return Response.status(Response.Status.valueOf("Json EXCEPTION")).entity(sresponse).build();
//            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(sresponse).build();
//            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
