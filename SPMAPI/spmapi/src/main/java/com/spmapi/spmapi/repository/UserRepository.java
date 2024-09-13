package com.spmapi.spmapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spmapi.spmapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT ur.user FROM UserRole ur JOIN ur.role r WHERE r.roleName = :roleName")
    List<User> findUsersByRole(String roleName);
}