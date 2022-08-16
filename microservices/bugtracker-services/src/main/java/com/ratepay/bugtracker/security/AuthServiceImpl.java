package com.ratepay.bugtracker.security;

import com.ratepay.bugtracker.exceptions.custom.EmailAlreadyInUse;
import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.exceptions.custom.InvalidCredentialsException;
import com.ratepay.bugtracker.exceptions.custom.UsernameNotAvailableException;
import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.bugtracker.services.RoleService;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.enume.RoleName;
import com.ratepay.client.bugtracker.mapper.RoleMapper;
import com.ratepay.client.bugtracker.models.RegisterRequest;
import com.ratepay.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;
    private final UserService userService;
    private static final ResponseDto DONE = new ResponseDto(true);
    private static final ResponseDto FAIL = new ResponseDto(false);

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Username " + username + " not found!"));
    }

    public String authenticate(String username, String password) throws InvalidCredentialsException {
        UserDetails userDetails = loadUserByUsername(username);
        boolean correctPassword = passwordEncoder.matches(password, userDetails.getPassword());
        if (!correctPassword) {
            throw new InvalidCredentialsException("Bad credentials");
        }
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtProvider.generateToken(userDetails);
    }

    public ResponseDto register(RegisterRequest registerRequest) {
        checkUsernameAvailability(registerRequest.getUsername());
        checkEmailAvailability(registerRequest.getEmail());
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        registerRequest.getRoles().stream().forEach(role -> {
            user.getRoles().add(roleMapper.toEntity(roleService.findByRoleName(RoleName.valueOf(role.toUpperCase()))));
        });
        userRepository.save(user);
        return DONE;
    }

    public ResponseDto updateUser(Long userId, RegisterRequest registerRequest) throws EntityNotFoundException {
        Optional<User> userSearched = userRepository.findById(userId);
        if (userSearched.isPresent()) {
            userSearched.get().setUsername(registerRequest.getUsername());
            userSearched.get().setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            registerRequest.getRoles().stream().forEach(role -> {
                userSearched.get().getRoles().add(roleMapper.toEntity(roleService.findByRoleName(RoleName.valueOf(role.toUpperCase()))));
            });
            userRepository.save(userSearched.get());
        } else {
            throw new EntityNotFoundException("currentUser base on principal of request is not found!");
        }
        return DONE;
    }


    private void checkUsernameAvailability(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameNotAvailableException("Username " + username + " is not available");
        }
    }

    private void checkEmailAvailability(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyInUse("Email " + email + " is already in use");
        }
    }
}
