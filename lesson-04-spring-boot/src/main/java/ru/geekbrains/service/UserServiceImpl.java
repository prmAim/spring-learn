package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.UserSpecifications;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import java.util.Optional;

/**
 * На уровне сервиса должна быть организована бизнес-логика, транзакционность
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Уже содержит преобразование в лист коллекции
     */
    @Override
    public Page<UserDto> findUsersByFilter(String usernameFilter, String emailFilter,
                                           Integer page, Integer size, String sortField) {
        Specification<User> spec = Specification.where(null);
        if (usernameFilter != null) {
            spec = spec.and(UserSpecifications.usernameContaining(usernameFilter));
        }
        if (emailFilter != null) {
            spec = spec.and(UserSpecifications.emailContaining(emailFilter));
        }
        // PageRequest.of(page, size, Sort.by("id"))) = реализация пагинации page = номер страницы, size = объем страницы
        // Sort.by - сортировка
        return userRepository.findAll(spec, PageRequest.of(page, size, Sort.by("id")))
                .map(UserServiceImpl::userToDto);
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id).map(UserServiceImpl::userToDto);
    }

    @Override
    public UserDto save(UserDto user) {
        return userToDto(userRepository.save(
                        new User(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getPassword()
                        )));
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Преобразование из DAO в DTO
     */
    private static UserDto userToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), null);
    }
}
