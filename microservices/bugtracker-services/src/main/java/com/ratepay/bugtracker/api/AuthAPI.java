
package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.security.AuthServiceImpl;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.models.LoginRequest;
import com.ratepay.client.bugtracker.models.LoginResponse;
import com.ratepay.client.bugtracker.models.RegisterRequest;
import com.ratepay.core.dto.BaseResponseDTO;
import com.ratepay.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthAPI {

    private final AuthServiceImpl authServiceImpl;


    @PostMapping("/login")
    public BaseResponseDTO<?> login(@Valid @RequestBody LoginRequest loginRequest){
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String jwtToken = authServiceImpl.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        User user = authServiceImpl.loadUserByUsername(loginRequest.getUsername());
        return BaseResponseDTO.ok(new LoginResponse(user.getId(), user.getUsername(), jwtToken, user.getAuthorities()));
    }

    @PostMapping("/register")
    public BaseResponseDTO<?> register(@Valid @RequestBody RegisterRequest registerRequest){
        ResponseDto result = authServiceImpl.register(registerRequest);
        return BaseResponseDTO.ok(result);
    }

    @PutMapping("/edit-user")
    public BaseResponseDTO<?> updateUser(@RequestParam(name = "id") Long userId,@Valid @RequestBody RegisterRequest registerRequest){
        ResponseDto result = authServiceImpl.updateUser(userId, registerRequest);
        return BaseResponseDTO.ok(result);
    }



}
