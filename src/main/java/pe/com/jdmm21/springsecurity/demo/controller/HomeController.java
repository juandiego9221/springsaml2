package pe.com.jdmm21.springsecurity.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            logger.info("hola");
            model.addAttribute("msg", "Welcome " + principal.getName() + " into Spring Boot in-memory managed user using BASIC authentication.");
        }
        logger.info("xxx");
        return "home";
    }
}
