/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.apiconnect;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADEDOYIN
 */
public class createServlet extends HttpServlet {

    /**
     * this life-cycle method is invoked when this servlet is first accessed by
     * the client
     */
    public void init(ServletConfig config) {
        System.out.println("Servlet is being initialized");
    }

    /**
     * handles HTTP GET request
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter writer = response.getWriter();
        writer.println("<html>Hello, I am a Java servlet!</html>");
        writer.flush();
    }

    /**
     * handles HTTP POST request
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
//        String paramWidth = request.getParameter("width");
//        int width = Integer.parseInt(paramWidth);
//
//        String paramHeight = request.getParameter("height");
//        int height = Integer.parseInt(paramHeight);
//
//        long area = width * height;

        String sResponse = "";
        HashMap<String, Object> map = new HashMap<>();
        try {
            CreateUserAPI service = new CreateUserAPI();
            service.vi.setUserName(request.getParameter("username"));
            service.vi.setPassword(request.getParameter("password"));
            service.vi.setPermissions(request.getParameter("permissions"));
            service.vi.setFName(request.getParameter("fname"));
            service.vi.setLName(request.getParameter("lname"));
            service.vi.setEmail(request.getParameter("email"));
            service.vi.setPhoneNo(request.getParameter("phoneno"));
            service.vi.setGender(request.getParameter("gender"));
            service.vi.setDOB(request.getParameter("dob"));
            service.vi.setNationality(request.getParameter("nationality"));
            service.vi.setActivated("1");
            service.vi.setRetired("0");
            service.vi.setPasswordEDate(request.getParameter("passwordenddate"));

            map = service.createUser(service.vi);

            if (map.size() > 0) {
                sResponse = map.get("Errormessage").toString();
            } else {
                sResponse = "Record not saved";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sResponse = "An error occured";
        }

        PrintWriter writer = response.getWriter();
        writer.println("<html>Response: " + sResponse + "</html>");
        writer.flush();

    }

    /**
     * this life-cycle method is invoked when the application or the server is
     * shutting down
     */
    public void destroy() {
        System.out.println("Servlet is being destroyed");
    }
}
