package util;

import interfaces.BankCustomer;
import interfaces.Person;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Timer;


public class Bus extends Vehicle implements ActionListener{
	
	
	//When this bus was designed, it was imagined to work (with the Person scheduler)
	//in the following way:
	//1) When a person decides to take a bus (however), they go to the bus stop - for
	//	which they have some pointer - and call busStop.waitForBus()
	//2) The Bus, which is at some other station, gets a message that his stop timer
	//	has timed out, so he calls the appropriate animation (for driving to the next 
	//	stop) and then alerts both the passengers and the people at the next stop that
	//	the buss has arrived.
	//3) Given that message, the people will then add and remove themselves from 
	//	the bus as necessary. 
	//NOTE: It was assumed that the PersonAgent waits for the Bus's message to board/
	//	leave the bus. 
	
	//DATA
	static int TIME_AT_STOP = 10000;
	
	Timer timer = new Timer(TIME_AT_STOP, this);

	public List<BusStop> stops = new ArrayList<BusStop>();
	public int currentStop = 0;
	
	
	
	
	//SETTERS
	public void addStop(BusStop stop){
		this.stops.add(stop);
	}
	
	public void setBusStops(List<BusStop> stops){
		this.stops = stops;
	}
	
	public void setCurrentStop(int currentStop){
		this.currentStop = currentStop;
	}
	
	
	//CONSTRUCTOR
	public Bus(){
		//timer.start();
	}
	
	public void start(int currentStop){
		this.currentStop= currentStop;
		timer.start();
	}
	
	public void start(){
		timer.start();
	}
	
	
	//METHODS
	public void GoToNextStop(){
		
		doGoToNextStop();
		
		
		
		tellPassengersArrived();
		tellNextStopPeopleArrived();
		
		currentStop = (currentStop+1)%stops.size();
		
	}
	
	public void tellPassengersArrived(){
		
		synchronized(passengers){
			for(Person p:this.passengers){
				p.msgBusAtStop(stops.get(currentStop+1));
			}
		}
	}
	
	
	public void tellNextStopPeopleArrived(){
		
		synchronized(this.stops.get(currentStop+1).peopleWaiting){
			for(Person p:this.stops.get(currentStop+1).peopleWaiting){
				p.msgBusAtStop(stops.get(currentStop+1));
			}
		}
		
	}
	
	
	public void getOnBus(Person p){
		if(stops.get(currentStop).peopleWaiting.contains(p)){
			passengers.add(p);
			stops.get(currentStop).peopleWaiting.remove(p);
			return;
		}
		else{
			System.err.println("Someone tried to board a bus when it wasn't "
					+ "at their stop");
		}
		
	}
	
	public void getOffBuss(Person p){
		passengers.remove(p);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		GoToNextStop();
		
	}
	
	private void doGoToNextStop(){
		//blah 
	}
	
	
	
	
	
	
}