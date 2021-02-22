package com.rmena.contactlistdashboardapp.repository;

import com.rmena.contactlistdashboardapp.models.Contact;
import com.rmena.contactlistdashboardapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query(
            value = "SELECT * \n" +
                    "FROM contact \n" +
                    "INNER JOIN users ON contact.fk_user = users.id\n" +
                    "where username = ?1",
            nativeQuery = true
    )
    List<Contact> findAllByUser(String user);
}
