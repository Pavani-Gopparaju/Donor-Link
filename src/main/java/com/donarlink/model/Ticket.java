package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Ticket_id;

    @Column
    private String Ticket_Title;

    @Column
    private String Ticket_Description;

    @Column
    private Date Ticket_Date;

    @Column
    private String Ticket_Status;

    @OneToOne
    private User Ticket_User;

    @OneToOne
    private NGO ngo;

    public int getTicket_id() {
        return Ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        Ticket_id = ticket_id;
    }

    public String getTicket_Title() {
        return Ticket_Title;
    }

    public void setTicket_Title(String ticket_Title) {
        Ticket_Title = ticket_Title;
    }

    public String getTicket_Description() {
        return Ticket_Description;
    }

    public void setTicket_Description(String ticket_Description) {
        Ticket_Description = ticket_Description;
    }

    public Date getTicket_Date() {
        return Ticket_Date;
    }

    public void setTicket_Date(Date ticket_Date) {
        Ticket_Date = ticket_Date;
    }

    public String getTicket_Status() {
        return Ticket_Status;
    }

    public void setTicket_Status(String ticket_Status) {
        Ticket_Status = ticket_Status;
    }

    public User getTicket_User() {
        return Ticket_User;
    }

    public void setTicket_User(User ticket_User) {
        Ticket_User = ticket_User;
    }

    public NGO getNgo() {
        return ngo;
    }

    public void setNgo(NGO ngo) {
        this.ngo = ngo;
    }

    public Ticket() {

    }

    public Ticket(int ticket_id, String ticket_Title, String ticket_Description, Date ticket_Date, String ticket_Status, User user, NGO ngo) {
        this.Ticket_id = ticket_id;
        this.Ticket_Title = ticket_Title;
        this.Ticket_Description = ticket_Description;
        this.Ticket_Date = ticket_Date;
        this.Ticket_Status = ticket_Status;
        this.Ticket_User = user;
        this.ngo = ngo;

    }
}
