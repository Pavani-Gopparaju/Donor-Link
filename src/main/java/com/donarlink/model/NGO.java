package com.donarlink.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User admin;

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOperationalStatement() {
        return operationalStatement;
    }

    public void setOperationalStatement(String operationalStatement) {
        this.operationalStatement = operationalStatement;
    }

    public String getVisionStatement() {
        return visionStatement;
    }

    public void setVisionStatement(String visionStatement) {
        this.visionStatement = visionStatement;
    }

    public String getNgoAddress() {
        return ngoAddress;
    }

    public void setNgoAddress(String ngoAddress) {
        this.ngoAddress = ngoAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(LocalDate establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public NGO() {
    }

    public NGO(int id, String legalName, String registrationNumber, String operationalStatement, String visionStatement, String ngoAddress, String emailAddress, LocalDate establishmentDate) {
        this.id = id;
        this.legalName = legalName;
        this.registrationNumber = registrationNumber;
        this.operationalStatement = operationalStatement;
        this.visionStatement = visionStatement;
        this.ngoAddress = ngoAddress;
        this.emailAddress = emailAddress;
        this.establishmentDate = establishmentDate;
    }
}
