/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package com.deeservices.usermanagementmodule;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.json.Json;
import javax.json.JsonArray;
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

/**
 * REST Web Service
 *
 * @author ADEDOYIN
 */
@Path("viewusers")
public class ViewUserResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ViewUserResource
     */
    public ViewUserResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(@Context HttpServletRequest headers, String content) {
        try {
            HttpServletRequest temp = headers;
            Properties prop = new Properties();
            String sfile = "allconfig.properties";
            InputStream istream = getClass().getClassLoader().getResourceAsStream(sfile);

            if (istream != null) {
                prop.load(istream);
            } else {
                String sresponse = "{\"StatusCode\": \"95\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"Incorrect Authorization\"}";
                return Response.status(Response.Status.UNAUTHORIZED).entity(sresponse).build();

            }
            String i = temp.getHeader("Authorization")
                    .replaceFirst("Basic", "")
                    .trim();
            String keypri = Base64.base64Decode(i.trim());

            String AuthPassword = prop.getProperty("AuthPassword");

            if (AuthPassword.equals(keypri)) {
                /**
                 * ***********************Do resource Logic
                 * here******************
                 */
                User sresponse = new User();
                List<User> mapResponse = new ArrayList<>();
                Map<String, String> map = new LinkedHashMap<>();

                mapResponse = sresponse.getUser();
                if (mapResponse.size() <= 0) {
                    String sresponseOut = "{\"StatusCode\": \"96\",\"Code\":\"No Record Found \",\"StatusResponse\":\"Incorrect Authorization\"}";

                    return Response.status(Response.Status.OK).entity(sresponseOut).build();

                } else {

                    JsonReader jsonReaderOutput = Json.createReader(new StringReader(mapResponse.toString()));
                    JsonArray jsonOutput = jsonReaderOutput.readArray();
                    System.out.println(jsonOutput);

                    /**
                     * ***************************End*********************************
                     */
                    return Response.status(Response.Status.OK).entity(jsonOutput.toString()).build();
                }
            } else {
                String sresponse = "{\"StatusCode\": \"97\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"Incorrect Authorization\"}";
                return Response.status(Response.Status.UNAUTHORIZED).entity(sresponse).build();

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
