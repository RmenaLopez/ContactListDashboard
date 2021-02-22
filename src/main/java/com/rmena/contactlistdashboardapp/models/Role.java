package com.rmena.contactlistdashboardapp.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToMany(
            cascade=CascadeType.MERGE,
            mappedBy = "roles")
    private Set<User> users;

    @NonNull
    private String userRole;


}
