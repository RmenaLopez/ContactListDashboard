package com.rmena.contactlistdashboardapp.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor
public class User implements Serializable {

    private @Id Long id; // will be set when persisting

    private String firstName;
    private String lastName;
    private int age;

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
