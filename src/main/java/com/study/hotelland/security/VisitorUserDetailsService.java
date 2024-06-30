package com.study.hotelland.security;

import com.study.hotelland.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorUserDetailsService implements UserDetailsService {

    private final VisitorService visitorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new VisitorUserDetails(visitorService.findByName(username));
    }
}
