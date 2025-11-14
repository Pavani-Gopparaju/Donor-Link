package com.donarlink.controller;

import com.donarlink.model.Donation;
import com.donarlink.model.NGO;
import com.donarlink.model.User;
import com.donarlink.repository.DonationRepository;
import com.donarlink.repository.NGORepository;
import com.donarlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;



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
    public String signup(@ModelAttribute("user") User user) {



        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@ModelAttribute("user") User user, Model model) {
        List<Donation> donations = donationRepository.getDonationsByDonor_Id(user.getId());
        model.addAttribute("donation", donations);
        LocalDate today = LocalDate.now();
        NGO ngo = new NGO("child vikas", "213123123", "working for kids", "feed children", "India", "childvikas@email.com", today );
        List<NGO> ngos = new ArrayList<>();
        ngos.add(ngo);
        model.addAttribute("ngo", ngos);
        return "dashboard";
    }


}