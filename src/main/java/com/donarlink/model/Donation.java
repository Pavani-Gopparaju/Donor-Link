package com.donarlink.model;

import jakarta.persistence.*;

import java.util.Date;

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
    private User donor;

    @OneToOne
    private NGO ngo;

    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getDonor() {
        return donor;
    }

    public void setDonor(User donor) {
        this.donor = donor;
    }

    public NGO getNgo() {
        return ngo;
    }

    public void setNgo(NGO ngo) {
        this.ngo = ngo;
    }

    public Donation() {

    }

    public Donation(int donation_id, double amount, Date date, Ticket ticket, User donor, NGO ngo) {
        this.donation_id = donation_id;
        this.amount = amount;
        this.date = date;
        this.ticket = ticket;
        this.donor = donor;
        this.ngo = ngo;
    }
}
