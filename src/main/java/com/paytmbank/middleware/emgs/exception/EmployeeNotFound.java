package com.paytmbank.middleware.emgs.exception;

//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.NoArgsConstructor;

public class EmployeeNotFound extends Exception {
	public EmployeeNotFound(String message) {
		super(message);
	}
}
