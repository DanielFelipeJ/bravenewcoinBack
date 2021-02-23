package com.finance.bravenewcoinBack.repository;

import com.finance.bravenewcoinBack.entity.ApiBrave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiBraveRepository extends JpaRepository<ApiBrave, Integer> {
}
