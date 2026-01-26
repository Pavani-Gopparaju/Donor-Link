package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
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
