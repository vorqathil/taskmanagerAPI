package com.vorqathil.taskmanager.controllers;

import com.vorqathil.taskmanager.dto.AuthenticationDTO;
import com.vorqathil.taskmanager.models.User;
import com.vorqathil.taskmanager.services.JwtService;
import com.vorqathil.taskmanager.services.AuthService;
import com.vorqathil.taskmanager.util.UserValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    public AuthController(AuthService authService, UserValidator userValidator, JwtService jwtService, ModelMapper modelMapper) {
        this.authService = authService;
        this.userValidator = userValidator;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid AuthenticationDTO authDTO, BindingResult bindingResult) {
        User user = modelMapper.map(authDTO, User.class);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            return ResponseEntity.badRequest().body("Registration failed: " + error);
        }
        authService.register(user);
        String token = jwtService.generateAccessToken(user.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO authDTO) {
        return ResponseEntity.ok(authService.login(authDTO));
    }
}
