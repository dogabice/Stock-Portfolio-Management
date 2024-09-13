package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.Portfolio;
import com.spmapi.spmapi.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
}
