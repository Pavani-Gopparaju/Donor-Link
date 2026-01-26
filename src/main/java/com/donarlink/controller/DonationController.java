package com.donarlink.controller;

import com.donarlink.model.Donation;
import com.donarlink.model.NGO;
import com.donarlink.model.Task;
import com.donarlink.model.User;
import com.donarlink.repository.DonationRepository;
import com.donarlink.repository.TaskRepository;
import com.donarlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/donate")
public class DonationController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

    @GetMapping("/task/{id}")
    public String showDonationPage(@PathVariable("id") int taskId, Model model, Principal principal) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getStatus() == Task.TaskStatus.COMPLETED) {
            return "redirect:/dashboard?error=Task already completed";
        }

        double remainingAmount = task.getEstimated_cost() - task.getAmountRaised();

        // Ensure remaining amount is not negative (floating point safety)
        if (remainingAmount < 0)
            remainingAmount = 0;

        model.addAttribute("task", task);
        model.addAttribute("remainingAmount", remainingAmount);
        model.addAttribute("userName", principal.getName());

        return "makeDonation";
    }

    @PostMapping("/process")
    public String processDonation(@RequestParam("taskId") int taskId,
            @RequestParam("amount") double amount,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod,
            @RequestParam(value = "cardNumber", required = false) String cardNumber,
            @RequestParam(value = "cardHolder", required = false) String cardHolder,
            @RequestParam(value = "expiry", required = false) String expiry,
            @RequestParam(value = "cvv", required = false) String cvv,
            @RequestParam(value = "upiId", required = false) String upiId,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));

            User donor = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            double remainingAmount = task.getEstimated_cost() - task.getAmountRaised();

            // Validation
            if (amount <= 0) {
                redirectAttributes.addFlashAttribute("error", "Amount must be greater than 0");
                return "redirect:/donate/task/" + taskId;
            }
            if (amount > remainingAmount) {
                redirectAttributes.addFlashAttribute("error",
                        "Amount exceeds remaining goal (" + remainingAmount + ")");
                return "redirect:/donate/task/" + taskId;
            }

            // Update Task
            task.setAmountRaised(task.getAmountRaised() + amount);

            // Allow small floating point margin if needed, but strictly >= cost is complete
            if (task.getAmountRaised() >= task.getEstimated_cost()) {
                task.setStatus(Task.TaskStatus.COMPLETED);
            }
            taskRepository.save(task);

            // Create Donation Record
            Donation donation = new Donation();
            donation.setAmount(amount);
            donation.setDate(new Date());
            donation.setDonor(donor);
            donation.setNgo(task.getNgo());
            donation.setTask(task);

            donationRepository.save(donation);

            redirectAttributes.addFlashAttribute("success", "Donation of ₹" + amount + " successful!");
            return "redirect:/dashboard";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Transaction failed: " + e.getMessage());
            return "redirect:/donate/task/" + taskId;
        }
    }
}
