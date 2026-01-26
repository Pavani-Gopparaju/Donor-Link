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
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("ngo", new NGO());
        return "Signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, @ModelAttribute("ngo") NGO ngo,
            RedirectAttributes redirectAttributes) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // 1. Save User first
            User savedUser = userRepository.save(user);

            // 2. If Role is NGO, save NGO details
            if ("ROLE_NGO".equals(user.getRole())) {
                ngo.setAdmin(savedUser);
                ngoRepository.save(ngo);
            }

            // Add a success message for the login page
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
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

        // 3. Get data based on role
        if ("ROLE_NGO".equals(user.getRole())) {
            NGO ngo = ngoRepository.findByAdmin_Id(user.getId())
                    .orElseThrow(() -> new RuntimeException("NGO profile not found for this user"));

            // Get donations received by this NGO
            List<Donation> ngoDonations = donationRepository.getDonationsByNgo_Id(ngo.getId());

            // Get tasks
            java.util.List<com.donarlink.model.Task> activeTasks = new java.util.ArrayList<>();
            java.util.List<com.donarlink.model.Task> fullyFundedTasks = new java.util.ArrayList<>();

            if (ngo.getTasks() != null) {
                for (com.donarlink.model.Task task : ngo.getTasks()) {
                    if (task.getAmountRaised() >= task.getEstimated_cost()
                            || task.getStatus() == com.donarlink.model.Task.TaskStatus.COMPLETED) {
                        fullyFundedTasks.add(task);
                    } else {
                        activeTasks.add(task);
                    }
                }
            }

            model.addAttribute("ngo", ngo);
            model.addAttribute("ngoDonations", ngoDonations);
            model.addAttribute("activeTasks", activeTasks);
            model.addAttribute("fullyFundedTasks", fullyFundedTasks);
            model.addAttribute("isNGO", true);

        } else {
            // Default to Donor view
            List<Donation> donations = donationRepository.getDonationsByDonor_Id(user.getId());
            List<NGO> ngos = (List<NGO>) ngoRepository.findAll();

            model.addAttribute("donations", donations);
            model.addAttribute("ngos", ngos);
            model.addAttribute("isNGO", false);
        }

        return "dashboard";
    }

}