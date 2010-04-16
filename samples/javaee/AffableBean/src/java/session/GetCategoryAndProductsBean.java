/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package session;

import entity.Category;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tgiunipero
 */
@Stateless
public class GetCategoryAndProductsBean {

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    private Category selectedCategory;
    private List categoryProducts;

    public void getCategoryAndProducts(String categoryId, HttpSession session) {

        // get selected category
        selectedCategory = categoryFacade.find(Short.parseShort(categoryId));

        // place selected category in session scope
        session.setAttribute("selectedCategory", selectedCategory);

        // get all products for selected category
        categoryProducts = productFacade.findForCategory(selectedCategory);

        // place category products in session scope
        session.setAttribute("categoryProducts", categoryProducts);
    }
}