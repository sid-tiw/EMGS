package com.paytmbank.middleware.emgs.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeAlreadyPresent extends Exception {
    public EmployeeAlreadyPresent(String message) {
        super(message);
    }
}
