package com.spmapi.spmapi.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        // Giriş işlemi burada yapılacak
        return "redirect:/dashboard"; // Başarıyla giriş yaptıktan sonra yönlendirme
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@RequestParam String username, @RequestParam String password) {
        // Kayıt işlemi burada yapılacak
        return "redirect:/login"; // Başarıyla kayıt olduktan sonra yönlendirme
    }
}
