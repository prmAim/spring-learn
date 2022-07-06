package ru.geekbrain.lesson04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrain.lesson04.dto.RoleDto;
import ru.geekbrain.lesson04.dto.UserDto;
import ru.geekbrain.lesson04.persist.Role;
import ru.geekbrain.lesson04.persist.User;
import ru.geekbrain.lesson04.persist.UserRepository;
import ru.geekbrain.lesson04.persist.UserSpecifications;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
            .map(UserServiceImpl::userDaoToDto);
  }

  @Override
  public Optional<UserDto> findById(long id) {
    return userRepository.findById(id).map(user -> userDaoToDto(user));
  }

  @Override
  public UserDto save(UserDto user) {
    return userDaoToDto(userRepository.save(
            new User(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    setRoleToDao(user.getRolesDto())
            )));
  }

  @Override
  public void deleteById(long id) {
    userRepository.deleteById(id);
  }

  /**
   * Преобразование класса <User> из DAO в DTO
   */
  private static UserDto userDaoToDto(User user) {
    return new UserDto(user.getId(), user.getUsername(), user.getEmail(), null, setRoleToDto(user.getRoles()));
  }

  /**
   * Преобразование класса <User> из DTO в DAO
   */
  private static Set<User> setUserToDao(Set<UserDto> usersDto) {
    Set<User> user = new HashSet<>();
    for (UserDto userDto : usersDto) {
      user.add(new User(userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), setRoleToDao(userDto.getRolesDto())));
    }
    return user;
  }

  /**
   * Преобразование класса <User> из DAO в DTO
   */
  private static Set<UserDto> setUserToDto(Set<User> users) {
    Set<UserDto> usersDto = new HashSet<>();
    for (User user : users) {
      usersDto.add(new UserDto(user.getId(), user.getUsername(), user.getEmail(), null, setRoleToDto(user.getRoles())));
    }
    return usersDto;
  }

  /**
   * Преобразование класса <Role> из DAO в DTO
   */
  private static Set<RoleDto> setRoleToDto(Set<Role> roles) {
    Set<RoleDto> rolesDto = new HashSet<>();
    for (Role role : roles) {
      rolesDto.add(new RoleDto(role.getId(), role.getNamerole(), null));
    }
    return rolesDto;
  }

  /**
   * Преобразование класса <Role> из DTO в DAO
   */
  private static Set<Role> setRoleToDao(Set<RoleDto> rolesDto) {
    Set<Role> roles = new HashSet<>();
    for (RoleDto roleDto : rolesDto) {
      roles.add(new Role(roleDto.getId(), roleDto.getNamerole(), setUserToDao(roleDto.getUserDto())));
    }
    return roles;
  }
}
