package com.donarlink.model;

import com.donarlink.model.enums.Expense_types;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private int amount;

    @Column
    private Expense_types type;

    @Column
    private Date date;

    public Expense() {

    }

    public Expense(String title, String description, int amount, Expense_types type, Date date) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.date = date;

    }
}
