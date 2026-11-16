package TaskBuddy;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public AuthController(UserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}

    public static class RegistrationForm {
        @NotBlank @Email private String email;
        @NotBlank @Size(min=8,max=72) private String password;
        @NotBlank @Size(min=2,max=80) private String displayName;
        public String getEmail(){return email;} public void setEmail(String v){email=v;}
        public String getPassword(){return password;} public void setPassword(String v){password=v;}
        public String getDisplayName(){return displayName;} public void setDisplayName(String v){displayName=v;}
    }
    @GetMapping("/") public String home(){return "landing";}
    @GetMapping("/login") public String login(){return "login";}
    @GetMapping("/register") public String register(Model model){model.addAttribute("form",new RegistrationForm());return "register";}
    @PostMapping("/register") public String create(@ModelAttribute("form") RegistrationForm form, BindingResult result, Model model){
        if(result.hasErrors()) return "register";
        if(users.existsByEmailIgnoreCase(form.getEmail())){model.addAttribute("message","An account with this email already exists.");return "register";}
        users.save(new UserAccount(form.getEmail().trim().toLowerCase(),encoder.encode(form.getPassword()),form.getDisplayName().trim(),"LOCAL"));
        return "redirect:/login?registered";
    }
    @GetMapping("/guest") public String guest(){return "guest";}
}
