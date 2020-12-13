package com.udacity.jdnd.critter.user;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    @Nationalized
    private String name;

    // matches either empty string or 10 digits number
    @Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;

    @Column
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Long> petIds;

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", petIds=" + petIds +
                '}';
    }
}
