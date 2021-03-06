/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.servlet;

import com.deeservices.apiconnect.CreateUserAPI;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADEDOYIN
 */
@WebServlet("/create")
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

    }

    /**
     * handles HTTP POST request
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

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

            service.vi.setLogOnOperatorID("1"); //Value will be recieved from the session

            map = service.createUser(service.vi);

            if (map.size() > 0) {
                sResponse = map.get("Errormessage").toString();
                request.setAttribute("response", sResponse);
                if (sResponse.contains("succes")) {
                    response.sendRedirect("/UserManagementModule/");
                } else {

                    request.setAttribute("response", sResponse);
                    request.getRequestDispatcher("/createuser.xhtml").forward(request, response);
                }
            } else {
                sResponse = "Record not saved";
                request.setAttribute("response", sResponse);
                request.getRequestDispatcher("/createuser.xhtml").forward(request, response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sResponse = "An error occured";
            request.getRequestDispatcher("/createuser.xhtml").forward(request, response);
        }

//        PrintWriter writer = response.getWriter();
//        writer.println("<html>Response: " + sResponse + "</html>"); // to navigate to any other page
//        writer.flush();
    }

    /**
     * this life-cycle method is invoked when the application or the server is
     * shutting down
     */
    public void destroy() {
        System.out.println("Servlet is being destroyed");
    }
}
