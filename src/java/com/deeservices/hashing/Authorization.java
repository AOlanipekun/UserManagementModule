/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.hashing;

//import com.sun.xml.internal.messaging.saaj.util.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.ws.rs.core.Response;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;

/**
 *
 * @author ADEDOYIN
 */
public class Authorization {

    public String validateAuthorizationCrediential(String val1) throws IOException, Base64DecodingException {
        String bOutput = "";
        Properties prop = new Properties();
        String sfile = "allconfig.properties";
        InputStream istream = getClass().getClassLoader().getResourceAsStream(sfile);

        if (istream != null) {
            prop.load(istream);
        } else {
            String sresponse = "{\"StatusCode\": \"94\",\"Code\":\"Invalid Property File\",\"StatusResponse\":\"Incorrect Authorization\"}";
            return sresponse;

        }

        String authorization = val1;

        if (authorization == null) {
            String sresponse = "{\"StatusCode\": \"95\",\"Code\":\"Authorization\",\"StatusResponse\":\"Authorization is required\"}";
            return sresponse;
        }
        if (!(authorization.contains("Basic"))) {
            String sresponse = "{\"StatusCode\": \"95\",\"Code\":\"Authorization\",\"StatusResponse\":\"Authorization Username invalid\"}";
            return sresponse;
        }
        authorization = authorization.replaceFirst("Basic", "")
                .trim();
//        String keypri = Base64.base64Decode(authorization);
        byte[] keypri = Base64.decode(authorization);

        String AuthPassword = prop.getProperty("AuthPassword");

        if (AuthPassword.equals(new String(keypri))) {
            bOutput = "success";
        } else {
            String sresponse = "{\"StatusCode\": \"97\",\"Code\":\"Unauthorized User\",\"StatusResponse\":\"Incorrect Authorization\"}";
            return sresponse;
        }
        return bOutput;
    }
}
