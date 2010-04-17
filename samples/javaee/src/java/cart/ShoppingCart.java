/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package cart;

import entity.Product;
import java.util.*;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCart {

    HashMap<String, ShoppingCartItem> items;
    int numberOfItems;
    double total;

    public ShoppingCart() {
        items = new HashMap<String, ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

    public synchronized void addItem(String productId, Product product) {

        if (items.containsKey(productId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(productId);
            scitem.incrementQuantity();
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(product);
            items.put(productId, newItem);
        }
    }

    public synchronized void update(String productId, Product product, String quantity) {

        int qty = -1;

        // cast quantity as int
        qty = Integer.parseInt(quantity);

        if (qty >= 0) {
            if (items.containsKey(productId)) {
                ShoppingCartItem shoppingCartItem = (ShoppingCartItem) items.get(productId);
                if (qty != 0) {
                    // set item quantity to new value
                    shoppingCartItem.setQuantity(qty);
                } else {
                    // if quantity equals 0, remove from cart
                    Iterator iterator = (items.keySet()).iterator();

                    String keyMatch = "";

                    while (iterator.hasNext()) {
                        String key = iterator.next().toString();
                        ShoppingCartItem value = items.get(key);
                        if (value.equals(shoppingCartItem)) {
                            // retain key
                            keyMatch = key;
                        }
                    } //end while loop

                    items.remove(keyMatch);
                }
            }
        }
    }

    public synchronized HashMap<String, ShoppingCartItem> getItems() {

        return items;
    }

    public synchronized int getNumberOfItems() {
        numberOfItems = 0;

        Iterator it = items.keySet().iterator();

        while(it.hasNext()) {
            Object productId = it.next();
            ShoppingCartItem item = (ShoppingCartItem) items.get(productId.toString());
            numberOfItems += item.getQuantity();
        }

        return numberOfItems;
    }

    public synchronized double getSubtotal() {
        double amount = 0;

        Iterator it = items.keySet().iterator();

        while(it.hasNext()) {
            Object productId = it.next();
            ShoppingCartItem item = (ShoppingCartItem) items.get(productId.toString());
            Product productDetails = (Product) item.getItem();

            amount += (item.getQuantity() * productDetails.getPrice().doubleValue());
        }

        return amount;
    }

    public synchronized void calculateTotal(String surcharge) {
        double s = Double.parseDouble(surcharge);
        double amount = 0;

        amount = getSubtotal();
        amount = amount + s;

        total = amount;
    }

    public synchronized double getTotal() {
        return total;
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }

}