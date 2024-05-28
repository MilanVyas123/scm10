package com.scm.scm10.Controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm10.entities.User;
import com.scm.scm10.forms.UserLoginForm;
import com.scm.scm10.helper.MessageColors;
import com.scm.scm10.helper.MessageHelper;
import com.scm.scm10.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @Autowired
    UserService service;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping({ "/home", "/" })
    String getHome() {
        return "home";
    }

    @GetMapping("/about")
    String getAbout() {
        return "about";
    }

    @GetMapping("/service")
    String getService() {
        return "services";
    }

    @GetMapping("/contact")
    String getContact() {
        return "contact";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(Model model) {

        // User user=new User();
        // user.setName("Milan vyas");
        // user.setEmail("vyasmilan58@gmail.com");
        // user.setPassword("Milan@123");
        // user.setAbout("This is about of Milan vyas");
        // user.setPhoneNumber("6352964372");
        // model.addAttribute("user", user);

        UserLoginForm form = new UserLoginForm();
        model.addAttribute("user", form);

        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute("user") UserLoginForm user, BindingResult bindingResult,
            HttpSession httpSession) {
        System.out.println(user);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        boolean res = service.isUserExistbyEmail(user.getEmail());
        if (res == true) {

            MessageHelper messageHelper = new MessageHelper("Email already exists", MessageColors.red);
            httpSession.setAttribute("message", messageHelper);
            return "register";
        }
        User user2 = new User();
        user2.setName(user.getName());
        user2.setRoleList(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        user2.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user2.setEmail(user.getEmail());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setAbout(user.getAbout());

        MessageHelper messageHelper = new MessageHelper("Registration successfull", MessageColors.blue);

        httpSession.setAttribute("message", messageHelper);

        service.saveUser(user2);
        return "redirect:/register";
    }

    // @RequestMapping("/home")
    // String home(Model model,@ModelAttribute("message") String msg)
    // {
    // model.addAttribute("name", "substtring tech");
    // System.out.println("Hello world");
    // List<User> allUsers = service.getAllUsers();
    // model.addAttribute("users", allUsers);
    // model.addAttribute("message", msg);
    // System.out.println(msg);
    // return "home";
    // }

    // @GetMapping("/home1")
    // String home(Model model, @RequestParam(defaultValue = "0") int pageNumber,
    // @RequestParam(defaultValue = "5") int pageSize)
    // {

    // List<User> allUsers=service.findAll(pageNumber,pageSize);
    // model.addAttribute("pageNumber",pageNumber);
    // model.addAttribute("pageSize",pageSize);
    // model.addAttribute("users", allUsers);
    // return "home";
    // }

    // @RequestMapping("/services")
    // String services(Model model)
    // {
    // User user=new User(1, "milan", "vyas", "google", "123-45-678",
    // "http://localhost:9090/services", "10000", "dead@gmail.com", "122345",null);
    // model.addAttribute("user", user);
    // return "services";
    // }
    // @PostMapping("/subform")
    // String subForm(@RequestParam("file") MultipartFile
    // file,@ModelAttribute("user") User user, BindingResult
    // bindingResult,RedirectAttributes attributes)throws IOException {
    // // Exclude the id field from binding
    // bindingResult.getSuppressedFields(); // exclude id field from binding

    // byte[] data=file.getBytes();
    // String fileName=System.currentTimeMillis()+file.getOriginalFilename();
    // java.nio.file.Path
    // path=Paths.get("src/main/resources/static/Uploads/"+fileName);
    // Files.write(path, data);

    // user.setFilename(fileName);
    // // Now you can use the user object without the id field
    // System.out.println(user);

    // if(service.saveUser(user))
    // {
    // attributes.addFlashAttribute("message", "User save success");
    // }
    // else
    // {
    // attributes.addFlashAttribute("message","User save failure");
    // }

    // return "redirect:/home";
    // }

}
