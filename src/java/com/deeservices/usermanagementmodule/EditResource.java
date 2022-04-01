/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package com.deeservices.usermanagementmodule;

import com.deeservices.hashing.Authorization;
import com.deeservices.hashing.HashPassword;
import java.io.StringReader;
import java.lang.invoke.MethodType;
import java.util.HashMap;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author ADEDOYIN
 */
@Path("edituser")
public class EditResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EditResource
     */
    public EditResource() {
    }

    @Path("search")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRecord(@Context HttpServletRequest headers, String content) {
        try {
            HttpServletRequest temp = headers;

            String authorization = temp.getHeader("Authorization");
            Authorization validate = new Authorization();
            String isvalid = validate.validateAuthorizationCrediential(authorization);
            if (isvalid != "success") {
                return Response.status(Response.Status.UNAUTHORIZED).entity(isvalid).build();

            } else {
                JsonReader jsonReader = Json.createReader(new StringReader(content));
                JsonObject json = jsonReader.readObject();
                String UserName = json.getString("username");
                /**
                 * ***********************Do resource Logic
                 * here******************
                 */
                User sresponse = new User();
                HashPassword hp = new HashPassword();

                Map<String, String> mapResponse = new HashMap<>();
                JSONObject map = new JSONObject();

                map = sresponse.searchUser(UserName.trim());

                if (map.length() <= 0) {
                    String sresponseOut = "{\"StatusCode\": \"96\",\"Code\":\"No Record Found \",\"StatusResponse\":\"Incorrect Authorization\"}";

                    return Response.status(Response.Status.OK).entity(sresponseOut).build();

                } else {
                    String listString = map.toString();
                    return Response.status(Response.Status.OK).entity(listString).build();

                }
            }

        } catch (JsonException ex) {
            String sresponse = "{\"StatusCode\": \"98\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(sresponse).build();
//            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(sresponse).build();
//            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Path("edit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(@Context HttpServletRequest headers, String content) {
        try {
            HttpServletRequest temp = headers;

            String authorization = temp.getHeader("Authorization");
            Authorization validate = new Authorization();
            String isvalid = validate.validateAuthorizationCrediential(authorization);
            if (isvalid != "success") {
                return Response.status(Response.Status.UNAUTHORIZED).entity(isvalid).build();

            } else {
                JsonReader jsonReader = Json.createReader(new StringReader(content));
                JsonObject json = jsonReader.readObject();
                String UserName = json.getString("username");
                String Password = json.getString("password");
                String Permissions = json.getString("permissions");
                String FName = json.getString("fname");
                String LName = json.getString("lname");
                String Email = json.getString("email");
                String PhoneNo = json.getString("phoneno");
                String Gender = json.getString("gender");
                String DOB = json.getString("dob");
                String Nationality = json.getString("nationality");
                String PasswordEDate = json.getString("passwordenddate");
                String Retired = json.getString("retired");
                String Activated = json.getString("activated");
                String LogOnOperatorID = json.getString("LogOnOperatorID");

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
                JSONObject map = new JSONObject();

                // String hpPassword = hp.createpassword(Password);
                String hpPassword = Password;
                map = sresponse.createUser(UserName.trim(),
                        hpPassword.trim(),
                        Integer.valueOf(Permissions),
                        FName.trim(),
                        LName.trim(),
                        Email.trim(),
                        PhoneNo.trim(),
                        Gender.trim(),
                        DOB.trim(),
                        Nationality.trim(),
                        PasswordEDate.trim(),
                        Boolean.valueOf(Retired),
                        Boolean.valueOf(Activated),
                        Integer.valueOf(LogOnOperatorID));
                if (!(map.length() > 0)) {

                    map.put("StatusCode", "0");
                    map.put("StatusResponse", "Username does not exist");

                } else {
                    map.put("StatusCode", "1");
                    map.put("StatusResponse", map.get("Errormessage"));

                }

                String listString = map.toString();
                return Response.status(Response.Status.OK).entity(listString).build();

                /**
                 * ***************************End*********************************
                 */
            }
        } catch (JsonException ex) {
            String sresponse = "{\"StatusCode\": \"98\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(sresponse).build();
//            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(sresponse).build();
//            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
