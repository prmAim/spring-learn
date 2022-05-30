package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.exception.NotFoundException;
import ru.geekbrains.service.UserService;

import java.sql.SQLException;
import java.util.Optional;

@RequestMapping("/rest/v1/user")
@RestController
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    private Page<UserDto> findAll(@RequestParam Optional<String> usernameFilter,
                                  @RequestParam Optional<String> emailFilter,
                                  @RequestParam Optional<Integer> page,
                                  @RequestParam Optional<Integer> size,
                                  @RequestParam Optional<String> sortField) {
        String usernameFilterValue = usernameFilter
                .filter(s -> !s.isBlank())
                .orElse(null);
        String emailFilterValue = emailFilter
                .filter(s -> !s.isBlank())
                .orElse(null);
        Integer pageValue = page.orElse(1) - 1;
        Integer sizeValue = size.orElse(3);
        String sortFieldValue = sortField
                .filter(s -> !s.isBlank())
                .orElse("id");
        return userService.findUsersByFilter(
                usernameFilterValue,
                emailFilterValue,
                pageValue,
                sizeValue,
                sortFieldValue);
    }

    @GetMapping("/{id}/id")
    public UserDto findById(@PathVariable long id) {
        return userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Created user shouldn't have id");
        }
        return userService.save(user);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Created user should have id");
        }
        return userService.save(user);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public String notFoundException(NotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String illegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String sqlException(SQLException ex) {
        return ex.getMessage();
    }
}
