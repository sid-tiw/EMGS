package com.paytmbank.middleware.emgs.details;

import com.paytmbank.middleware.emgs.entity.Leave;

public class LeaveDetails {
    Leave lv;
    String status, error;
    public LeaveDetails(Leave lv) {
        this.lv = lv;
        status = "Success!";
        error = "No error.";
    }

    public LeaveDetails(Exception e) {
        status = "Failure!";
        error = e.getLocalizedMessage();
    }
}
