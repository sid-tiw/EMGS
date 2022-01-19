package com.paytmbank.middleware.emgs.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paytmbank.middleware.emgs.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
