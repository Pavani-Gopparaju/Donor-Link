package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donation_id;

    @Column
    private double amount;

    @Column
    private Date date;

    @OneToOne
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "donor_user_id")
    private User donor;

    @ManyToOne
    @JoinColumn(name = "ngo_ref_id")
    private NGO ngo;

    @ManyToOne
    private Task task;

    public Donation() {

    }

    public Donation(int donation_id, double amount, Date date, Ticket ticket, User donor, NGO ngo, Task task) {
        this.donation_id = donation_id;
        this.amount = amount;
        this.date = date;
        this.ticket = ticket;
        this.donor = donor;
        this.ngo = ngo;
        this.task = task;
    }
}
