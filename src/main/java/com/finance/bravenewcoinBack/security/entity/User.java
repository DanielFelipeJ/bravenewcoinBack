package com.finance.bravenewcoinBack.security.entity;

import com.finance.bravenewcoinBack.entity.ApiBrave;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_rol", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Rol> roles = new HashSet<>();

    @Column(columnDefinition = "integer default 0")
    private Integer preferedCryptoId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<ApiBrave> userCrypto;

    public User() {
    }

    public User(@NotNull String name, @NotNull String lastname, @NotNull String username, @NotNull String password) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public Integer getPreferedCryptoId() {
        return preferedCryptoId;
    }

    public void setPreferedCryptoId(Integer preferedCryptoId) {
        this.preferedCryptoId = preferedCryptoId;
    }

    public List<ApiBrave> getUserCrypto() {
        return userCrypto;
    }

    public void setUserCrypto(List<ApiBrave> userCrypto) {
        this.userCrypto = userCrypto;
    }
}
