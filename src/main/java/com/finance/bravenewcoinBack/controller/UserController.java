package com.finance.bravenewcoinBack.controller;

import com.finance.bravenewcoinBack.entity.ApiBrave;
import com.finance.bravenewcoinBack.security.entity.User;
import com.finance.bravenewcoinBack.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/apibrave")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> users() {
        return userService.findAll();
    }

    @PutMapping("/users/{id}/addcrypto")
    ResponseEntity<?> addCrypto(@PathVariable("id") Integer id, @RequestBody ApiBrave apiBrave) {

        Map<String, Object> response = new HashMap<>();
        User user = null;

        try {

            user = userService.findById(id);

        }catch (DataAccessException e) {

            response.put("message", "Usuario no existe");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        try {

            List<ApiBrave> userCryptos = user.getUserCrypto();

            if(userCryptos.stream().filter(item -> item.getSymbol().equals(apiBrave.getSymbol()))
                    .collect(Collectors.toList()).size() > 0) {

                response.put("message", "La moneda ya ha sido asignada al usuario");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

            userCryptos.add(apiBrave);
            user.setUserCrypto(userCryptos);
            user = userService.save(user);

        }catch (DataAccessException e) {

            response.put("message", "Error al asociar la moneda");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause()
            .getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "Moneda asociada con éxito");
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/users/{id}/cryptos")
    ResponseEntity<?> userCryptos(@PathVariable("id") Integer id) {

        Map<String, Object> response = new HashMap<>();
        User user = null;

        try {

            user = userService.findById(id);

        }catch (DataAccessException e) {

            response.put("message", "Usuario no encontrado");
            response.put("error", e.getMessage().concat(": "). concat(e.getMostSpecificCause()
                    .getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("cryptos", user.getUserCrypto());
        System.out.println(user.getUserCrypto().get(0).getId());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/users/{id}/setfavoritecrypto")
    ResponseEntity<?> setFavoriteCrypto(@PathVariable("id") Integer id, @RequestBody ApiBrave apiBrave) {

        Map<String, Object> response = new HashMap<>();
        User user = null;

        try {

                user = userService.findById(id);

        }catch (DataAccessException e) {

            response.put("message", "Usuario no existe");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        if(user == null) {

            response.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        List<ApiBrave> userCryptos = user.getUserCrypto();

        if(userCryptos.stream().filter(item -> (item.getId() == apiBrave.getId()))
        .collect(Collectors.toList()).size() == 0) {

            response.put("message", "La moneda no está asociada al usuario");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        try {

            user.setPreferedCryptoId(apiBrave.getId());
            user = userService.save(user);

        }catch (Exception e) {

            response.put("message", "Error al asignar moneda favorita");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "Moneda asociada con éxito");
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/users/{id}/top3")
    ResponseEntity<?> userTopCryptos(@PathVariable("id") Integer id) {

        Map<String, Object> response = new HashMap<>();
        User user = null;
        ApiBrave preferedCrypto = null;

        try {

            user = userService.findById(id);
            preferedCrypto = userService.findCryptoById(user.getPreferedCryptoId());

        }catch (DataAccessException e) {

            response.put("message", "Usuario o moneda no encontrado");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause()
                    .getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        List<ApiBrave> userCryptos = user.getUserCrypto();
        userCryptos.sort(Comparator.comparing(ApiBrave::getValue).reversed());
        List<ApiBrave> top3Cryptos = new ArrayList<>();
        Double preferedCryptoValue = Double.valueOf(preferedCrypto.getValue());

        for(ApiBrave a: userCryptos) {

            Double valuePreferedCrypto = a.getValue() / preferedCryptoValue;
            a.setValue(valuePreferedCrypto);
            top3Cryptos.add(a);

            if(top3Cryptos.size() == 3) {

                break;

            }

        }

        response.put("cryptos", top3Cryptos);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
