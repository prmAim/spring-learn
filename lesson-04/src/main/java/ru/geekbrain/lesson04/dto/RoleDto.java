package ru.geekbrain.lesson04.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

/**
 * Разделение уровней сервиса [валидация front] и уровня сущности DB из-за принципа единства отвественности
 */
public class RoleDto {

  private Long id;

  @NotBlank
  private String namerole;

  @JsonIgnore
  private Set<UserDto> userDto;

  public RoleDto(Long id, String namerole, Set<UserDto> userDto) {
    this.id = id;
    this.namerole = namerole;
    this.userDto = userDto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNamerole() {
    return namerole;
  }

  public void setNamerole(String namerole) {
    this.namerole = namerole;
  }

  public Set<UserDto> getUserDto() {
    return userDto;
  }

  public void setUserDto(Set<UserDto> userDto) {
    this.userDto = userDto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RoleDto roleDto = (RoleDto) o;
    return id.equals(roleDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
