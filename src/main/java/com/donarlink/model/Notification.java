package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Notification() {
    }

    public Notification(String message, User user) {
        this.message = message;
        this.user = user;
        this.date = LocalDateTime.now();
        this.isRead = false;
    }
}
