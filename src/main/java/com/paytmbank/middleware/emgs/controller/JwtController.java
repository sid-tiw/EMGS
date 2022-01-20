package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.details.JwtRequest;
import com.paytmbank.middleware.emgs.security.JwtUtil;
import com.paytmbank.middleware.emgs.security.UserSecurity;
import com.paytmbank.middleware.emgs.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jrq) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jrq.getUsername(), jrq.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
        final UserDetails userDetails = userSecurityService.loadUserByUsername(jrq.getUsername());
        return ResponseEntity.ok().body(jwtUtil.generateToken(userDetails));
    }

    @GetMapping("/echo")
    public String echoReq() {
        return "Hello, Sid!";
    }
}
