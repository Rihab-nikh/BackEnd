package org.example.backend.security;

import org.example.backend.entities.User;
import org.example.backend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = accountService.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            user.getRoles().forEach(role -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
                grantedAuthorities.add(grantedAuthority);
            });
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    grantedAuthorities);
        }

    }


}