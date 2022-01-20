package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.details.EmployeeDetailsBasic;
import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.service.EmployeeService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Page<Employee> pg = employeeService.listAll();
        return ResponseEntity.ok().body(pg);
    }

    /* As the name suggests, it will create a new employee, with validity tests that will take place in the service class */
    @PostMapping("/createEmployee")
    public ResponseEntity<?> createNewEmployee(@RequestBody Employee emp) {
        /* Basic returnable object, detailing the error and status. */
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            employeeService.create(emp);
        } catch (Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
        /* If the check is successful then return the basic details of the empolyee */
        obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());

        return ResponseEntity.ok().body(obj);
    }

    /* Get Employee by its id. The id parameter maps to eid. */
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable(name = "id", required = true) String id) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            Employee emp = employeeService.find(id);
            return ResponseEntity.ok().body(emp);
        } catch(Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Get Employee by its phone number. The phone parameter maps to Employee.phone. */
    @GetMapping("/getEmployeeByPhone/{phone}")
    public ResponseEntity<?> getEmployeeByPhone(@PathVariable(name = "phone", required = true) String id) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            Employee emp = employeeService.findByPhone(id);
            return ResponseEntity.ok().body(emp);
        } catch(Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Get Employee by its email. The email parameter maps to Employee.email. */
    @GetMapping("/getEmployeeByEmail/{email}")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable(name = "email", required = true) String id) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            Employee emp = employeeService.findByEmail(id);
            return ResponseEntity.ok().body(emp);
        } catch(Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* The delete controller method */
    @PostMapping("/delete/{eid}")
    public ResponseEntity<?> delete(@PathVariable(name = "eid", required = true) String eid) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            employeeService.delete(eid);
            return ResponseEntity.ok().body("Delete successful.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    /* Update the employee */
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Employee emp) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            employeeService.update(emp);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }
}
