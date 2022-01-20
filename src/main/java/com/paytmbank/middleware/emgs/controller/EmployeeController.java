package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.details.EmployeeDetailsBasic;
import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping("/updateEmployee")
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

    /* Drop project from employee */
    @PostMapping("/dropProject/{eid}")
    public ResponseEntity<?> dropProject(@PathVariable(name = "eid", required = true) String eid) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            employeeService.dropProject(eid);
            Employee emp = employeeService.find(eid);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Grant leave to the employee
    * Pass JSON data to this post method.
    * The JSON data must contain these fields :
    * "eid": {The employee Id}
    * "lid": {The Leave Id (long type) } */
    @PostMapping("/grantLeave")
    public ResponseEntity<?> grantLeave(@RequestBody String data) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            Employee emp = employeeService.grantLeave(data);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            obj.setStatus("Failure");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Add project to Employee
    * Pass JSON data to this post method.
    * Must contain two fields :
    * "eid": {The Employee Id}
    * "pid": {The Project Id} */
    @PostMapping("/addProject/")
    public ResponseEntity<?> addProject(@RequestBody String data) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            Employee emp = employeeService.addProject(data);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }
}
