package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUserId(Long userId);

    Optional<Balance> findByUser(User user);
}
