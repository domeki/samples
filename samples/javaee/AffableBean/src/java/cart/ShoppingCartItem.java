/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package cart;

import entity.Product;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCartItem {

    Object item;
    int quantity;

    public ShoppingCartItem(Object item) {
        this.item = item;
        quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public Object getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        double amount = 0;

        Product productDetails = (Product) this.getItem();

        amount = (this.getQuantity() * productDetails.getPrice().doubleValue());

        return roundOff(amount);
    }

    private double roundOff(double x) {
        long val = Math.round(x * 100); // cents

        return val / 100.0;
    }
}