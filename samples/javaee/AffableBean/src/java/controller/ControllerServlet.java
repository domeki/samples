/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import cart.ShoppingCart;
import session.CategoryFacade;
import session.ProductFacade;
import entity.Category;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import session.CustomerOrderFacade;
import session.GetCategoryAndProductsBean;
import session.OrderedProductFacade;
import session.PlaceOrderBean;

/**
 *
 * @author tgiunipero
 */
@WebServlet(name = "Controller",
            urlPatterns = {"/category", "/addToCart", "/viewCart", "/updateCart", "/checkout", "/purchase", "/chooseLanguage"},
            loadOnStartup = 1)

public class ControllerServlet extends HttpServlet {

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private OrderedProductFacade OrderedProductFacade;

    @EJB
    private PlaceOrderBean placeOrderBean;
    @EJB
    private GetCategoryAndProductsBean getCategoryAndProductsBean;

    private Category selectedCategory;
    private List categoryProducts;
    private ShoppingCart cart;
    private String userPath;
    private String surcharge;

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

        // if category page is requested
        if (userPath.equals("/category")) {

            // get categoryId from request
            String categoryId = request.getQueryString();

            if (categoryId != null) {

                getCategoryAndProductsBean.getCategoryAndProducts(categoryId, session);
            } else {

                // if neither category id nor previously selected category exist
                // (e.g., if session has expired) send user to welcome page
                if (selectedCategory == null) {

                    try {
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            // if cart page is requested
        } else if (userPath.equals("/viewCart")) {

            userPath = "/cart";

            String clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {
                cart = (ShoppingCart) session.getAttribute("cart");

                // if cart doesn't exist (e.g., if session times out) send user to welcome page
                if (cart == null) {
                    try {
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    cart.clear();
                }
            }

            // if checkout page is requested
        } else if (userPath.equals("/checkout")) {

            cart = (ShoppingCart) session.getAttribute("cart");

            // if cart doesn't exist (i.e., if session has expired)
            // send user to welcome page
            if (cart == null) {
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // calculate total
            cart.calculateTotal(surcharge);

            // forward to /WEB-INF/view/checkout.jsp
            // switch to a secure channel

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
                // return user from whence s/he came
                userPath = userView;
            } else {
                // if previous view is index or cannot be determined, send user to welcome page
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
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

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();

        // get user input from request
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");

        Product product;

        // if addToCart action is called
        if (userPath.equals("/addToCart")) {

            if (cart == null) {
                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            } else {
                // if cart exists but isn't in user session, set for garbage collection
                // and send user to welcome page (this can occur if session times out)
                if (session.getAttribute("cart") == null) {
                    cart = null;
                    try {
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            userPath = "/category";

            if (!productId.equals("")) {

                product = productFacade.find(Integer.parseInt(productId));

                cart.addItem(productId, product);

            }

            // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {

            String userView = (String) session.getAttribute("view");

            // if request does not come from cart page or if session expired
            // send user to welcome page
            if (userView == null || !userView.equals("/cart")) {

                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            userPath = "/cart";

            cart = (ShoppingCart) session.getAttribute("cart");

            if (!productId.equals("") && !quantity.equals("")) {

                product = productFacade.find(Integer.parseInt(productId));
                cart.update(productId, product, quantity);
            }

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

                // perform simple validation
                boolean validationErrorFlag = false;
                boolean nameError;
                boolean emailError;
                boolean phoneError;
                boolean addressError;
                boolean cityRegionError;
                boolean ccNumberError;

                if (name == null
                        || name.equals("")
                        || name.length() > 45) {
                    validationErrorFlag = true;
                    nameError = true;
                    request.setAttribute("nameError", nameError);
                }
                if (email == null
                        || email.equals("")
                        || !email.contains("@")) {
                    validationErrorFlag = true;
                    emailError = true;
                    request.setAttribute("emailError", emailError);
                }
                if (phone == null
                        || phone.equals("")
                        || phone.length() < 9) {
                    validationErrorFlag = true;
                    phoneError = true;
                    request.setAttribute("phoneError", phoneError);
                }
                if (address == null
                        || address.equals("")
                        || address.length() > 45) {
                    validationErrorFlag = true;
                    addressError = true;
                    request.setAttribute("addressError", addressError);
                }
                if (cityRegion == null
                        || cityRegion.equals("")
                        || cityRegion.length() > 2) {
                    validationErrorFlag = true;
                    cityRegionError = true;
                    request.setAttribute("cityRegionError", cityRegionError);
                }
                if (ccNumber == null
                        || ccNumber.equals("")
                        || ccNumber.length() > 19) {
                    validationErrorFlag = true;
                    ccNumberError = true;
                    request.setAttribute("ccNumberError", ccNumberError);
                }

                // if validation error found, return user to checkout
                if (validationErrorFlag == true) {
                    request.setAttribute("validationErrorFlag", validationErrorFlag);
                    userPath = "/checkout";

                    // otherwise, save order to database
                } else {

                    int orderId;
                    orderId = placeOrderBean.placeOrder(name, email, phone, address, cityRegion, ccNumber, cart);

                    // if order processed successfully
                    // send user to confirmation page
                    if (orderId != 0) {
                        userPath = "/confirmation";

                        // dissociate shopping cart from session
                        cart = null;

                        // end session
                        session.invalidate();

                        if (language != null) {                         // if user has manually changed language
                            session = request.getSession(true);         // at some point during session, create
                            session.setAttribute("language", language); // new session and set language attribute
                        }

                        // get order details
                        CustomerOrder order = customerOrderFacade.find(orderId);

                        List<OrderedProduct> orderedProducts = OrderedProductFacade.findByOrderId(orderId);

                        Iterator iter = orderedProducts.iterator();
                        List<Product> products = new ArrayList<Product>();

                        while (iter.hasNext()) {
                            OrderedProduct o = (OrderedProduct) iter.next();
                            Product p = (Product) productFacade.find(o.getOrderedProductPK().getProductId());
                            products.add(p);
                        }

                        // get customer details
                        Customer customer = order.getCustomerId();

                        // place in request scope
                        request.setAttribute("order", order);
                        request.setAttribute("orderedProducts", orderedProducts);
                        request.setAttribute("products", products);
                        request.setAttribute("customer", customer);

                        // otherwise, send back to checkout page and display error
                    } else {
                        userPath = "/checkout";
                        request.setAttribute("orderFailureFlag", true);
                    }
                }
            } else {
                // if cart doesn't exist or if session expired
                // send user to welcome page
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
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