package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.UserBalanceCode;
import com.spmapi.spmapi.repository.UserBalanceCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBalanceCodeService {
    //----------------------------------------------------------------     
    @Autowired
    private UserBalanceCodeRepository userBalanceCodeRepository;
    //---------------------------------------------------------------- 
    public List<UserBalanceCode> getAllUserBalanceCodes() {
        return userBalanceCodeRepository.findAll();
    }
    //---------------------------------------------------------------- 
    public UserBalanceCode saveUserBalanceCode(UserBalanceCode userBalanceCode) {
        return userBalanceCodeRepository.save(userBalanceCode);
    }
    //---------------------------------------------------------------- 
    public void deleteUserBalanceCode(Long id) {
        userBalanceCodeRepository.deleteById(id);
    }
    //----------------------------------------------------------------     
}
