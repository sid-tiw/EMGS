package com.paytmbank.middleware.emgs.service;

import com.paytmbank.middleware.emgs.entity.Department;
import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.exception.DepartmentAlreadyPresent;
import com.paytmbank.middleware.emgs.exception.DepartmentNotFound;
import com.paytmbank.middleware.emgs.exception.EmployeeAlreadyPresent;
import com.paytmbank.middleware.emgs.exception.RequestError;
import com.paytmbank.middleware.emgs.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public void save(Department dept) {
        departmentRepository.save(dept);
    }

    /* To create new employee. An employee object is passed, and then checks are done inside this function, before ultimately calling
	the save function inside here. */
    public void create(Department dept) throws Exception {
        /* Checks start here */

        // If already found, then throw an error.
        if (departmentRepository.existsById(dept.getDeptID())) throw new DepartmentAlreadyPresent("Error!! Another department with same id exists.");

        /* Checks end here */

        this.save(dept);
    }

    /* To get the list of all available departments. Paged */
    public Page<Department> listAll() {
        Page<Department> lst = departmentRepository.findAll(Pageable.ofSize(10));
        return lst;
    }

    /* Get a Department by its id */
    public Department get(String deptId) throws Exception {
        if (!departmentRepository.existsById(deptId)) throw new DepartmentNotFound("Error!! No department with the given department id found.");
        return departmentRepository.getById(deptId);
    }
}
