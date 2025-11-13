package com.donarlink.model;

import com.donarlink.model.enums.Expense_types;
import jakarta.persistence.*;

import java.util.Date;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Expense_types getType() {
        return type;
    }

    public void setType(Expense_types type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
