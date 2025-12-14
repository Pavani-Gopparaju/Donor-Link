package com.donarlink.controller;

import com.donarlink.model.NGO;
import com.donarlink.model.User;
import com.donarlink.repository.NGORepository;
import com.donarlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class NGO_Controller {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NGORepository ngoRepository;

    @RequestMapping("/CreateNGO")
    public String CreateNGO(Model model, Principal principal) {
        model.addAttribute("ngo", new NGO());
        return "NGO_Registration";
    }

    @PostMapping("/register_ngo")
    public String RegisterNGO(@ModelAttribute("user") User user, @ModelAttribute("ngo") NGO ngo, Model model, Principal principal) {
        String email = principal.getName();

        // 2. Find the full User object from the database
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. Associate the User with the NGO
        ngo.setAdmin(currentUser);

        ngoRepository.save(ngo);
        return "NGO_Registration";
    }

}
