package com.donarlink.controller;

import com.donarlink.model.NGO;
import com.donarlink.model.Task;
import com.donarlink.model.User;
import com.donarlink.repository.NGORepository;
import com.donarlink.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String RegisterNGO(@ModelAttribute("user") User user, @ModelAttribute("ngo") NGO ngo, Model model,
            Principal principal) {
        String email = principal.getName();

        // 2. Find the full User object from the database
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. Associate the User with the NGO
        ngo.setAdmin(currentUser);

        ngoRepository.save(ngo);
        return "NGO_Registration";
    }

    @Autowired
    private com.donarlink.repository.TaskRepository taskRepository;

    @GetMapping("/createTask")
    public String CreateTask(Model model, Principal principal) {
        model.addAttribute("task", new Task());
        return "createTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute("task") Task task, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        NGO ngo = ngoRepository.findByAdmin_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("NGO not found for this user"));

        task.setNgo(ngo);
        taskRepository.save(task);

        return "redirect:/dashboard";
    }

    @GetMapping("/ngoDetails")
    public String ngoDetails(@org.springframework.web.bind.annotation.RequestParam("id") int id, Model model,
            Principal principal) {
        NGO ngo = ngoRepository.findById(id).orElseThrow(() -> new RuntimeException("NGO not found"));
        model.addAttribute("ngo", ngo);

        java.util.List<Task> activeTasks = new java.util.ArrayList<>();
        java.util.List<Task> fullyFundedTasks = new java.util.ArrayList<>();

        if (ngo.getTasks() != null) {
            for (Task task : ngo.getTasks()) {
                if (task.getAmountRaised() >= task.getEstimated_cost()
                        || task.getStatus() == Task.TaskStatus.COMPLETED) {
                    fullyFundedTasks.add(task);
                } else {
                    activeTasks.add(task);
                }
            }
        }

        model.addAttribute("activeTasks", activeTasks);
        model.addAttribute("fullyFundedTasks", fullyFundedTasks);
        return "ngoDetails";
    }

}
