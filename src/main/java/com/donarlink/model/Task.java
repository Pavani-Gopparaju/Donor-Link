package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private double estimated_cost;

    @Column
    private double current_donation;


    public Task(String title, String description, double estimated_cost, double current_donation) {
        this.estimated_cost = 0.0;
        this.current_donation = 0.0;
        this.title = "";
        this.description = "";
    }

    public Task() {

    }
}
