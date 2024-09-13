package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.BalanceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceCodeRepository extends JpaRepository<BalanceCode, Long> {
}
