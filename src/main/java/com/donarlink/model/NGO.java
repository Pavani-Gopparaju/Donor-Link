package com.donarlink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
public class NGO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String legalName;

    @Column
    private String registrationNumber;

    @Column
    @Lob
    private String operationalStatement;

    @Column
    private String visionStatement;

    @Column
    @Lob
    private String ngoAddress;

    @Column
    private String emailAddress;

    @Column
    private LocalDate establishmentDate;

    @OneToMany(mappedBy = "ngo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private User admin;

    public NGO() {
    }

    public NGO( String legalName, String registrationNumber, String operationalStatement, String visionStatement, String ngoAddress, String emailAddress,LocalDate establishmentDate) {
        this.legalName = legalName;
        this.registrationNumber = registrationNumber;
        this.operationalStatement = operationalStatement;
        this.visionStatement = visionStatement;
        this.ngoAddress = ngoAddress;
        this.emailAddress = emailAddress;
        this.establishmentDate = establishmentDate;
    }
}
