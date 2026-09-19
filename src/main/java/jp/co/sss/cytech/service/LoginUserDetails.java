package jp.co.sss.cytech.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.co.sss.cytech.entity.User;

public class LoginUserDetails implements UserDetails {

    private final User user;

    public LoginUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

//    @Override
//    public String getUsername() {
//        return user.getEmail();
//    }
    public String getUserName() {
        return user.getUserName();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}