/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package session;

import cart.ShoppingCart;
import entity.Customer;
import entity.CustomerOrder;
import javax.ejb.Local;

/**
 *
 * @author tgiunipero
 */
@Local
public interface PlaceOrderLocal {

    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart);

    public Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber);

    public CustomerOrder addOrder(Customer customer, ShoppingCart cart);

    public void addOrderedItems(CustomerOrder order, ShoppingCart cart);
}