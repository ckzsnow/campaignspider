package com.ys.model;

public class Company {

	private String cName;
	
	private long registerNo;
	
	private String representativeName;
	
	private String companyLocation;
	
	private String registerMoney;
	//状态
	private String state;
	
	private String companyType;
	//建立日期
	private String buildDate;
	//期限
	private String deadline;
	//登记机关
	private String registerLocation;
	//受理机关
	private String acceptLocation;
	//经营范围
	private String operateScope;
	
	public Company() {
		super();
	}
	
	
	public Company( String cName, long registerNo, String representativeName, String companyLocation,
			String registerMoney, String state, String companyType, String buildDate, String deadline,
			String registerLocation, String acceptLocation, String operateScope) {
		super();
		this.cName = cName;
		this.registerNo = registerNo;
		this.representativeName = representativeName;
		this.companyLocation = companyLocation;
		this.registerMoney = registerMoney;
		this.state = state;
		this.companyType = companyType;
		this.buildDate = buildDate;
		this.deadline = deadline;
		this.registerLocation = registerLocation;
		this.acceptLocation = acceptLocation;
		this.operateScope = operateScope;
	}


	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public long getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(long registerNo) {
		this.registerNo = registerNo;
	}

	public String getRepresentativeName() {
		return representativeName;
	}

	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}

	public String getCompanyLocation() {
		return companyLocation;
	}

	public void setCompanyLocation(String companyLocation) {
		this.companyLocation = companyLocation;
	}

	public String getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(String registerMoney) {
		this.registerMoney = registerMoney;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(String registerLocation) {
		this.registerLocation = registerLocation;
	}

	public String getAcceptLocation() {
		return acceptLocation;
	}

	public void setAcceptLocation(String acceptLocation) {
		this.acceptLocation = acceptLocation;
	}

	public String getOperateScope() {
		return operateScope;
	}

	public void setOperateScope(String operateScope) {
		this.operateScope = operateScope;
	}


	@Override
	public String toString() {
		return "Company [cName=" + cName + ", registerNo=" + registerNo + ", representativeName=" + representativeName
				+ ", companyLocation=" + companyLocation + ", registerMoney=" + registerMoney + ", state=" + state
				+ ", companyType=" + companyType + ", buildDate=" + buildDate + ", deadline=" + deadline
				+ ", registerLocation=" + registerLocation + ", acceptLocation=" + acceptLocation + ", operateScope="
				+ operateScope + "]";
	}
	
	
	
}
