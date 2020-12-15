package com.udacity.jdnd.critter.pet;

import com.udacity.jdnd.critter.schedule.Schedule;
import com.udacity.jdnd.critter.user.Customer;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @Nationalized
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",nullable = false)
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedules_id")
    private Set<Schedule> schedules;

    private LocalDate birthDate;

    private String notes;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
