package com.rmena.contactlistdashboardapp.models;

import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(
            cascade=CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.MERGE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Contact> contacts = new HashSet<>();

    @NonNull
    private String username;
    @NonNull
    private String password;

    public Set<RoleObject> getRoles() {
        Set<RoleObject> userRoles = new HashSet<>();
        for (Role role : roles){
            userRoles.add(new RoleObject(role.getId(), role.getUserRole()));
        }
        return userRoles;
    }
}
