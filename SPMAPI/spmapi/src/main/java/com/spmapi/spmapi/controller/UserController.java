package com.spmapi.spmapi.controller;

import com.spmapi.spmapi.model.User;
import com.spmapi.spmapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user) {
        long userCount = userService.getUserCount();
    
        if ("admin".equalsIgnoreCase(user.getRole()) || userCount == 0) {
            user.setRole("admin");
        } else {
            user.setRole("user");
        }
    
        // Kullanıcıyı oluşturur ve portföyü de otomatik olarak oluşturur
        User createdUser = userService.createUser(user);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    

@DeleteMapping("/{id}")
public void deleteOneUser(@PathVariable Long id){
    userService.deleteUserById(id);
}



    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setRole(user.getRole());
            updatedUser.setBalance(user.getBalance());
            User savedUser = userService.saveUser(updatedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}/assign-role")
    public ResponseEntity<User> assignRole(@PathVariable Long id, @RequestParam String role) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            User user = userService.assignRole(existingUser.get(), role);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}/update-balance")
    public ResponseEntity<User> updateUserBalance(@PathVariable Long id, @RequestParam double amount) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            User updatedUser = userService.updateUserBalance(user, amount);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
