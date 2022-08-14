
package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.security.AuthService;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.models.LoginRequest;
import com.ratepay.client.bugtracker.models.LoginResponse;
import com.ratepay.client.bugtracker.models.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthAPI {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        String jwtToken = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        User user = authService.loadUserByUsername(loginRequest.getUsername());
        return ResponseEntity.ok(new LoginResponse(user.getId(), user.getUsername(), jwtToken, user.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){
        User user = authService.register(new User(registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getEmail()));
        return ResponseEntity.ok(user);
    }
}
