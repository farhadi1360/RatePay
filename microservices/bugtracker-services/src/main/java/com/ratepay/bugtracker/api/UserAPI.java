package com.ratepay.bugtracker.api;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.bugtracker.services.UserService;
import com.ratepay.client.bugtracker.models.UserModel;
import com.ratepay.core.rest.impl.BaseRestSqlModeImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserAPI extends BaseRestSqlModeImpl<UserModel, Long> {

    public UserAPI(UserService userService) {
        super(userService);
    }
}
