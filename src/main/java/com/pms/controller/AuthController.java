package com.pms.controller;

import com.pms.config.Jwtprovider;
import com.pms.modal.User;
import com.pms.repository.UserRepository;
import com.pms.request.LoginRequest;
import com.pms.response.AuthResponse;
import com.pms.service.CustomeUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomeUserDetailsImpl customeUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new Exception("Email already exists with another account");
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the provided password
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        User savedUser = userRepository.save(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= Jwtprovider.generateToken(authentication);
        AuthResponse res=new AuthResponse();
        res.setMessage("signup success");
        res.setJwt(jwt);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest){
        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=Jwtprovider.generateToken(authentication);
        AuthResponse res=new AuthResponse();
        res.setMessage("siging success");
        res.setJwt(jwt);
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }
    private Authentication authenticate(String username, String password){
        UserDetails userDetails=customeUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invaild password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }
}
