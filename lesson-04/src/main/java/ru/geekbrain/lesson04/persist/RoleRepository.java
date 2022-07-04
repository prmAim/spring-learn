package ru.geekbrain.lesson04.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrain.lesson04.dto.RoleDto;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query("select r " +
          " from Role r " +
          "where r.id in :ids")
  Set<RoleDto> findAllByIds(@Param("ids") Long[] ids);
}
