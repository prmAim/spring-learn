package ru.geekbrain.lesson04.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  public void authConfig(AuthenticationManagerBuilder auth,
                         UserDetailsService userDetailsService) throws Exception {
    // кодирование строки пароль
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // Создаем пользователей, которые храняться в оперативной памяти
    auth.inMemoryAuthentication()
            .withUser("admin_from_memory")
            // .password("password") = Так делать нельзя. Строковые типы храняться все вместе в отдельном стеке памяти. Их легко найти.
            // Злоумышленик может снять дамп памяти и в нем найти строковые значения.
            // .password("password")
            .password(encoder.encode("secret"))
            .roles("ADMIN")
            .and()
            .withUser("guest_from_memory")
            .password(encoder.encode("secret"))
            .roles("GUEST");

    // Создаем пользователей, которые храняться в БД
    auth.userDetailsService(userDetailsService);
  }


}