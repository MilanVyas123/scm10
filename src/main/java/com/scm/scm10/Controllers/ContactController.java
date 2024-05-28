package com.scm.scm10.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.SocialLinks;
import com.scm.scm10.entities.User;
import com.scm.scm10.forms.AddContactForm;
import com.scm.scm10.helper.Helper;
import com.scm.scm10.helper.MessageColors;
import com.scm.scm10.helper.MessageHelper;
import com.scm.scm10.helper.ResourceNotFoundException;
import com.scm.scm10.services.CloudinaryImageService;
import com.scm.scm10.services.ContactService;
import com.scm.scm10.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @Autowired
    CloudinaryImageService cloudinaryImageService;

    @GetMapping("/add")
    public String addContact(Model model) {
        AddContactForm addContactForm = new AddContactForm();
        model.addAttribute("contact", addContactForm);
        return "user/add_contact";
    }

    @PostMapping("/add_contact")
    public String saveContact(@Valid @ModelAttribute("contact") AddContactForm contact, BindingResult bindingResult,
            MultipartFile picture, Authentication authentication, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            httpSession.setAttribute("message", MessageHelper.builder()
                    .colors(MessageColors.red)
                    .message("Please correct the following error")
                    .build());
            return "user/add_contact";
        }
        String uniqueFilename = null;
        String fileurl=null;
        if (!picture.isEmpty()) {
            uniqueFilename = System.currentTimeMillis() + picture.getOriginalFilename();
            System.out.println("upload file" + picture.isEmpty());

            fileurl=cloudinaryImageService.uploadImage(picture, uniqueFilename);
            System.out.println("Public ID : "+fileurl);
            //for upload file in local folder eg: Uploads/

            // try {
            //     byte[] data = picture.getBytes();
            //     Path path2 = Paths.get("src/main/resources/static/Uploads/", uniqueFilename);
            //     Files.write(path2, data);
            // } catch (IOException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }

            // contact2.setUser();


            System.out.println("ContactForm : " + contact);

        }

        Contact contact2 = new Contact();
        contact2.setAddress(contact.getAddress());
        contact2.setDescription(contact.getDescription());
        contact2.setEmail(contact.getEmail());
        contact2.setFavorite(contact.isFavorite());
        contact2.setLinkedInLink(contact.getLinkedInLink());
        contact2.setWebsiteLink(contact.getWebsiteLink());
        contact2.setName(contact.getName());
        contact2.setPhoneNumber(contact.getPhoneNumber());
        contact2.setPicture(fileurl);
        contact2.setPublicIdOfPicture(uniqueFilename);

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.findUserbyEmail(username).orElse(null);

        List<SocialLinks> links = new ArrayList<>();
        links.add(new SocialLinks(0, contact.getLinkedInLink(), "linkedInURL", contact2));
        links.add(new SocialLinks(0, contact.getWebsiteLink(), "webSiteURL", contact2));

        contact2.setLinks(links);
        contact2.setUser(user);
        contact2.setId(0);
        System.out.println("file" + uniqueFilename);
        contactService.saveContact(contact2);

        System.out.println("Contact : " + contact2);

        httpSession.setAttribute("message", MessageHelper.builder()
                .colors(MessageColors.green)
                .message("Contact added successfully")
                .build());
        return "redirect:/user/contacts/add";
    }

    @GetMapping("/view")
    public String getContacts(Model model, Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.findUserbyEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(username + "not exist"));
        List<Contact> contacts = contactService.findByUser(user);
        model.addAttribute("contacts", contacts);
        return "user/user_contacts";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") int id, HttpSession httpSession) {
        boolean flag = contactService.deleteById(id);
        if (flag == true) {
            httpSession.setAttribute("message", MessageHelper.builder()
                    .colors(MessageColors.green)
                    .message("Contact deleted successfully")
                    .build());
        } else {
            httpSession.setAttribute("message", MessageHelper.builder()
                    .colors(MessageColors.red)
                    .message("Something went wrong !!")
                    .build());
        }
        return "redirect:/user/contacts/view";
    }

    @GetMapping("/update/fav/{id}/{fav}")
    public String changeFavstatus(@PathVariable("id") int id, @PathVariable("fav") boolean flag) {
        System.out.println("id : " + id + " fav : " + flag);
        Contact contact=contactService.getContactById(id);
        contact.setFavorite(!flag);
        if(contact!=null)
        {
            contactService.updateContact(contact);
        }
        return "user/user_contacts";
    }
}
