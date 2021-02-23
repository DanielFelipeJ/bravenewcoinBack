package com.finance.bravenewcoinBack.security.repository;

import com.finance.bravenewcoinBack.security.entity.Rol;
import com.finance.bravenewcoinBack.security.enums.RolName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolname(RolName rolname);

}
