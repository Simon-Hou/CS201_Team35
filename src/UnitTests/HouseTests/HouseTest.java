package UnitTests.HouseTests;

import person.PersonAgent;
import person.test.mock.MockPerson;
import house.House;
import junit.framework.TestCase;

public class HouseTest extends TestCase {
	House house;
	PersonAgent p;

	public void setUp() throws Exception{
		house=new House();
		p=new PersonAgent("MockPerson", null);
	}

	public void testPersonIn(){
		assertEquals("the person pointer in the iniatial inhabitant role should be null. it is not.",house.room.inhabitant.self, null);
		house.personIn(p);
		assertEquals("the person pointer in the inhabitant role should be the person who get in. it is not.",house.room.inhabitant.self, p);


	}





}
