package br.com.ota.cpjbackend.repository;

import br.com.ota.cpjbackend.model.Role;
import br.com.ota.cpjbackend.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName roleName);

    List<Role> findManyByNameIn(List<RoleName> rolesName);

}
