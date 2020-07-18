package com.ag04smarts.scc.security;

import com.ag04smarts.scc.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUserDetails loadUserByUsername(String username) throws BadCredentialsException {
        return userRepository.findUserByUsername(username).map(MyUserDetails::new)
                .orElseThrow(() -> new BadCredentialsException(username + " not found"));
    }
}
