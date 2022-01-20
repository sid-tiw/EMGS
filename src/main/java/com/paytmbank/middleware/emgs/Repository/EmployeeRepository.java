package com.paytmbank.middleware.emgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paytmbank.middleware.emgs.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByPhone(String phone);
    Employee findByPhone(String phone);
    boolean existsByEmail(String email);
    Employee findByEmail(String email);
}
