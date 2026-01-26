package com.donarlink.controller;

import com.donarlink.model.User;
import com.donarlink.repository.UserRepository;
import com.donarlink.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/notifications")
public class TestNotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/generate")
    public String generateNotification(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "User not logged in";
        }
        User user = userRepository.findByUsername(userDetails.getUsername());
        notificationService
                .createNotification("This is a test notification generated at " + java.time.LocalDateTime.now(), user);
        return "Notification created";
    }
}
