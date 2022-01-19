package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.service.EmployeeService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.net.http.HttpResponse;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /* Get the list of all users. This controller method is strictly for testing purposes. */
    /* Although this will be available to the super_admin during production (if any such thing happens). */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(employeeService.listAll());
    }

    /* As the name suggests, it will create a new employee, with validity tests that will take place in the service class */
    @PostMapping("/createEmployee")
    public ResponseEntity<?> createNewEmployee(@RequestBody Employee emp) {
        try {
            employeeService.create(emp);
        } catch (Exception e) {
            System.out.println(emp.getEmail());
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok().body("User created successfully.");
    }
}
