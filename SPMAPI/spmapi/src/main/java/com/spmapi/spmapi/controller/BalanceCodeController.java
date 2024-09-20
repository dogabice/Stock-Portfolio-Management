package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.BalanceCodeDTO;
import com.spmapi.spmapi.model.BalanceCode;
import com.spmapi.spmapi.service.BalanceCodeService;
import com.spmapi.spmapi.service.UserBalanceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/balance-codes")
public class BalanceCodeController {
    //-------------------------------------------------------------------    
    //SERVICES
    @Autowired
    private BalanceCodeService balanceCodeService;
      //-------------------------------------------------------------------
      @Autowired
      private UserBalanceCodeService userBalanceCodeService;  
    //-------------------------------------------------------------------
    //MAPPINGS
    @GetMapping
    public ResponseEntity<List<BalanceCodeDTO>> getAllBalanceCodes() {
        List<BalanceCode> balanceCodes = balanceCodeService.getAllBalanceCodes();
        List<BalanceCodeDTO> dtoList = balanceCodes.stream()
                .map(code -> new BalanceCodeDTO( code.getCode(), code.getAmount()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    //-------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<?> createBalanceCode(@RequestBody BalanceCodeDTO balanceCodeDTO) {
        // Input validation
        if (balanceCodeDTO.getCode() == null || balanceCodeDTO.getCode().isEmpty()) {
            return new ResponseEntity<>("Code cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (balanceCodeDTO.getAmount() == null || balanceCodeDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>("Amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        // Create and save BalanceCode
        BalanceCode balanceCode = new BalanceCode();
        balanceCode.setCode(balanceCodeDTO.getCode());
        balanceCode.setAmount(balanceCodeDTO.getAmount());
        BalanceCode createdBalanceCode = balanceCodeService.saveBalanceCode(balanceCode);

        // Return the created BalanceCode
        return new ResponseEntity<>(new BalanceCodeDTO(createdBalanceCode.getCode(), createdBalanceCode.getAmount()), HttpStatus.CREATED);
    }
    //-------------------------------------------------------------------
    @PostMapping("/{balanceCodeId}/assign-balance/{userId}")
    public ResponseEntity<?> assignBalanceCodeToUser(@PathVariable Long userId, @PathVariable Long balanceCodeId) {
        // Assign balance code to user
        boolean isAssigned = userBalanceCodeService.assignBalanceCodeToUser(userId, balanceCodeId);

        if (isAssigned) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Balance code successfully assigned to the user and balance updated.");
        } else {
            return new ResponseEntity<>("User or BalanceCode not found.", HttpStatus.BAD_REQUEST);
        }
    }

    //-------------------------------------------------------------------
}
