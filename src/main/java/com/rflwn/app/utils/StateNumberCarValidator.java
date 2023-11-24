package com.rflwn.app.utils;

import org.springframework.stereotype.Component;

@Component
public class StateNumberCarValidator {
    private static final String carNumberPattern = "^[A-Z]{1}\\d{3}[A-Z]{2}-\\d{2,3}$";

    public boolean isValidNumber(String stateNumber) {
        return stateNumber.matches(carNumberPattern);
    }
}
