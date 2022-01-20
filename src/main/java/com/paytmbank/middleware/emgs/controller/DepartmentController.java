package com.paytmbank.middleware.emgs.controller;

import com.paytmbank.middleware.emgs.details.DepartmentDetailsBasic;
import com.paytmbank.middleware.emgs.entity.Department;
import com.paytmbank.middleware.emgs.repository.DepartmentRepository;
import com.paytmbank.middleware.emgs.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /* Controller method to get the list of all the departments */
    @GetMapping("/getDeps")
    public ResponseEntity<?> getDeps() {
        Page<Department> lst = departmentService.listAll();
        return ResponseEntity.ok().body(lst);
    }

    /* Add a department to the list of available documents. */
    @PostMapping("/addDepartment")
    public ResponseEntity<?> addDepartment(@RequestBody Department dept) {
        DepartmentDetailsBasic obj = new DepartmentDetailsBasic();
        try {
            departmentService.create(dept);
        } catch(Exception e) {
            obj.setErrorDesc(e.getLocalizedMessage());
            obj.setStatus("Failure!");
            return ResponseEntity.badRequest().body(obj);
        }
        obj = new DepartmentDetailsBasic(dept.getDeptID(), dept.getName());
        return ResponseEntity.ok().body(obj);
    }

    /* Get the department with given id. */
    @GetMapping("/getDepartment/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable(name = "id", required = true) String id) {
        DepartmentDetailsBasic obj = new DepartmentDetailsBasic();
        try {
            Department dept = departmentService.get(id);
            return ResponseEntity.ok().body(dept);
        } catch(Exception e) {
            obj.setStatus("Failure!");
            obj.setErrorDesc(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(obj);
        }
    }
}
