package ru.geekbrain.lesson04.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@EnableWebSecurity
// Включение авторизации на уровне методов
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration {

  // Настройка аутентификации
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
            .roles("ADMIN")     // Указываем без ROLE_
            .and()
            .withUser("guest_from_memory")
            .password(encoder.encode("secret"))
            .roles("GUEST");    // Указываем без ROLE_

    // Создаем пользователей, которые храняться в БД
    auth.userDetailsService(userDetailsService);
  }

  // Настройка авторизации через REST
  @Configuration
  @Order(1)     // последовательность обработки. Этап № 1.
  public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .antMatcher("/rest/v1/product/**")
              .authorizeRequests()
              .anyRequest().permitAll()   // разрешить всем
              .and()
              .httpBasic()    // для REST API тип авторизации базовый
              .and()
              .csrf().disable()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // постоянная авторизация. Сессия не сохраняется.
    }
  }

  // Настройка авторизации WEB via form
  @Configuration
  @Order(2)     // последовательность обработки. Этап № 2.
  public static class UiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              // Какие запросы URL доступны роли
              .authorizeRequests()    // Авторизация конкретных запросов
              // /**/ = любая папка, /*/ = папка одной вложености
              // Разрешим всем
              // НЕ ПУТАТЬ [authorizeRequests()] + antMatchers() <> antMatcher()
              .antMatchers("/**/*.css", "/**/*.js").permitAll()
              .antMatchers("/error").permitAll()
              .antMatchers("/accsess_denied").authenticated()   // только авторизованым
              .antMatchers("/login").permitAll()
              .antMatchers("/").permitAll()
              // Органичиваем
              // hasAnyRole = добавляем префикс ROLE_
              // hasAnyAuthority() = без префикса
              .antMatchers("/user/**").hasAnyRole("ADMIN", "SUPERADMIN")
              .antMatchers("/product/**").hasAnyRole("ADMIN", "SUPERADMIN", "MANAGER")
              .and()
              .formLogin()                            // авторизация с помощью формы
              .loginPage("/login")                    // Указание своей страницы AAA URL: .../login
              .loginProcessingUrl("/authenticateTheUser")
              //.defaultSuccessUrl("/")               // если ААА прошла успешно, то переадресуем на этот URL
              .successHandler((req, res, auth) -> {   // Гибкий подход
                Set<String> auths = auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                // если роль у пользователя ROLE_ADMIN или ROLE_SUPERADMIN, то переходим после авторизации на URL: .../user, иначе все остальные на URL: .../
                if (auths.contains("ROLE_ADMIN") || auths.contains("ROLE_SUPERADMIN")) {
                  res.sendRedirect(contextPath + "/user");
                } else {
                  if (auths.contains("ROLE_MANAGER")) {
                    res.sendRedirect(contextPath + "/product");
                  } else {
                    res.sendRedirect(contextPath + "/");
                  }
                }
              })
              .and()
              .exceptionHandling()                  // обработка ошибок
              .accessDeniedPage("/access_denied");   // Если не авторизовался, то URL: .../access_denied

    }
  }

}