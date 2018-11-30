package co.id.app.aisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.id.app.aisa.domain.Permission;

/**
 * 
 * Spring Data JPA repository for the Permission entity.
 *
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
