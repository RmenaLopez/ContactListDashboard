package com.rmena.contactlistdashboardapp.repository;

import com.rmena.contactlistdashboardapp.models.Contact;
import com.rmena.contactlistdashboardapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByUser(User user);
}
