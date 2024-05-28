package com.scm.scm10.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.SocialLinks;
import com.scm.scm10.entities.User;
import com.scm.scm10.helper.ResourceNotFoundException;
import com.scm.scm10.repo.ContactRepo;

@Service
public class ContactService {

    @Autowired
    ContactRepo contactRepo;

    public List<Contact> getAllContact() {
        return contactRepo.findAll();
    }

    public Contact getContactById(int id) {
        return contactRepo.findById(id).orElse(null);
    }

    public Contact saveContact(Contact contact) {
        return contactRepo.save(contact);
    }

    public Optional<Contact> updateContact(Contact contact) {
        Contact contact2 = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not exist"));

        List<SocialLinks> links = new ArrayList<>();
        links.add(new SocialLinks(0, "linkedInURL", contact.getLinkedInLink(), contact2));
        links.add(new SocialLinks(0, "webSiteURL", contact.getWebsiteLink(), contact2));

        contact2.builder()
                .name(contact.getName())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .address(contact.getAddress())
                .description(contact.getDescription())
                .linkedInLink(contact.getLinkedInLink())
                .websiteLink(contact.getWebsiteLink())
                .picture(contact.getPicture())
                .favorite(contact.isFavorite())
                .links(links)
                .user(contact.getUser());

        contactRepo.save(contact2);
        return Optional.ofNullable(contact2);

    }

    public List<Contact> findByUser(User user) {
        return contactRepo.findByUser(user);
    }

    public boolean deleteById(int id) {
        Contact contact = contactRepo.findById(id).orElse(null);

        if (contact != null) {
            contactRepo.delete(contact);
            return true;
        }

        return false;
    }

}
