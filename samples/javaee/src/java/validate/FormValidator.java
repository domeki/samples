/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */
package validate;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author tgiunipero
 */
public class FormValidator {

    // performs simple validation
    public boolean validate(String name,
                            String email,
                            String phone,
                            String address,
                            String cityRegion,
                            String ccNumber,
                            HttpServletRequest request) {

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

        return validationErrorFlag;
    }
}