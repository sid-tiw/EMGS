package com.paytmbank.middleware.emgs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Project {
	@Id
	private String pid;
	private String name;
	
	private Date startDate;
	private Date endDate; // expected end date. Can be null.
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private Department dpt;

	@OneToMany(mappedBy = "pid")
	private List<Employee> emps; // all the employees in a certain project
}
