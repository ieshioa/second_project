package com.green.glampick.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public MyUser getLoginUser() {
        MyUserDetail myUserDetails = (MyUserDetail)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return myUserDetails == null ? null : myUserDetails.getMyUser();
    }

    public long getLoginUserId() {
        MyUser myUser = getLoginUser();
        return myUser == null ? 0 : myUser.getUserId();
    }

}
