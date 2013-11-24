package util;

import interfaces.BankInterface;
import interfaces.Person;
import bank.BankTellerRole;
import role.Role;

public class Job {
	
	public Job(){
		
	}
	
	public Job(Role role,int location,int shiftStart,int shiftEnd,Place placeOfWork,Person person,JobType jt){
		this.jobRole = role;
		this.location = location;
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
	public Place placeOfWork;
	public int location;
	public int shiftStart;
	public int shiftEnd;
	public enum JobType {BankTeller,MarketHost,MarketEmployee,MarketCashier,
		MarketDeliveryMan,RestaurantHost,RestaurantWaiter,RestaurantCook,
		RestaurantCashier};
	public JobType jobType;
		
		
		
}
