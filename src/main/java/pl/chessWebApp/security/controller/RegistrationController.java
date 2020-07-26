package pl.chessWebApp.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.chessWebApp.security.entity.User;
import pl.chessWebApp.security.service.UserService;
import pl.chessWebApp.security.user.CrmUser;

import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {

        theModel.addAttribute("crmUser", new CrmUser());

        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("crmUser") CrmUser theCrmUser,
            BindingResult theBindingResult,
            Model theModel) {

        String userName = theCrmUser.getUserName();
        String userEmail = theCrmUser.getEmail();

        LOGGER.info("Processing registration form for: " + userName + " and " + userEmail);

        // form validation
        if (theBindingResult.hasErrors()) {
            return "registration-form";
        }

        // check the database if user or email already exists
        User isExisting = userService.findByUserName(userName);
        User isExisting2 = userService.findByUserEmail(userEmail);

        if (isExisting != null || isExisting2 != null) {
            theModel.addAttribute("crmUser", new CrmUser());
            theModel.addAttribute("registrationError", "User name or email already exists.");

            LOGGER.warn("User name or email already exists.");
            return "registration-form";
        } else {// create user account
            userService.save(theCrmUser);

            LOGGER.info("Successfully created user: " + userName);
        }
        return "registration-confirmation";
    }
}
