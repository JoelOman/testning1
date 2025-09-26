package com.practice2.testning1.User;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String email,
                                         @RequestParam String password) {

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException ("Ogiltig e-postadress.");
        }
        if(password.length() < 6) {
            throw new RuntimeException ("Lösenordet måste vara minst 6 tecken");
        }

        try{
            User user = userService.registerUser(email, password);
            return ResponseEntity.ok(user);
        }
        catch (Exception | UserAlreadyExistsException e) {
            throw new RuntimeException ("Kunde ej registrera användare");
        }

    }

}