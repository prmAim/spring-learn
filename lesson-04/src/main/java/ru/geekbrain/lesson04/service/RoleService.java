package ru.geekbrain.lesson04.service;

import ru.geekbrain.lesson04.dto.RoleDto;
import ru.geekbrain.lesson04.persist.Role;

import java.util.Set;

/**
 * Логическая часть с ролями пользователей
 */
public interface RoleService {

  Set<RoleDto> findAll();
}
