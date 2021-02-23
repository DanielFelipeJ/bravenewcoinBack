package com.finance.bravenewcoinBack;

import com.finance.bravenewcoinBack.security.entity.Rol;
import com.finance.bravenewcoinBack.security.enums.RolName;
import com.finance.bravenewcoinBack.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        Rol rolAdmin    = new Rol(RolName.ROL_ADMIN);
        Rol rolUser     = new Rol(RolName.ROL_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);
    }
}
