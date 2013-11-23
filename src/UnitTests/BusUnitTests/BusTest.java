package UnitTests.BusUnitTests;

import interfaces.Person;

import java.util.ArrayList;
import java.util.List;

import util.Bank;
import util.Bus;
import util.BusStop;
import util.Loc;
import util.deposit;
import util.openAccount;
import util.withdrawal;
import UnitTests.mock.bankMock.MockBankPerson;
import UnitTests.mock.bankMock.MockBankTeller;
import UnitTests.mock.busMock.MockBusPerson;
import bank.BankCustomerRole;
import bank.BankTellerRole;
import junit.framework.*;

public class BusTest extends TestCase{
	
	//Unit test for the BankCustomerRole
	
	
	List<Person> people1 = new ArrayList<Person>();
	List<Person> people2 = new ArrayList<Person>();
	BusStop stop1;
	BusStop stop2;
	List<BusStop> stops = new ArrayList<BusStop>();
	
	Bus bus;
	
	
	public void setUp() throws Exception {
		super.setUp();
		
		for(int i = 1;i<10;++i){
			people1.add(new MockBusPerson());
			people2.add(new MockBusPerson());
		}
	
		stop1  = new BusStop(new Loc(0,0),people1);
		stop2 = new BusStop(new Loc(1,0),people2);
		stops.add(stop1);
		stops.add(stop2);
		
		bus = new Bus();
		bus.setBusStops(stops);
		
		
		
	}
	
	
	/** This test will examine the only normative scenario - a bus goes to the stop,
	 * picks people up, goes to the next stop, and repeats.
	 */
	public void testBusScenario(){
		
	}
	
}