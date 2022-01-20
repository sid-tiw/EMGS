package com.paytmbank.middleware.emgs.details;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class JwtRequest {
    private String password;
    private String username;
}
