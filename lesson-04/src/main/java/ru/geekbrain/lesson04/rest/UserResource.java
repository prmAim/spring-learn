package ru.geekbrain.lesson04.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geekbrain.lesson04.dto.UserDto;
import ru.geekbrain.lesson04.exception.NotFoundException;
import ru.geekbrain.lesson04.service.UserService;

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

    @Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
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

    @Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
    @GetMapping("/{id}/id")
    public UserDto findById(@PathVariable long id) {
        return userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Secured("ROLE_SUPERADMIN")
    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Created user shouldn't have id");
        }
        return userService.save(user);
    }

    @Secured("ROLE_SUPERADMIN")
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
