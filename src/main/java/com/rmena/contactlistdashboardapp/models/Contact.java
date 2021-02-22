package com.rmena.contactlistdashboardapp.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contact_id")
    private Long id;

    @ManyToOne(
            cascade=CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;

    @NonNull
    private String contactName;

    @NonNull
    private String phone;

    private String age;
    private String nickname;

    public Contact(@NonNull String contactName, @NonNull String phone, String age, String nickname) {
        this.contactName = contactName;
        this.phone = phone;
        this.age = age;
        this.nickname = nickname;
    }
}
