package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.details.EmployeeDetailsBasic;
import com.paytmbank.middleware.emgs.details.TicketDetailsBasic;
import com.paytmbank.middleware.emgs.dto.EmployeeDTO;
import com.paytmbank.middleware.emgs.dto.TicketDTO;
import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.entity.Ticket;
import com.paytmbank.middleware.emgs.exception.EmployeeNotFound;
import com.paytmbank.middleware.emgs.exception.RequestError;
import com.paytmbank.middleware.emgs.exception.UnauthorizedUser;
import com.paytmbank.middleware.emgs.security.JwtUtil;
import com.paytmbank.middleware.emgs.service.EmployeeService;

import com.paytmbank.middleware.emgs.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    TicketService ticketService;

    @Autowired
    JwtUtil jwtUtil;
    
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "You are not authorized for this operation.";
    private static final String FAILURE_MESSAGE = "Failure!";

    private Employee currentUser(String token) throws RequestError, EmployeeNotFound {
        if (!token.startsWith("Bearer ")) throw new RequestError("Token invalid!!");
        String jwtToken = token.substring(7);
        String eid = jwtUtil.extractUsername(jwtToken);

        return employeeService.find(eid);
    }

    /* Get the list of all users. This controller method is strictly for testing purposes. */
    /* Although this will be available to the super_admin during production (if any such thing happens). */
    @GetMapping("/users")
    public ResponseEntity<Page<Employee>> getUsers() {
        Page<Employee> pg = employeeService.listAll();
        return ResponseEntity.ok().body(pg);
    }

    /* As the name suggests, it will create a new employee, with validity tests that will take place in the service class */
    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeDetailsBasic> createNewEmployee(@RequestBody EmployeeDTO emp, @RequestHeader (name="Authorization") String token) {
        /* Basic returnable object, detailing the error and status. */
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            // Check if the employee who is creating the user, is an admin or not.
            Employee currentUser = currentUser(token);

            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser("You must be admin to perform this operation.");

            // Create method uses emp, do some checks and then save it to the repo.
            employeeService.create(emp.getEmployee());
        } catch (UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        } catch (Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
        /* If the check is successful then return the basic details of the employee */
        obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());

        return ResponseEntity.ok().body(obj);
    }

    /* Get Employee by its id. The id parameter maps to eid. */
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<EmployeeDetailsBasic> getEmployee(@PathVariable(name = "id", required = true) String id, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same eid, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
            if (currentUser.getEid().compareTo(id) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            Employee emp = employeeService.find(id);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());

            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        } catch(Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Get Employee by its phone number. The phone parameter maps to Employee.phone. */
    @GetMapping("/getEmployeeByPhone/{phone}")
    public ResponseEntity<EmployeeDetailsBasic> getEmployeeByPhone(@PathVariable(name = "phone", required = true) String phone, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same phone, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
            if (currentUser.getPhone().compareTo(phone) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            Employee emp = employeeService.findByPhone(phone);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());

            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        } catch(Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Get Employee by its email. The email parameter maps to Employee.email. */
    @GetMapping("/getEmployeeByEmail/{email}")
    public ResponseEntity<EmployeeDetailsBasic> getEmployeeByEmail(@PathVariable(name = "email", required = true) String email, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same email, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
            if (currentUser.getEmail().compareTo(email) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            Employee emp = employeeService.findByEmail(email);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());

            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        } catch(Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* The delete controller method */
    @PostMapping("/delete/{eid}")
    public ResponseEntity<EmployeeDetailsBasic> delete(@PathVariable(name = "eid", required = true) String eid, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same email, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
            if (currentUser.getEid().compareTo(eid) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            employeeService.delete(eid);
            obj.setStatus("Delete successful.");
            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        } catch (Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Update the employee */
    @PostMapping("/updateEmployee")
    public ResponseEntity<EmployeeDetailsBasic> update(@RequestBody EmployeeDTO emp, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same eid, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
            if (currentUser.getEid().compareTo(emp.getEid()) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            if (currentUser.getEid().compareTo(emp.getEid()) == 0) {
                if (currentUser.getEmail().compareTo(emp.getEmail()) != 0)
                    throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
                if (currentUser.getPhone().compareTo(emp.getPhone()) != 0)
                    throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
                if (currentUser.getLid() != emp.getLid())
                    throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
                if (currentUser.getPid() != emp.getPid())
                    throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
                if (currentUser.getDpt() != emp.getDpt())
                    throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);
            }

            employeeService.update(emp.getEmployee());
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        } catch (Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Drop project from employee */
    @PostMapping("/dropProject/{eid}")
    public ResponseEntity<EmployeeDetailsBasic> dropProject(@PathVariable(name = "eid", required = true) String eid, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same eid, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            employeeService.dropProject(eid);
            Employee emp = employeeService.find(eid);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        }  catch (Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Grant leave to the employee
     * Pass JSON data to this post method.
     * The JSON data must contain the fields eid and pid */
    @PostMapping("/grantLeave")
    public ResponseEntity<EmployeeDetailsBasic> grantLeave(@RequestBody String data, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same eid, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            Employee emp = employeeService.grantLeave(data);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        }  catch (Exception e) {
            obj.setStatus("Failure");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* Add project to Employee
     * Pass JSON data to this post method.
     * Must contain two fields eid and pid. */
    @PostMapping("/addProject/")
    public ResponseEntity<EmployeeDetailsBasic> addProject(@RequestBody String data, @RequestHeader (name="Authorization") String token) {
        EmployeeDetailsBasic obj = new EmployeeDetailsBasic();
        try {
            /* Check if the user who is making the request is having the same eid, or is admin or not. */
            Employee currentUser = currentUser(token);
            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            Employee emp = employeeService.addProject(data);
            obj = new EmployeeDetailsBasic(emp.getEid(), emp.getFname(), emp.getSname());
            return ResponseEntity.ok().body(obj);
        } catch(UnauthorizedUser e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.status(401).body(obj);
        } catch (Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }

    /* To raise a support ticket, to do an operation that can only be done by admin. */
    @PostMapping("/raiseTicket")
    public ResponseEntity<TicketDetailsBasic> raiseTicket(@RequestBody TicketDTO tc, @RequestHeader (name="Authorization") String token) {
        TicketDetailsBasic obj = new TicketDetailsBasic();
        try {
            /* Find which user raised the ticket. Associate the user with the ticket. */
            Employee currentUser = currentUser(token);
            Ticket ntc = tc.getTicket();
            ntc.setRaisedBy(currentUser);

            ticketService.saveTicket(ntc);

            obj = new TicketDetailsBasic(ntc);

            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            obj.setStatus(FAILURE_MESSAGE);
            obj.setErrorDesc(e.getLocalizedMessage());

            return ResponseEntity.internalServerError().body(obj);
        }
    }

    @GetMapping("/viewTickets")
    public ResponseEntity<String> viewTickets(@RequestParam(value = "p", defaultValue = "1") String pageNumber, @RequestHeader (name="Authorization") String token) {
        try {
            Employee currentUser = currentUser(token);

            if (currentUser.getRole().compareTo(ROLE_ADMIN) != 0)
                throw new UnauthorizedUser(UNAUTHORIZED_EXCEPTION_MESSAGE);

            int pgNo = Integer.parseInt(pageNumber);
            List<Ticket> pageTickets = ticketService.listAll(pgNo);

            return ResponseEntity.ok().body(pageTickets.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Some error occurred.");
        }
    }

    @GetMapping("/logoutSuccessful")
    public ResponseEntity<String> postLogout() {
        return ResponseEntity.ok().body("Logout successful.");
    }
}
