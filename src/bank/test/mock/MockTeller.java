package bank.test.mock;

import util.Task;
import bank.interfaces.Teller;

public class MockTeller implements Teller{

	public EventLog log = new EventLog();
	
	
	String name;
	public MockTeller(String name){
		this.name = name;
	}
	
	@Override
	public void msgIWantTo(Task t) {
		log.add(new LoggedEvent("Just got a new task: "+ t));
		
	}

	@Override
	public void msgDoneAndLeaving() {
		log.add(new LoggedEvent("My customer just left"));
		
	}

}
