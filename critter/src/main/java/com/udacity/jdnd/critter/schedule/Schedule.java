package com.udacity.jdnd.critter.schedule;

import com.udacity.jdnd.critter.pet.Pet;
import com.udacity.jdnd.critter.user.Employee;
import com.udacity.jdnd.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private List<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private List<Pet> pets;

    private LocalDate date;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "EmployeeSkill", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    public Schedule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", employees=" + employees +
                ", pets=" + pets +
                ", date=" + date +
                ", activities=" + activities +
                '}';
    }
}
