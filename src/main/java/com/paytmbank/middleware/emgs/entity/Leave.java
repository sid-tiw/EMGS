package com.paytmbank.middleware.emgs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Getter @Setter @NoArgsConstructor
public class Leave {
	@Id
	private String lid;
	private String reason;
	private Date startDate;
	private Date endDate;

	private boolean isPaid;
	
	@OneToOne(mappedBy = "lid")
	private Employee emp; // the employee who has taken this leave.
}
