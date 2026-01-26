package com.donarlink.service;

import com.donarlink.model.Notification;
import com.donarlink.model.User;
import com.donarlink.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(String message, User user) {
        Notification notification = new Notification(message, user);
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndIsReadFalseOrderByDateDesc(user);
    }

    public List<Notification> getAllNotifications(User user) {
        return notificationRepository.findByUserOrderByDateDesc(user);
    }

    public void markAsRead(Long id) {
        notificationRepository.findById(id).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}
