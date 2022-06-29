package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.UserRepository;

import java.util.Collections;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                // new User из пакета org.springframework.security.core.userdetails.User;
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))  // ADMIN - тут роль (без префикса ROLE_)
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Use '" + username + "' not found"));
    }
}
