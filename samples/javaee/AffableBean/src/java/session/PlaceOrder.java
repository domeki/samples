/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tgiunipero
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PlaceOrder implements PlaceOrderLocal {

    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {

        try {
            Customer customer = addCustomer(name, email, phone, address, cityRegion, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    public Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);

        em.persist(customer);
        return customer;
    }

    public CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);

        em.persist(order);
        return order;
    }

    public void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        em.flush();

        Iterator it = cart.getItems().keySet().iterator();

        // iterate through shopping cart and add items to OrderedProduct
        while(it.hasNext()) {

            String s = (String)it.next();
            int productId = Integer.parseInt(s);

            // set up primary key object
            OrderedProductPK orderedItemPK = new OrderedProductPK();
            orderedItemPK.setOrderId(order.getId());
            orderedItemPK.setProductId(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedItemPK);

            // set quantity
            ShoppingCartItem item = (ShoppingCartItem) cart.getItems().get(String.valueOf(productId));
            orderedItem.setQuantity(String.valueOf(item.getQuantity()));

            em.persist(orderedItem);
        }
    }
}