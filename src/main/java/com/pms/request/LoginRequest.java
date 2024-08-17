package com.pms.request;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
