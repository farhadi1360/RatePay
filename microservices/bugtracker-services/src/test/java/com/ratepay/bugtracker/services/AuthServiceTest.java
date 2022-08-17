package com.ratepay.bugtracker.services;

import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.bugtracker.security.AuthServiceImpl;
import com.ratepay.bugtracker.security.JwtProvider;
import com.ratepay.bugtracker.utils.UserPrepareInfo;
import com.ratepay.client.bugtracker.mapper.RoleMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtProvider jwtProvider;
    @Mock
    RoleService roleService;
    @Mock
    UserService userService;
    @Mock
    RoleMapper roleMapper;
    @InjectMocks
    AuthServiceImpl authService;

    @BeforeEach
    void setMockOutput() {
        when(userRepository.save(UserPrepareInfo.getUser())).thenReturn(UserPrepareInfo.getUser());

    }

    @DisplayName("Test Mock Register User ")
    @Test
    @Order(1)
    public void registerTest() {
        assertEquals(UserPrepareInfo.mockAnswer(), authService.register(UserPrepareInfo.userRequest()));
    }

    @DisplayName("Test Mock Authenticate User")
    @Test
    @Order(2)
    public void authenticateTest() {
        String userName="ratepay1";
        String password="123";
        when(passwordEncoder.matches("123", "$2a$10$vkmPHBa3Fd4pl3Ue/XV0Ve2YcpOHf2.oHg6KjtyaNjJMCLh/Ww1HK")).thenReturn(true);
        when(userRepository.findByUsername("ratepay1")).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        when(jwtProvider.generateToken(UserPrepareInfo.getUser())).thenReturn("$2a$10$vkmPHBa3Fd4pl3Ue/XV0Ve2YcpOHf2.oHg6KjtyaNjJMCLh/Ww1HK");
        assertEquals(null, authService.authenticate(userName, password));
    }
    @DisplayName("Test Mock Update User")
    @Test
    @Order(3)
    public void updateUserTest(){
        when(userRepository.findById(1l)).thenReturn(Optional.of(UserPrepareInfo.getUser()));
        assertEquals(UserPrepareInfo.mockAnswer(), authService.updateUser(1l,UserPrepareInfo.userRequest()));

    }


}
