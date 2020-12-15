package com.udacity.jdnd.critter.user;

import com.udacity.jdnd.critter.schedule.Schedule;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private long id;

    @Nationalized
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "EmployeeSkill", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection(fetch =FetchType.EAGER,  targetClass = DayOfWeek.class)
    @CollectionTable(name = "Days_Available", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedules_id")
    private Set<Schedule> schedules;

    public Employee() {
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

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skills=" + skills +
                ", daysAvailable=" + daysAvailable +
                '}';
    }
}
