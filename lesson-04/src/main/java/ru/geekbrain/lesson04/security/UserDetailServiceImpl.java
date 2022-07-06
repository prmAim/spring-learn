package ru.geekbrain.lesson04.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrain.lesson04.persist.UserRepository;
import java.util.stream.Collectors;

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
                    // Указываем роли
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getNamerole()))
                            .collect(Collectors.toList())
                    // удобно для проверки ЛОгин/Пароли из Оперативной памяти
                    // Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))  // ADMIN - это разрешение (без префикса ROLE_), ROLE_ADMIN - это роль.
            ))
            .orElseThrow(() -> new UsernameNotFoundException("Use '" + username + "' not found"));
  }
}
