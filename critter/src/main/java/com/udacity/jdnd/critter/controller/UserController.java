package com.udacity.jdnd.critter.controller;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.dto.CustomerDTO;
import com.udacity.jdnd.critter.dto.EmployeeDTO;
import com.udacity.jdnd.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.critter.enums.EmployeeSkill;
import com.udacity.jdnd.critter.service.CustomerService;
import com.udacity.jdnd.critter.service.EmployeeService;
import com.udacity.jdnd.critter.service.PetService;
import com.udacity.jdnd.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PetService petService;
    @Autowired
    ScheduleService scheduleService;

    //<editor-fold desc="Customer">
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = convertCustomerDTOToCustomer(customerDTO, new Customer());
        customerService.save(customer);
        return convertCustomerToCustomerDTO(customer, new CustomerDTO());
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerService.findAll().forEach(customer -> customerDTOS.add(convertCustomerToCustomerDTO(customer, new CustomerDTO())));
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);
        Customer owner = customerService.findById(pet.getCustomer().getId());
        CustomerDTO ownerDto = convertCustomerToCustomerDTO(owner, new CustomerDTO());
        ownerDto.getPetIds().add(pet.getId());
        return ownerDto;
    }
    //</editor-fold>

    //<editor-fold desc="Employee">
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO, new Employee());
        employeeService.save(employee);
        return convertEmployeeToEmployeeDTO(employee, new EmployeeDTO());
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return convertEmployeeToEmployeeDTO(employee, new EmployeeDTO());
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.save(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        //Find employees by date
        List<Employee> employees = scheduleService.findEmployeesByDate(employeeDTO.getDate());
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        //Filter available employees by skills
        employees.forEach(employee -> {
            if (employee.getSkills().containsAll(employeeDTO.getSkills())){
                employeeDTOS.add(convertEmployeeToEmployeeDTO(employee,new EmployeeDTO()));
            }
        });
        return employeeDTOS;
    }

    //</editor-fold>

    //<editor-fold desc="DTO converters">
    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO, Customer customer) {
        if (customer != null) {
            //Copy everything except id
            BeanUtils.copyProperties(customerDTO, customer, "id");
        } else {
            //Copy everything
            BeanUtils.copyProperties(customerDTO, customer);
        }

        if (customerDTO.getPetIds() != null) {
            List<Pet> pets = new ArrayList<>();
            for (Long petId : customerDTO.getPetIds()) {
                pets.add(petService.findById(petId));
            }
            customer.setPets(pets);
        }
        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPets() != null) {
            List<Long> petIds = new ArrayList<>();
            for (Pet pet : customer.getPets()) {
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO, Employee employee) {
        if (employee != null) {
            BeanUtils.copyProperties(employeeDTO, employee, "id");
        } else {
            BeanUtils.copyProperties(employeeDTO, employee);
        }
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee, EmployeeDTO employeeDTO) {
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
    //</editor-fold>
}
