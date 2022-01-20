package com.paytmbank.middleware.emgs.service;

import com.paytmbank.middleware.emgs.repository.EmployeeRepository;
import com.paytmbank.middleware.emgs.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    EmployeeRepository empRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserSecurity(empRepo.getById(username));
    }
}
