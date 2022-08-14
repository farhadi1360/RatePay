package com.ratepay.bugtracker.config;


import com.ratepay.bugtracker.utils.Constants;
import com.ratepay.bugtracker.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Optional<String> user = Optional.of(SecurityUtils.getCurrentUserLogin());

        String currentUser = ("".equalsIgnoreCase(user.get()) ? Constants.SYSTEM_ACCOUNT : user.get());

        return Optional.ofNullable(currentUser);
    }
}
