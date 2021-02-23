package com.finance.bravenewcoinBack.security.service;

import com.finance.bravenewcoinBack.entity.ApiBrave;
import com.finance.bravenewcoinBack.repository.ApiBraveRepository;
import com.finance.bravenewcoinBack.security.entity.User;
import com.finance.bravenewcoinBack.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApiBraveRepository apiBraveRepository;

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }

    public ApiBrave findCryptoById(Integer id) {
        return apiBraveRepository.findById(id).get();
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
