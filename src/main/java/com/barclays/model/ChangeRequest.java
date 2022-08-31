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
	private String approvalSysId;

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
	private String implementationPlan;
	
	@Column 
	private String riskImpactAnalysis;
	
	@Column
	private String backoutPlan;

	public ChangeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangeRequest(String changeRecord, String sysId, String approvalSysId, String description,
			String shortDescription, String risk, String assignGroup, Date plannedStartDate, Date plannedEndDate,
			String state, String justification, String testPlan, int flag, String implementationPlan,
			String riskImpactAnalysis, String backoutPlan) {
		super();
		this.changeRecord = changeRecord;
		this.sysId = sysId;
		this.approvalSysId = approvalSysId;
		this.description = description;
		this.shortDescription = shortDescription;
		this.risk = risk;
		this.assignGroup = assignGroup;
		this.plannedStartDate = plannedStartDate;
		this.plannedEndDate = plannedEndDate;
		this.state = state;
		this.justification = justification;
		this.testPlan = testPlan;
		this.flag = flag;
		this.implementationPlan = implementationPlan;
		this.riskImpactAnalysis = riskImpactAnalysis;
		this.backoutPlan = backoutPlan;
	}



	public String getChangeRecord() {
		return changeRecord;
	}

	public void setChangeRecord(String changeRecord) {
		this.changeRecord = changeRecord;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getApprovalSysId() {
		return approvalSysId;
	}

	public void setApprovalSysId(String approvalSysId) {
		this.approvalSysId = approvalSysId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getAssignGroup() {
		return assignGroup;
	}

	public void setAssignGroup(String assignGroup) {
		this.assignGroup = assignGroup;
	}

	public Date getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(Date plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public Date getPlannedEndDate() {
		return plannedEndDate;
	}

	public void setPlannedEndDate(Date plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getImplementationPlan() {
		return implementationPlan;
	}

	public void setImplementationPlan(String implementationPlan) {
		this.implementationPlan = implementationPlan;
	}

	public String getRiskImpactAnalysis() {
		return riskImpactAnalysis;
	}

	public void setRiskImpactAnalysis(String riskImpactAnalysis) {
		this.riskImpactAnalysis = riskImpactAnalysis;
	}

	public String getBackoutPlan() {
		return backoutPlan;
	}

	public void setBackoutPlan(String backoutPlan) {
		this.backoutPlan = backoutPlan;
	}

	@Override
	public String toString() {
		return "ChangeRequest [changeRecord=" + changeRecord + ", sysId=" + sysId + ", approvalSysId=" + approvalSysId
				+ ", description=" + description + ", shortDescription=" + shortDescription + ", risk=" + risk
				+ ", assignGroup=" + assignGroup + ", plannedStartDate=" + plannedStartDate + ", plannedEndDate="
				+ plannedEndDate + ", state=" + state + ", justification=" + justification + ", testPlan=" + testPlan
				+ ", flag=" + flag + ", implementationPlan=" + implementationPlan + ", riskImpactAnalysis="
				+ riskImpactAnalysis + ", backoutPlan=" + backoutPlan + "]";
	}
	
}
