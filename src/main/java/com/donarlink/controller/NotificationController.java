package com.donarlink.controller;

import com.donarlink.model.Notification;
import com.donarlink.model.User;
import com.donarlink.repository.UserRepository;
import com.donarlink.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return List.of();
        }
        User user = userRepository.findByUsername(userDetails.getUsername());
        return notificationService.getUnreadNotifications(user);
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
