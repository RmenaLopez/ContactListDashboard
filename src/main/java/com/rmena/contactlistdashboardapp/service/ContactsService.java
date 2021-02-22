package com.rmena.contactlistdashboardapp.service;

import com.rmena.contactlistdashboardapp.models.Contact;
import com.rmena.contactlistdashboardapp.models.User;
import com.rmena.contactlistdashboardapp.repository.ContactRepository;
import com.rmena.contactlistdashboardapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Contact> getAllContactsForUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        return contactRepository.findAllByUser(username);
    }

    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    public void addContact(Contact contact) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        contact.setUser(user);
        contactRepository.save(contact);
    }

    //TODO: create batch delete
    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }

    public void updateContact(Contact contact) {
        contactRepository.saveAndFlush(contact);
    }

}
