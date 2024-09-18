package com.spmapi.spmapi.repository;

import com.spmapi.spmapi.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findUserRolesByUserId(Long userId);
}
