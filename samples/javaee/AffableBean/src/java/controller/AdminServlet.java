/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */
package controller;

import entity.Customer;
import entity.CustomerOrder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import session.CustomerFacade;
import session.CustomerOrderFacade;
import session.OrderManager;

/**
 *
 * @author tgiunipero
 */
@WebServlet(name = "AdminServlet",
            urlPatterns = {"/admin/logout",
                           "/admin/viewOrders",
                           "/admin/viewCustomers",
                           "/admin/customerRecord",
                           "/admin/orderRecord"})

public class AdminServlet extends HttpServlet {

    @EJB
    private OrderManager orderManager;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;

    private String userPath;
    private Customer customer;
    private CustomerOrder order;
    private List orderList = new ArrayList();
    private List customerList = new ArrayList();


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();

        // if viewCustomers is requested
        if (userPath.equals("/admin/viewCustomers")) {
            customerList = customerFacade.findAll();
            request.setAttribute("customerList", customerList);
        }

        // if viewOrders is requested
        if (userPath.equals("/admin/viewOrders")) {
            orderList = customerOrderFacade.findAll();
            request.setAttribute("orderList", orderList);
        }

        // if customerRecord is requested
        if (userPath.equals("/admin/customerRecord")) {

            // get customer id from request
            String customerId = request.getQueryString();

            // get customer details
            customer = customerFacade.find(Integer.parseInt(customerId));
            request.setAttribute("customerRecord", customer);

            // get customer order details
            order = customerOrderFacade.findByCustomerId(customer);
            request.setAttribute("order", order);
        }

        // if orderRecord is requested
        if (userPath.equals("/admin/orderRecord")) {

            // get customer id from request
            String orderId = request.getQueryString();

            orderManager.getOrderDetails(Integer.parseInt(orderId), request);
        }

        // if logout is requested
        if (userPath.equals("/admin/logout")) {
            session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp");
        }

        // use RequestDispatcher to forward request internally
        userPath = "/admin/index.jsp";
        try {
            request.getRequestDispatcher(userPath).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}