package com.donarlink.repository;

import com.donarlink.model.Notification;
import com.donarlink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsReadFalseOrderByDateDesc(User user);

    List<Notification> findByUserOrderByDateDesc(User user);
}
