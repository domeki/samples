/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;


/**
 *
 * @author tgiunipero
 */
@Stateless
public class AdminManager {

    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private CustomerFacade customerFacade;

    List orderList = new ArrayList();
    List customerList = new ArrayList();

    public List getOrders() {

        orderList = customerOrderFacade.findAll();
        return orderList;
    }

    public List getCustomers() {

        customerList = customerFacade.findAll();
        return customerList;
    }

}