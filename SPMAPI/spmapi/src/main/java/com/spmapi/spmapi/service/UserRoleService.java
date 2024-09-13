package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.UserRole;
import com.spmapi.spmapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> getUserRoleById(Long id) {
        return userRoleRepository.findById(id);
    }

    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public void deleteUserRole(Long id) {
        userRoleRepository.deleteById(id);
    }

    public List<UserRole> getUserRolesByUserId(Long userId) {
        return userRoleRepository.findUserRolesByUserId(userId);
    }
}
