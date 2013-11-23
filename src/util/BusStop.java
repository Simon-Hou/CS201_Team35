package util;

import interfaces.Person;

import java.util.ArrayList;
import java.util.List;

public class BusStop {
	
	public BusStop(){
		
	}
	
	public BusStop(Loc location,List<Person> peopleWaiting){
		this.location = location;
		this.peopleWaiting = peopleWaiting;
	}
	
	public Loc location = new Loc(-1000,-1000);
	public List<Person> peopleWaiting = new ArrayList<Person>();
	
	
	public void waitForBus(Person p){
		peopleWaiting.add(p);
	}
	
	
	
	

}
