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
import java.util.List;

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
        List<Employee> employees = employeeService.findAllById(scheduleDTO.getEmployeeIds());
        List<Pet> pets = petService.findAllById(scheduleDTO.getPetIds());
        //add employee to schedule
        if (employees != null) {
            schedule.setEmployees(employees);
        }
        //add pets to schedule
        if (pets != null) {
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
        if (pet == null) {
            throw new UnsupportedOperationException("Pet not found");
        }
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
        if (employee == null) {
            throw new UnsupportedOperationException("Employee not found");
        }
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
        if (customer == null) {
            throw new UnsupportedOperationException("Customer for not found");
        }
        List<Pet> pets = petService.findAllByCustomer(customer);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>();
        //Get schedules of all pets of the customer
        if (pets == null) {
            throw new UnsupportedOperationException("No pets found");
        }
        pets.forEach(pet -> schedules.addAll(scheduleService.findByPet(pet)));
        //convert to DTO
        schedules.forEach(schedule -> scheduleDTOS.add(convertScheduleToScheduleDTO(schedule, new ScheduleDTO())));
        return scheduleDTOS;
    }

    //<editor-fold desc="DTO converters">
    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO, Schedule schedule) {
        //Copy everything
        BeanUtils.copyProperties(scheduleDTO, schedule);
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
