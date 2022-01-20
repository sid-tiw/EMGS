package com.paytmbank.middleware.emgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paytmbank.middleware.emgs.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    public boolean existsByPhone(String phone);
    public Employee findByPhone(String phone);
    public boolean existsByEmail(String email);
    public Employee findByEmail(String email);
}
