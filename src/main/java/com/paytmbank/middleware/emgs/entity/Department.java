package com.paytmbank.middleware.emgs.entity;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Department {
	@Id
	public String deptID;
	public String name;
	public String desc; // description of the depratment

	@OneToMany(mappedBy = "dpt")
	List<Employee> emps;

	@OneToMany(mappedBy = "dpt")
	List<Project> projects;
}