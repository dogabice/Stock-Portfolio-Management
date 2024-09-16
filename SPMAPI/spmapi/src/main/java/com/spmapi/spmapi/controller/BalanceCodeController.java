package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.BalanceCodeDTO;
import com.spmapi.spmapi.DTOs.BalanceDTO;
import com.spmapi.spmapi.model.Balance;
import com.spmapi.spmapi.model.BalanceCode;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.service.BalanceCodeService;
import com.spmapi.spmapi.service.BalanceService;
import com.spmapi.spmapi.service.UserService; // UserService'yi ekleyin
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/balance-codes")
public class BalanceCodeController {

    @Autowired
    private BalanceCodeService balanceCodeService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private UserService userService; // UserService'yi ekleyin

    @GetMapping
    public ResponseEntity<List<BalanceCodeDTO>> getAllBalanceCodes() {
        List<BalanceCode> balanceCodes = balanceCodeService.getAllBalanceCodes();
        List<BalanceCodeDTO> dtoList = balanceCodes.stream()
                .map(code -> new BalanceCodeDTO(code.getId(), code.getCode(), code.getAmount()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BalanceCodeDTO> createBalanceCode(@RequestBody BalanceCodeDTO balanceCodeDTO) {
        BalanceCode balanceCode = new BalanceCode();
        balanceCode.setCode(balanceCodeDTO.getCode());
        balanceCode.setAmount(balanceCodeDTO.getAmount());
        BalanceCode createdBalanceCode = balanceCodeService.saveBalanceCode(balanceCode);
        return new ResponseEntity<>(new BalanceCodeDTO(createdBalanceCode.getId(), createdBalanceCode.getCode(), createdBalanceCode.getAmount()), HttpStatus.CREATED);
    }

    
    @PostMapping("/{id}/assign-balance")
    public ResponseEntity<BalanceDTO> assignBalanceToBalanceCode(@PathVariable Long id, @RequestBody BalanceDTO balanceDTO) {
        BalanceCode balanceCode = balanceCodeService.getAllBalanceCodes()
                .stream()
                .filter(bc -> bc.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (balanceCode == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<User> userOptional = userService.getUserById(balanceDTO.getUserId()); // UserService kullanarak kullanıcıyı alın

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        Balance balance = new Balance();
        balance.setBalance(balanceDTO.getBalance());
        balance.setUser(user);
        Balance createdBalance = balanceService.saveBalance(balance);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BalanceDTO(createdBalance.getId(), createdBalance.getBalance(), createdBalance.getUser().getId()));
    }
        

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalanceCode(@PathVariable Long id) {
        balanceCodeService.deleteBalanceCode(id);
        return ResponseEntity.noContent().build();
    }
}
