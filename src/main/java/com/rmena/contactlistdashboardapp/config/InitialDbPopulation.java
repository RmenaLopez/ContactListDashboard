package com.rmena.contactlistdashboardapp.config;

import com.rmena.contactlistdashboardapp.models.Contact;
import com.rmena.contactlistdashboardapp.models.Role;
import com.rmena.contactlistdashboardapp.models.User;
import com.rmena.contactlistdashboardapp.repository.ContactRepository;
import com.rmena.contactlistdashboardapp.repository.RoleRepository;
import com.rmena.contactlistdashboardapp.repository.UserRepository;
import com.rmena.contactlistdashboardapp.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class InitialDbPopulation {

    private final List<String> nameList = Stream.of(
    "Luci Ugalde",
    "Lana Stoughton",
    "Launa Middleton",
    "Oswaldo Smidt",
    "Ciara Barrus" ,
    "Zada Dills",
    "Jadwiga Every",
    "Jama Lautenschlage",
    "Alfredia Lamoureaux",
    "Scot Heskett",
    "Patrina Korn",
    "Dessie Kai",
    "Dena Grogg",
    "Madelene Geer",
    "Jetta Reilley",
    "Marsha Moschella",
    "Herma Davilla",
    "Caridad Ruffner",
    "Jolanda Maynard",
    "Stepanie Lindamood",
    "Willetta Bullock",
    "Wyatt Wooldridge",
    "Danette Lindley",
    "Denae Brandenberger",
    "Milda Hartung",
    "Elane Rife",
    "Loyce Westervelt",
    "Maryanna Altieri",
    "Kortney Andrus",
    "Brandon Squillante",
    "Karen Nissen",
    "Tenisha Kelch",
    "Elna Dries",
    "Mariella Burtner",
    "Irina Uhl",
    "Melony Jacox",
    "Ching Cuccia",
    "Waneta Cappiello",
    "Suzette Friley",
    "Cristal Glueck",
    "Ula Metro",
    "Margaretta Hoggan",
    "Hassan Brewer",
    "Trudy Serrata",
    "Tabetha Poynor",
    "Arica Garton",
    "Stacee Chesser",
    "Marcela Ruelas",
    "Estefana Batdorf",
    "Mickey Levar"
            )
            .collect(Collectors.toList());

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


        Random random = new Random();
        Contact contact;
        for (String name : nameList)
        {
            contact = new Contact();
            contact.setUser(user);
            contact.setContactName(name);
            contact.setAge(String.valueOf(random.nextInt(100)));
            contact.setNickname(name.substring(0,3));
            contact.setPhone("662" +
                            String.valueOf(random.nextInt(5)) +
                            String.valueOf(random.nextInt(999999 - 100000) + 100000)
                    );
            contactRepository.save(contact);
            }
    }
}
