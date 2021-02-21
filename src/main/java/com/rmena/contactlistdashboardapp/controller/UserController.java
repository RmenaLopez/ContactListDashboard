package com.rmena.contactlistdashboardapp.user;

import com.rmena.contactlistdashboardapp.models.User;
import com.rmena.contactlistdashboardapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String username
            , @RequestParam String password) {

        User n = new User();
        n.setUsername(username);
        n.setPassword(password);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}
