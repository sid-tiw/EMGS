package com.paytmbank.middleware.emgs.entity;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.paytmbank.middleware.emgs.dto.DepartmentDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Department {
	@Id
	private String deptID;
	private String name;

	@Column(name = "`desc`")
	private String desc; // description of the depratment

	@OneToMany(mappedBy = "dpt")
	private List<Employee> emps;

	@OneToMany(mappedBy = "dpt")
	private List<Project> projects;

	public Department(DepartmentDTO deptDTO) {
		deptID = deptDTO.getDeptID();
		name = deptDTO.getName();
		desc = deptDTO.getDesc();
		emps = deptDTO.getEmps();
		projects = deptDTO.getProjects();
	}
}
