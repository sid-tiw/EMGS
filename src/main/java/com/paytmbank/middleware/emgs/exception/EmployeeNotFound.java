package com.paytmbank.middleware.emgs.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeNotFound extends UsernameNotFoundException{
	public EmployeeNotFound(String message) {
		super(message);
	}
}
