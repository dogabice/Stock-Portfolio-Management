package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.UserBalanceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBalanceCodeRepository extends JpaRepository<UserBalanceCode, Long> {
    // Belirli bir kullanıcıya ait balance kodlarının listelenmesi
    List<UserBalanceCode> findByUserId(Long userId);

    // Belirli bir balance koduna ait kullanıcıları listeleme
    List<UserBalanceCode> findByBalanceCodeId(Long balanceCodeId);
}
