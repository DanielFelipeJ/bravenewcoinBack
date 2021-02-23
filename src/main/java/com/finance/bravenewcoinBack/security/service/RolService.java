package com.finance.bravenewcoinBack.security.service;

import com.finance.bravenewcoinBack.security.entity.Rol;
import com.finance.bravenewcoinBack.security.enums.RolName;
import com.finance.bravenewcoinBack.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(RolName rolname) {
        return rolRepository.findByRolname(rolname);
    }

    public void save(Rol rol) {
        rolRepository.save(rol);
    }
}
