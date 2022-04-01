/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deeservices.servlet;

import com.deeservices.apiconnect.EditUserAPI;
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
@WebServlet("/find")
public class FindServlet extends HttpServlet {

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
            EditUserAPI service = new EditUserAPI();
            service.vi.setUserName(request.getParameter("username"));
            map = service.FindUser(service.vi.getUserName());

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
        writer.println("<html>Response: " + sResponse + "</html>"); // to navigate to any other page
        writer.flush();

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
