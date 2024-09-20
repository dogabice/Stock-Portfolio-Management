package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.DTOs.CreateUserDTO;
import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.service.PortfolioService;
import com.spmapi.spmapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {
    //-------------------------------------------------------------------
    @Autowired
    private UserService userService;
    //-------------------------------------------------------------------
    @Autowired
    private PortfolioService portfolioService;
    //-------------------------------------------------------------------
    //MAPPINGS
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //-------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    //-------------------------------------------------------------------
    @DeleteMapping("/{id}")
       public void deleteOneUser(@PathVariable Long id){
           userService.deleteUserById(id);
    }
    //-------------------------------------------------------------------------------------------------------------
    //REGISTER
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        long userCount = userService.getUserCount();

        // DTO to USER
        User user = userService.CreateUserDTOToUser(createUserDTO);

        // If this is the first regiter or the role is ADMIN.
        if ("admin".equalsIgnoreCase(user.getRole()) || userCount == 0) {
            user.setRole("admin");
        } else {
            user.setRole("user");
        }

        // Create User
        User createdUser = userService.createUser(user);

        // Create empty portfolio for the new user.
        portfolioService.createPortfolioForUser(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    //-------------------------------------------------------------------------------------------------------------
    //ADMIN TRANSACTIONS
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<?> createUserWithRole(@RequestBody CreateUserDTO createUserDTO, @RequestParam String role) {
        // Roles should be "admin" or "user".
        if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("user")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid role. Only 'admin' or 'user' roles are allowed.");
        }

        User user = userService.CreateUserDTOToUser(createUserDTO);

        user.setRole(role.toLowerCase());

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    //-------------------------------------------------------------------------
}
