package util;

import java.util.concurrent.Semaphore;

import person.PersonAgent;
import cityGui.SimCityGui;
import cityGui.test.CarAgentGui;

public class CarAgent extends VehicleAgent{
	
	
	//Data
	
	public SimCityGui cityGui;
	
	public CarAgentGui gui;
	public PersonAgent passenger;
	
	public Loc currentLocation;
	public Loc destination;
	
	public boolean inUse = false;
	
	public Semaphore atDestination = new Semaphore(0,true);
	
	
	//Messages
	
	public void msgTakeMeTo(Loc from, Loc to){
		inUse = true;
		this.currentLocation = from;
		this.destination = to;
	}
	
	
	public void msgCarArrived(Loc loc){
		
	}
	
	
	//Scheduler
	
	@Override
	public boolean pickAndExecuteAnAction(){
		
		if(inUse){
			DriveToDestination();
		}
		
		return false;
	}
	
	
	//Actions
	
	public void DriveToDestination(){
		
		cityGui.city.movings.add(gui);
		
		doDriveToDestination();
		
		passenger.msgCarArrivedAtLoc(destination);
		
		cityGui.city.movings.remove(gui);
		
		inUse = false;
		
	}
	
	
	//Gui
	
	public void doDriveToDestination(){
		
		gui.goTo(destination.x, destination.y);
		
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
