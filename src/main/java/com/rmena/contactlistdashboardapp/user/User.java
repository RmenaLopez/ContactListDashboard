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

    private @Id Long id;

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
