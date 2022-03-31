/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package com.deeservices.usermanagementmodule;

import com.deeservices.hashing.Authorization;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.json.JsonException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;

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

            String authorization = temp.getHeader("Authorization");
            Authorization validate = new Authorization();
            String isvalid = validate.validateAuthorizationCrediential(authorization);
            if (isvalid != "success") {
                return Response.status(Response.Status.UNAUTHORIZED).entity(isvalid).build();

            } else {

                /**
                 * ***********************Do resource Logic
                 * here******************
                 */
                User sresponse = new User();
                List<Map<String, String>> mapResponse = new ArrayList<>();
                JSONArray map = new JSONArray();

//                mapResponse = sresponse.getUsernew();
                map = sresponse.getUserfinal();
                if (map.length() <= 0) {
                    String sresponseOut = "{\"StatusCode\": \"96\",\"Code\":\"No Record Found \",\"StatusResponse\":\"Incorrect Authorization\"}";

                    return Response.status(Response.Status.OK).entity(sresponseOut).build();

                } else {
                    /**
                     * ***************************End*********************************
                     */
//                    String listString
//                            = Arrays.toString(mapResponse.toArray());
//                    return Response.status(Response.Status.OK).entity(listString).build();
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

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response postJson(@Context HttpServletRequest headers, String content) {
//        try {
//            HttpServletRequest temp = headers;
//            Properties prop = new Properties();
//            String sfile = "allconfig.properties";
//            InputStream istream = getClass().getClassLoader().getResourceAsStream(sfile);
//
//            if (istream != null) {
//                prop.load(istream);
//            } else {
//                String sresponse = "{\"StatusCode\": \"94\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"Incorrect Authorization\"}";
//                return Response.status(Response.Status.UNAUTHORIZED).entity(sresponse).build();
//
//            }
//
//            String authorization = temp.getHeader("Authorization");
//
//            if (authorization == null) {
//                String sresponse = "{\"StatusCode\": \"95\",\"Code\":\"Authorization\",\"StatusResponse\":\"Authorization is required\"}";
//                return Response.status(Response.Status.UNAUTHORIZED).entity(sresponse).build();
//
//            }
//            if (!(authorization.contains("Basic"))) {
//                String sresponse = "{\"StatusCode\": \"95\",\"Code\":\"Authorization\",\"StatusResponse\":\"Authorization Username invalid\"}";
//                return Response.status(Response.Status.UNAUTHORIZED).entity(sresponse).build();
//
//            }
//            authorization = authorization.replaceFirst("Basic", "")
//                    .trim();
//            String keypri = Base64.base64Decode(authorization);
//
//            String AuthPassword = prop.getProperty("AuthPassword");
//
//            if (AuthPassword.equals(keypri)) {
//                /**
//                 * ***********************Do resource Logic
//                 * here******************
//                 */
//                User sresponse = new User();
//                List<Map<String, String>> mapResponse = new ArrayList<>();
//                JSONArray map = new JSONArray();
//
////                mapResponse = sresponse.getUsernew();
//                map = sresponse.getUserfinal();
//                if (map.length()<= 0) {
//                    String sresponseOut = "{\"StatusCode\": \"96\",\"Code\":\"No Record Found \",\"StatusResponse\":\"Incorrect Authorization\"}";
//
//                    return Response.status(Response.Status.OK).entity(sresponseOut).build();
//
//                } else {
//                    /**
//                     * ***************************End*********************************
//                     */
////                    String listString
////                            = Arrays.toString(mapResponse.toArray());
////                    return Response.status(Response.Status.OK).entity(listString).build();
//                    String listString = map.toString();
//                    return Response.status(Response.Status.OK).entity(listString).build();
//
//                }
//            } else {
//                String sresponse = "{\"StatusCode\": \"97\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"Incorrect Authorization\"}";
//                return Response.status(Response.Status.UNAUTHORIZED).entity(sresponse).build();
//
//            }
//
//        } catch (JsonException ex) {
//            String sresponse = "{\"StatusCode\": \"98\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
//            return Response.status(Response.Status.BAD_REQUEST).entity(sresponse).build();
////            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            String sresponse = "{\"StatusCode\": \"99\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"" + ex.getMessage() + "\"}";
//            return Response.status(Response.Status.EXPECTATION_FAILED).entity(sresponse).build();
////            Logger.getLogger(TemplateResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
