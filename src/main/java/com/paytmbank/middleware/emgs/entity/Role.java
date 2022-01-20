package com.paytmbank.middleware.emgs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Role {
	@Id
	public String rid;
	public String name;

	/* Role Description. */
	/* The size of desc might be more than the maximum size allowed. Check back here if any error occurs. */
	@Column (name = "`desc`")
	public String desc;

	/* List of all the employees in the given role. */
	@OneToMany(mappedBy = "rid")
	List<Employee> emps;
}
