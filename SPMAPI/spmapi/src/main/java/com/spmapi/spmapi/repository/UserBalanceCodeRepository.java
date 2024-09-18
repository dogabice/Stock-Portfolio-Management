package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.UserBalanceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBalanceCodeRepository extends JpaRepository<UserBalanceCode, Long> {
    List<UserBalanceCode> findByUserId(Long userId);

    List<UserBalanceCode> findByBalanceCodeId(Long balanceCodeId);
}
