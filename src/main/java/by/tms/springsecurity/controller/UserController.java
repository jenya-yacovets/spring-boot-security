package by.tms.springsecurity.controller;

import by.tms.springsecurity.model.UserAuthenticationModel;
import by.tms.springsecurity.model.UserRegistrationModel;
import by.tms.springsecurity.service.UserService;
import by.tms.springsecurity.service.exception.UsernameIsBusyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registrationView() {
        ModelAndView model = new ModelAndView("registration");
        model.addObject("user", new UserRegistrationModel());
        return model;
    }

    @PostMapping("/registration")
    public ModelAndView registrationHandler(@Valid @ModelAttribute("user") UserRegistrationModel user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ModelAndView("registration", bindingResult.getModel());
        }

        try {
            userService.create(user);
            return new ModelAndView("redirect:/authentication", "message", "Success registration account");
        } catch (UsernameIsBusyException e) {
            ModelAndView model = new ModelAndView("registration");
            model.addObject("user", user);
            model.addObject("message", "Username is busy");
            return model;
        }
    }

    @GetMapping("/authentication")
    public ModelAndView authenticationView() {
        ModelAndView model = new ModelAndView("authentication");
        model.addObject("user", new UserAuthenticationModel());
        return model;
    }

    @GetMapping("/")
    public ModelAndView homeView() {
        return new ModelAndView("home");
    }

    @GetMapping("/profile")
    public ModelAndView profileView() {
        return new ModelAndView("profile");
    }
}
