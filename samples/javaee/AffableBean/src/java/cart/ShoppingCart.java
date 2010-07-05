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

    List<ShoppingCartItem> items;
    int numberOfItems;
    double total;

    public ShoppingCart() {
        items = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>.
     * If item of the provided Product already exists in shopping cart, the
     * quantity of that item is incremented.
     */
    public synchronized void addItem(Product product) {

        boolean newItem = true;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getProduct().getId() == product.getId()) {

                newItem = false;
                scItem.incrementQuantity();
            }
        }

        if (newItem) {
            ShoppingCartItem scItem = new ShoppingCartItem(product);
            items.add(scItem);
        }
    }

    /**
     * Updates the <code>ShoppingCartItem</code> of the provided
     * <code>Product</code> to the specified quantity. If '<code>0</code>'
     * is the given quantity, the cart item is removed from the cart.
     */
    public synchronized void update(Product product, String quantity) {

        short qty = -1;

        // cast quantity as short
        qty = Short.parseShort(quantity);

        if (qty >= 0) {

            ShoppingCartItem item = null;

            for (ShoppingCartItem scItem : items) {

                if (scItem.getProduct().getId() == product.getId()) {

                    if (qty != 0) {
                        // set item quantity to new value
                        scItem.setQuantity(qty);
                    } else {
                        // if quantity equals 0, save item and break
                        item = scItem;
                        break;
                    }
                }
            }

            if (item != null) {
                // remove from cart
                items.remove(item);
            }
        }
    }

    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }

    /**
     * Returns the sum quantities for all
     * items maintained in shopping cart list
     */
    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (ShoppingCartItem scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }

    /**
     * Returns the sum of the product price multiplied by
     * the quantity for all items in shopping cart list
     */
    public synchronized double getSubtotal() {

        double amount = 0;

        for (ShoppingCartItem scItem : items) {

            Product product = (Product) scItem.getProduct();
            amount += (scItem.getQuantity() * product.getPrice().doubleValue());
        }

        return amount;
    }

    /**
     * Returns the sum of the subtotal with the specified surcharge
     */
    public synchronized void calculateTotal(String surcharge) {

        double amount = 0;

        // cast surcharge as double
        double s = Double.parseDouble(surcharge);

        amount = this.getSubtotal();
        amount += s;

        total = amount;
    }

    public synchronized double getTotal() {

        return total;
    }

    /**
     * Empties the shopping cart. All items are removed
     * from the shopping cart list, <code>numberOfItems</code>
     * and <code>total</code> are reset to '<code>0</code>'.
     */
    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }

}