package com.rmena.contactlistdashboardapp.controller;

import com.rmena.contactlistdashboardapp.models.Role;
import com.rmena.contactlistdashboardapp.models.User;
import com.rmena.contactlistdashboardapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewUser (@RequestParam String role) {
        Role n = new Role();
        n.setUserRole(role);
        roleRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Role> getAllUsers() {
        return roleRepository.findAll();
    }

}
