package com.spmapi.spmapi.service;

import com.spmapi.spmapi.model.BalanceCode;
import com.spmapi.spmapi.repository.BalanceCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceCodeService {

    @Autowired
    private BalanceCodeRepository balanceCodeRepository;

    public List<BalanceCode> getAllBalanceCodes() {
        return balanceCodeRepository.findAll();
    }

    public Optional<BalanceCode> getBalanceCodeById(Long id) {
        return balanceCodeRepository.findById(id);
    }

    public BalanceCode saveBalanceCode(BalanceCode balanceCode) {
        return balanceCodeRepository.save(balanceCode);
    }

    public void deleteBalanceCode(Long id) {
        balanceCodeRepository.deleteById(id);
    }
}
