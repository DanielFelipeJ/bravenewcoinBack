package com.finance.bravenewcoinBack.security.controller;

import com.finance.bravenewcoinBack.dto.Message;
import com.finance.bravenewcoinBack.security.dto.JwtDto;
import com.finance.bravenewcoinBack.security.dto.LoginUser;
import com.finance.bravenewcoinBack.security.dto.NewUser;
import com.finance.bravenewcoinBack.security.entity.Rol;
import com.finance.bravenewcoinBack.security.entity.User;
import com.finance.bravenewcoinBack.security.enums.RolName;
import com.finance.bravenewcoinBack.security.jwt.JwtProvider;
import com.finance.bravenewcoinBack.security.service.RolService;
import com.finance.bravenewcoinBack.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/newUser")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ResponseEntity(new Message("Datos incorrectos o faltantes"), HttpStatus.BAD_REQUEST);
        }
        if(userService.existsByUsername(newUser.getUsername())) {
            return new ResponseEntity(new Message("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(newUser.getName(), newUser.getLastname(), newUser.getUsername(),
                        passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROL_USER).get());
        if(newUser.getRoles().contains("admin")) {
            roles.add(rolService.getByRolName(RolName.ROL_ADMIN).get());
        }
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Message("Usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Message("Datos incorrectos o faltantes"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser
                .getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername()).get();
        user.setUserCrypto(new ArrayList<>());
        JwtDto jwtDto = new JwtDto(user, jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
