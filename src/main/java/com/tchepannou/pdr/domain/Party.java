package com.tchepannou.pdr.domain;

import java.util.Date;

public class Party extends Persistent {
    //-- Attribute
    private PartyKind kind;
    private String name;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private String heigth;
    private String weight;

    //-- Getter/Setter
    public PartyKind getKind() {
        return kind;
    }

    public void setKind(PartyKind kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getHeigth() {
        return heigth;
    }

    public void setHeigth(String heigth) {
        this.heigth = heigth;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
