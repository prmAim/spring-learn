package ru.geekbrain.lesson04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrain.lesson04.dto.RoleDto;
import ru.geekbrain.lesson04.dto.UserDto;
import ru.geekbrain.lesson04.persist.Role;
import ru.geekbrain.lesson04.persist.RoleRepository;
import ru.geekbrain.lesson04.persist.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * На уровне сервиса должна быть организована бизнес-логика
 */
@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Set<RoleDto> findAll() {
    return roleRepository.findAll().stream().map(role -> roleDaoToDto(role)).collect(Collectors.toSet());
  }

  /**
   * Преобразование из DAO в DTO
   */
  private static RoleDto roleDaoToDto(Role role) {
    return new RoleDto(role.getId(), role.getNamerole(), UsersDaoToDto(role.getUsers()));
  }

  /**
   * Преобразование класса <Role> из DAO в DTO
   */
  private static Set<UserDto> UsersDaoToDto(Set<User> users) {
    Set<UserDto> usersDto = new HashSet<>();
    for (User user : users) {
      usersDto.add(new UserDto(user.getId(), user.getUsername(), user.getEmail(), null, roleDaoToDto(user.getRoles())));
    }
    return usersDto;
  }

  /**
   * Преобразование класса <Role> из DAO в DTO
   */
  private static Set<RoleDto> roleDaoToDto(Set<Role> roles) {
    Set<RoleDto> rolesDto = new HashSet<>();
    for (Role role : roles) {
      rolesDto.add(new RoleDto(role.getId(), role.getNamerole(), null));
    }
    return rolesDto;
  }
}
