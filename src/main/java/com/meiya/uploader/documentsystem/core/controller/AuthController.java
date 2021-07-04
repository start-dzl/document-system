package com.meiya.uploader.documentsystem.core.controller;


import com.meiya.uploader.documentsystem.core.entity.Admin;
import com.meiya.uploader.documentsystem.core.service.AdminService;
import com.meiya.uploader.documentsystem.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/sign")
    public JwtAuthenticationResponse sign(@RequestBody LoginRequest loginRequest) {

        Optional<Admin> admin = adminService.findByUsername(loginRequest.getUsername());
        Admin existsAdmin = admin.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(loginRequest.getPassword(), existsAdmin.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        logger.info("login a admin whose username:{}", loginRequest.getUsername());

        return new JwtAuthenticationResponse(tokenProvider.token(loginRequest.getUsername()));
    }

}
