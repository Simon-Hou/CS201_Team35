package util;

import interfaces.Person;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class Bus implements ActionListener{
	
	//DATA
	static int TIME_AT_STOP = 10000;
	
	Timer timer = new Timer(TIME_AT_STOP, this);

	List<BusStop> stops = new ArrayList<BusStop>();
	int currentStop = 0;
	
	List<Person> passengers  = new ArrayList<Person>();
	
	
	//SETTERS
	public void addStop(BusStop stop){
		this.stops.add(stop);
	}
	
	public void setBusStops(List<BusStop> stops){
		this.stops = stops;
	}
	
	
	//CONSTRUCTOR
	public Bus(){
		timer.start();
	}
	
	
	//METHODS
	public void GoToNextStop(){
		
		doGoToNextStop();
		
		
		
		tellPassengersArrived();
		tellNextStopPeopleArrived();
		
		currentStop+=1;
		
	}
	
	public void tellPassengersArrived(){
		for(Person p:this.passengers){
			p.msgBusAtStop(stops.get(currentStop+1));
		}
	}
	
	public void tellNextStopPeopleArrived(){
		
		for(Person p:this.stops.get(currentStop+1).peopleWaiting){
			p.msgBusAtStop(stops.get(currentStop+1));
		}
		
	}
	
	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		GoToNextStop();
		
	}
	
	private void doGoToNextStop(){
		//blah 
	}
	
	
	
	
	
	
}
