package cz.cvut.fel.sit.controller;

import cz.cvut.fel.sit.mapper.RegisterMapper;
import cz.cvut.fel.sit.dto.request.LoginRequest;
import cz.cvut.fel.sit.dto.request.RegisterRequest;
import cz.cvut.fel.sit.dto.response.LoginResponse;
import cz.cvut.fel.sit.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final RegisterMapper registerMapper;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.createUser(registerMapper.toEntity(registerRequest), registerRequest.getPasswordCheck());
        return ResponseEntity.ok().build();
    }



    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(new LoginResponse(userService.loginUser(request)));
    }
}

