package util;

import interfaces.BankInterface;
import interfaces.Person;
import bank.BankTellerRole;
import role.Role;

public class Job {
	
	public Job(){
		
	}
	
	public Job(Role role,int location,int shiftStart,int shiftEnd,Place placeOfWork,Person person){
		this.jobRole = role;
		this.location = location;
		this.shiftStart = shiftStart;
		this.shiftEnd = shiftEnd;
		this.placeOfWork = placeOfWork;
		
		if(jobRole instanceof BankTellerRole){
			((BankTellerRole) jobRole).setPerson(person);
			((BankTellerRole) jobRole).setBank(((BankMapLoc) placeOfWork).bank);
		}
		
	}
	
	public Role jobRole;
	public Place placeOfWork;
	public int location;
	public int shiftStart;
	public int shiftEnd;
}
