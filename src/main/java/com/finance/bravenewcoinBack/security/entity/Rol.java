package com.finance.bravenewcoinBack.security.entity;


import com.finance.bravenewcoinBack.security.enums.RolName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolName rolname;

    public Rol() {
    }

    public Rol(@NotNull RolName rolname) {
        this.rolname = rolname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolName getRolname() {
        return rolname;
    }

    public void setRolname(RolName rolname) {
        this.rolname = rolname;
    }
}
