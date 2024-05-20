package pl.tumanski.discounthub.usermanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tumanski.discounthub.usermanagement.model.entity.Role;

import java.util.Optional;

interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
