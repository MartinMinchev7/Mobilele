package bg.sofuni.mobilele.web;

import bg.sofuni.mobilele.model.dto.UserRegistrationDTO;
import bg.sofuni.mobilele.service.UserService;
import bg.sofuni.mobilele.web.aop.WarnIfExecutionExceed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerDTO")
    public UserRegistrationDTO registerDTO() {
       return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "auth-register";
    }

    @WarnIfExecutionExceed(
            threshold = 1000
    )
    @PostMapping("/register")
    public String register(UserRegistrationDTO registerDTO) {

        userService.registerUser(registerDTO);

        return "redirect:/";
    }
}
