/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import cart.ShoppingCart;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import session.CategoryFacade;
import session.OrderManager;
import session.ProductFacade;
import validate.Validator;

/**
 *
 * @author tgiunipero
 */
@WebServlet(name = "Controller",
            loadOnStartup = 1,
            urlPatterns = {"/category",
                           "/addToCart",
                           "/viewCart",
                           "/updateCart",
                           "/checkout",
                           "/purchase",
                           "/chooseLanguage"})

public class ControllerServlet extends HttpServlet {

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private OrderManager orderManager;

    private ShoppingCart cart;
    private String userPath;
    private String surcharge;
    private Category selectedCategory;


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        surcharge = servletConfig.getServletContext().getInitParameter("deliverySurcharge");

        // store category list in servlet context
        getServletContext().setAttribute("categories", categoryFacade.findAll());
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

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();
        List<Product> categoryProducts;

        // if category page is requested
        if (userPath.equals("/category")) {

            // get categoryId from request
            String categoryId = request.getQueryString();

            if (categoryId != null) {

                // get selected category
                selectedCategory = categoryFacade.find(Short.parseShort(categoryId));

                // place selected category in session scope
                session.setAttribute("selectedCategory", selectedCategory);

                // get all products for selected category
                categoryProducts = productFacade.findForCategory(selectedCategory);

                // place category products in session scope
                session.setAttribute("categoryProducts", categoryProducts);
            }


        // if cart page is requested
        } else if (userPath.equals("/viewCart")) {

            String clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {

                cart = (ShoppingCart) session.getAttribute("cart");
                cart.clear();
            }

            userPath = "/cart";


        // if checkout page is requested
        } else if (userPath.equals("/checkout")) {

            cart = (ShoppingCart) session.getAttribute("cart");

            // calculate total
            cart.calculateTotal(surcharge);

            // forward to checkout page and switch to a secure channel


        // if user switches language
        } else if (userPath.equals("/chooseLanguage")) {

            // get language choice
            String language = request.getParameter("language");

            // place in session scope
            session.setAttribute("language", language);

            String userView = (String) session.getAttribute("view");

            if ((userView != null) &&
                (!userView.equals("/index")) &&
                (!userView.equals("/confirmation"))) {  // session is destroyed before sending confirmation
                                                        // view, so not possible to change languages there
                userPath = userView;
            } else {

                // if previous view is index, confirmation, or cannot be determined, send user to welcome page
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

        request.setCharacterEncoding("UTF-8");  // ensures that user input is interpreted as
                                                // 8-bit Unicode (e.g., for Czech characters)

        HttpSession session = request.getSession(true);
        Validator validator = new Validator();

        userPath = request.getServletPath();
        String productId;
        Product product;


        // if addToCart action is called
        if (userPath.equals("/addToCart")) {

            // if user is adding item to cart for first time
            // create cart object and attach it to user session
            if (cart == null) {

                cart = new ShoppingCart();
                session.setAttribute("cart", cart);

            // if cart object exists but isn't in user session,
            // empty existing cart object and place in session
            } else if (session.getAttribute("cart") == null) {

                cart.clear();
                session.setAttribute("cart", cart);
            }

            // get user input from request
            productId = request.getParameter("productId");

            if (!productId.isEmpty()) {

                product = productFacade.find(Integer.parseInt(productId));
                cart.addItem(productId, product);
            }

            userPath = "/category";


        // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {

            String quantity = request.getParameter("quantity");

            cart = (ShoppingCart) session.getAttribute("cart");

            // get user input from request
            productId = request.getParameter("productId");

            boolean invalidEntry = validator.validateQuantity(productId, quantity);

            if (!invalidEntry) {

                product = productFacade.find(Integer.parseInt(productId));
                cart.update(productId, product, quantity);
            }

            userPath = "/cart";


        // if purchase action is called
        } else if (userPath.equals("/purchase")) {

            // get language choice
            String language = (String) session.getAttribute("language");

            cart = (ShoppingCart) session.getAttribute("cart");

            if (cart != null) {

                // extract user data from request
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String cityRegion = request.getParameter("cityRegion");
                String ccNumber = request.getParameter("creditcard");

                // validateForm user data
                boolean validationErrorFlag = false;
                validationErrorFlag = validator.validateForm(name, email, phone, address, cityRegion, ccNumber, request);

                // if validation error found, return user to checkout
                if (validationErrorFlag == true) {
                    request.setAttribute("validationErrorFlag", validationErrorFlag);
                    userPath = "/checkout";

                    // otherwise, save order to database
                } else {

                    int orderId;
                    orderId = orderManager.placeOrder(name, email, phone, address, cityRegion, ccNumber, cart);

                    // if order processed successfully send user to confirmation page
                    if (orderId != 0) {

                        userPath = "/confirmation";

                        // dissociate shopping cart from session
                        cart = null;

                        // end session
                        session.invalidate();

                        if (language != null) {                         // if user has manually changed language at some
                            session = request.getSession(true);         // point during session, create new session and
                            session.setAttribute("language", language); // set language attribute - otherwise language
                        }                                               // may be switched on confirmation page!

                        // get order details
                        orderManager.getOrderDetails(orderId, request);

                    // otherwise, send back to checkout page and display error
                    } else {
                        userPath = "/checkout";
                        request.setAttribute("orderFailureFlag", true);
                    }
                }
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}