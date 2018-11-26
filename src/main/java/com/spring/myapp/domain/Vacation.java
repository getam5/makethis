package com.spring.myapp.domain;

import java.util.Date;

public class Vacation {
	
	private String vacid;
	private String vacationname;
	private String vacstate;
	private Date regdate;
	
	public String getVacid() {
		return vacid;
	}
	public void setVacid(String vacid) {
		this.vacid = vacid;
	}
	public String getVacationname() {
		return vacationname;
	}
	public void setVacationname(String vacationname) {
		this.vacationname = vacationname;
	}
	public String getVacstate() {
		return vacstate;
	}
	public void setVacstate(String vacstate) {
		this.vacstate = vacstate;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
}
