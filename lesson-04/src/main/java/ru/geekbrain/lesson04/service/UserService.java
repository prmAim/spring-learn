package ru.geekbrain.lesson04.service;

import org.springframework.data.domain.Page;
import ru.geekbrain.lesson04.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Page<UserDto> findUsersByFilter(String usernameFilter, String emailFilter, Integer page, Integer size, String sortField);

    Optional<UserDto> findById(long id);

    UserDto save (UserDto user);

    void deleteById(long id);
}
