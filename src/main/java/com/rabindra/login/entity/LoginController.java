package com.rabindra.login.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rabindra.login.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LoginController {
	private final UserService userService;
	
	private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private static final String SECRET = "mysecretkey";

	public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
    	  String username = user.getUsername();
          String password = user.getPassword();
        if (userService.login(username, password)) {
            // generate JWT token
            String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Successful Login");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
