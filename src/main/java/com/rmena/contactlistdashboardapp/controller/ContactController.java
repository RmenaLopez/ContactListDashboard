package com.rmena.contactlistdashboardapp.controller;

import com.rmena.contactlistdashboardapp.models.Contact;
import com.rmena.contactlistdashboardapp.models.User;
import com.rmena.contactlistdashboardapp.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api")
public class ContactController {

    @Autowired
    private ContactsService contactsService;

    @GetMapping(path="/contacts")
    public Iterable<Contact> getAllContactsForUser() {
        return contactsService.getAllContactsForUser();
    }
    @GetMapping(path="/contacts/all")
    public Iterable<Contact> getAllContacts() {
        return contactsService.getAllContacts();
    }

    @PostMapping(path="/contacts")
    public void addContactToUser(@RequestBody Contact contact) {
        contactsService.addContact(contact);
    }

}
