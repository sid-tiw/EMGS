package com.paytmbank.middleware.emgs.exception;

import com.paytmbank.middleware.emgs.entity.Department;

public class DepartmentAlreadyPresent extends Exception {
    public DepartmentAlreadyPresent(String message) {
        super(message);
    }
}
