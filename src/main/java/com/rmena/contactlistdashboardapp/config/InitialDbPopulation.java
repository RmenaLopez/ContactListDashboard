package com.rmena.contactlistdashboardapp.config;

import com.rmena.contactlistdashboardapp.models.Contact;
import com.rmena.contactlistdashboardapp.models.Role;
import com.rmena.contactlistdashboardapp.models.User;
import com.rmena.contactlistdashboardapp.repository.ContactRepository;
import com.rmena.contactlistdashboardapp.repository.RoleRepository;
import com.rmena.contactlistdashboardapp.repository.UserRepository;
import com.rmena.contactlistdashboardapp.service.ContactsService;
import com.rmena.contactlistdashboardapp.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;
import java.util.Scanner;



@Component
public class InitialDbPopulation {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Value("${app.default.user}")
    private String rootUser;

    @Value("${app.default.password}")
    private String password;

    @Value("${app.default.roles.admin}")
    private String adminRole;

    @Value("${app.default.roles.user}")
    String baseUserRole;

    @EventListener
    public void initializeDb(ContextRefreshedEvent event) throws IOException {
        Role rootRole = new Role(adminRole);
        Role baseRole = new Role(baseUserRole);

        User user = new User(rootUser, password);

        if (roleRepository.findByUserRole(adminRole) == null){
            roleRepository.save(rootRole);
        }

        if (roleRepository.findByUserRole(baseUserRole) == null){
            roleRepository.save(baseRole);
        }

        if (userRepository.findByUsername(rootUser) == null){
            service.saveUser(user, adminRole);
        }

        String fileName = "config/names.txt";
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());
        Random random = new Random();
        try {
            Scanner myReader = new Scanner(file);
            Contact contact = new Contact();
            while (myReader.hasNextLine()) {
                contact = new Contact();
                contact.setUser(user);
                String contactName = myReader.nextLine();
                contact.setContactName(contactName);
                contact.setAge(String.valueOf(random.nextInt(100)));
                contact.setNickname(contactName.substring(0,3));
                contact.setPhone("662" +
                                String.valueOf(random.nextInt(5)) +
                                String.valueOf(random.nextInt(999999 - 100000) + 100000)
                        );
                contactRepository.save(contact);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
