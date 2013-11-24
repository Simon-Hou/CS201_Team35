package util;

import interfaces.BankInterface;
import interfaces.Person;
import interfaces.PlaceOfWork;
import bank.BankTellerRole;
import role.Role;

public class Job {
	
	public Job(){
		
	}
	
	public Job(Role role,int shiftStart,int shiftEnd,PlaceOfWork placeOfWork,Person person,JobType jt){
		this.jobRole = role;
		//this.location = location;
		this.shiftStart = shiftStart;
		this.shiftEnd = shiftEnd;
		this.placeOfWork = placeOfWork;
		this.jobType = jt;
		if(jobRole instanceof BankTellerRole){
			((BankTellerRole) jobRole).setPerson(person);
			((BankTellerRole) jobRole).setBank(((BankMapLoc) placeOfWork).bank);
		}
		
	}
	
	public Role jobRole;
	public PlaceOfWork placeOfWork;
	//public int location;
	public int shiftStart;
	public int shiftEnd;
	public JobType jobType;
		
		
		
}
