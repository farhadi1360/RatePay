package com.ratepay.bugtracker.services.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.querydsl.core.types.Predicate;
import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.service.impl.MainServiceSQLModeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends MainServiceSQLModeImpl<UserModel, User,Long> implements UserService<UserModel, Long> {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper,UserRepository userRepository) {
        super(userMapper,userRepository);
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }
    @Override
    public Predicate queryBuilder(UserModel filter) {
        return null;
    }
}
