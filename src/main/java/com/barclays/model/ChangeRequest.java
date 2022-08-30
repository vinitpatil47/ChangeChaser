package com.barclays.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ChangeRequest {

	@Id
	private String changeRecord;
	
	@Column 
	private String sysId;

	@Column
	private String description;
	
	@Column
	private String shortDescription;
	
	@Column 
	private String risk;
	
	@Column 
	private String assignGroup;
	
	@Column 
	private Date plannedStartDate;
	
	@Column
	private Date plannedEndDate;
	
	@Column 
	private String state;
	
	@Column 
	private String justification;
	
	@Column 
	private String testPlan;
	
	@Column 
	private int flag;
	
	@Column 
	private String ImplementationPlan;
	
	@Column 
	private String riskImpactAnalysis;
	
	@Column 
	private String assignedTo;
	
	@Column
	private String backoutPlan;

	public ChangeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public ChangeRequest(String changeRecord, String shortDescription, String risk, String assignGroup,
//			Date plannedStartDate, int state) {
//		super();
//		this.changeRecord = changeRecord;
//		this.shortDescription = shortDescription;
//		this.risk = risk;
//		this.assignGroup = assignGroup;
//		this.plannedStartDate = plannedStartDate;
//		this.state = state;
//	}
//
//	public String getChangeRecord() {
//		return changeRecord;
//	}
//
//	public void setChangeRecord(String changeRecord) {
//		this.changeRecord = changeRecord;
//	}
//
//	public String getShortDescription() {
//		return shortDescription;
//	}
//
//	public void setShortDescription(String shortDescription) {
//		this.shortDescription = shortDescription;
//	}
//
//	public String getRisk() {
//		return risk;
//	}
//
//	public void setRisk(String risk) {
//		this.risk = risk;
//	}
//
//	public String getAssignGroup() {
//		return assignGroup;
//	}
//
//	public void setAssignGroup(String assignGroup) {
//		this.assignGroup = assignGroup;
//	}
//
//	public Date getPlannedStartDate() {
//		return plannedStartDate;
//	}
//
//	public void setPlannedStartDate(Date plannedStartDate) {
//		this.plannedStartDate = plannedStartDate;
//	}
//
//	public int getState() {
//		return state;
//	}
//
//	public void setState(int state) {
//		this.state = state;
//	}
//
//	@Override
//	public String toString() {
//		return "ChangeRequest [changeRecord=" + changeRecord + ", shortDescription=" + shortDescription + ", risk="
//				+ risk + ", assignGroup=" + assignGroup + ", plannedStartDate=" + plannedStartDate + ", state=" + state
//				+ "]";
//	}
	
	
}
