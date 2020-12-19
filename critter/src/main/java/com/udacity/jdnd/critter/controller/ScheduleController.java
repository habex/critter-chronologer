package com.udacity.jdnd.critter.controller;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.dto.ScheduleDTO;
import com.udacity.jdnd.critter.service.CustomerService;
import com.udacity.jdnd.critter.service.EmployeeService;
import com.udacity.jdnd.critter.service.PetService;
import com.udacity.jdnd.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO, new Schedule());

        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(schedule);

        //add employee to schedule
        if (scheduleDTO.getEmployeeIds() != null) {
            scheduleDTO.getEmployeeIds().forEach(empId -> {
                Employee employee = employeeService.findById(empId);
                //employee.setSchedules(schedules);
                employees.add(employee);
            });
            schedule.setEmployees(employees);
        }
        //add pets to schedule
        if (scheduleDTO.getPetIds() != null) {
            scheduleDTO.getPetIds().forEach(petId -> {
                Pet pet = petService.findById(petId);
                //pet.setSchedules(schedules);
                pets.add(pet);
            });
            schedule.setPets(pets);
        }
        scheduleService.save(schedule);
        return convertScheduleToScheduleDTO(schedule, new ScheduleDTO());
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        //convert to DTO
        scheduleService.findAll().forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDTO(schedule, new ScheduleDTO())));
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        //Find schedules by pet
        List<Schedule> schedules = scheduleService.findByPet(pet);
        //convert to DTO
        schedules.forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDTO(schedule, new ScheduleDTO())));
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        //Find schedules by employee
        List<Schedule> schedules = scheduleService.findByEmployee(employee);
        //convert to DTO
        schedules.forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDTO(schedule, new ScheduleDTO())));
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Pet> pets = customer.getPets();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        Set<Schedule> schedules = new HashSet<>();
        //Get schedules of all pets of the customer
        if (pets != null) {
            pets.forEach(pet -> schedules.addAll(scheduleService.findByPet(pet)));
        }
        //convert to DTO
        schedules.forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDTO(schedule, new ScheduleDTO())));
        return scheduleDTOS;
    }

    //<editor-fold desc="DTO converters">
    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO, Schedule schedule) {

        //Copy everything
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Pet> pets = new ArrayList<>();
        if (scheduleDTO.getPetIds() != null) {
            for (Long petId : scheduleDTO.getPetIds()) {
                if (petService.findById(petId) != null) {
                    pets.add(petService.findById(petId));
                }
            }
        }
        schedule.setPets(pets);

        List<Employee> employees = new ArrayList<>();
        if (scheduleDTO.getEmployeeIds() != null) {
            for (Long empId : scheduleDTO.getEmployeeIds()) {
                if (employeeService.findById(empId) != null) {
                    employees.add(employeeService.findById(empId));
                }
            }
        }
        schedule.setEmployees(employees);
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule, ScheduleDTO scheduleDTO) {

        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (schedule.getPets() != null) {
            List<Long> petIds = new ArrayList<>();
            for (Pet pet : schedule.getPets()) {
                if (pet != null) {
                    petIds.add(pet.getId());
                }
            }
            scheduleDTO.setPetIds(petIds);
        }
        if (schedule.getEmployees() != null) {
            List<Long> employeeIds = new ArrayList<>();
            for (Employee emp : schedule.getEmployees()) {
                if (emp != null) {
                    employeeIds.add(emp.getId());
                }
            }
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        return scheduleDTO;
    }
    //</editor-fold>
}
