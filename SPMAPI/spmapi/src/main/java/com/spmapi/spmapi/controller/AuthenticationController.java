/* 
package com.spmapi.spmapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        // Login
        return "redirect:/dashboard"; // Başarıyla giriş yaptıktan sonra yönlendirme
    }

    @PostMapping("/signup")
    public String signupUser(@RequestParam String username, @RequestParam String password) {
        // Register
        return "redirect:/login"; // Başarıyla kayıt olduktan sonra yönlendirme
    }
}
*/