package com.donarlink.controller;

import com.donarlink.model.Donation;
import com.donarlink.model.NGO;
import com.donarlink.model.User;
import com.donarlink.repository.DonationRepository;
import com.donarlink.repository.NGORepository;
import com.donarlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private NGORepository ngoRepository;



    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());

        return "Signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) { // Add RedirectAttributes
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);

            // Add a success message for the login page
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login"; // Use redirect:
        } catch (Exception e) {
            // Add an error message
            redirectAttributes.addFlashAttribute("error", "Email or phone already in use.");
            return "redirect:/signup";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) { // Use Principal to get the logged-in user

        // 1. Get the authenticated user
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 2. Add user's name to the model
        model.addAttribute("username", user.getUsername());

        // 3. Get data from repositories
        List<Donation> donations = donationRepository.getDonationsByDonor_Id(user.getId());
        List<NGO> ngos = (List<NGO>) ngoRepository.findAll(); // Get real NGOs from DB

        // 4. Add data to the model
        model.addAttribute("donations", donations);
        model.addAttribute("ngos", ngos);

        return "dashboard";
    }


}