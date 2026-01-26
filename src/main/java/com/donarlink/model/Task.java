package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000) // Allow longer descriptions
    private String description;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column
    private double estimated_cost;

    @Column
    private double amountRaised = 0.0;

    // Relationship: Many Tasks belong to One NGO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ngo_id", nullable = false)
    private NGO ngo;

    // Default Constructor
    public Task() {
        this.status = TaskStatus.TODO; // Default status
        this.priority = TaskPriority.MEDIUM; // Default priority
    }

    // Parameterized Constructor
    public Task(String title, String description, LocalDate deadline, TaskPriority priority, NGO ngo) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = TaskStatus.TODO;
        this.ngo = ngo;
    }

    // --- Enums ---
    public enum TaskPriority {
        HIGH, MEDIUM, LOW
    }

    public enum TaskStatus {
        TODO, IN_PROGRESS, COMPLETED
    }
}