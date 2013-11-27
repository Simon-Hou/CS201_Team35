package UnitTests.HouseTests;

import person.PersonAgent;
import house.InhabitantRole;
import house.gui.InhabitantGui;
import junit.framework.TestCase;

public class HouseInhabitantTest extends TestCase {
	InhabitantRole i;
	InhabitantGui g;
	PersonAgent p;
	
	public void setUp() throws Exception{
		i=new InhabitantRole();
		g=new InhabitantGui(i);
		i.setGui(g);
		p=new PersonAgent("MockPerson", null);
		i.self=p;
	}
	
	
	public void testParallelMsgAndScheduer(){
		assertEquals("foodReady should be false, it is not",i.foodReady,false);
		assertEquals("wantEat should be false, it is not",i.wantEat,false);
		assertEquals("wantLeave should be false, it is not",i.wantLeave,false);
		assertEquals("wantSleep should be false it is not",i.wantSleep,false);
		assertTrue("scheduler should return false, it is not",i.pickAndExecuteAnAction()==false);

		//send messages
		i.msgFoodReady();
		assertEquals("foodReady should be true, it is not",i.foodReady,true);

		i.msgGotHungry();
		assertEquals("wantEat should be true, it is not",i.wantEat,true);

		i.msgLeaveHouse();
		assertEquals("wantLeave should be true, it is not",i.wantLeave,true);

		i.msgTired();
		assertEquals("wantSleep should be true, it is not",i.wantSleep,true);
		
		//run the schduler

		//assertTrue("scheduler should return true, it is not",i.pickAndExecuteAnAction()==true);
//		
//		assertEquals("scheduler should return true, it is not",i.pickAndExecuteAnAction(),true);
//		assertEquals("scheduler should return true, it is not",i.pickAndExecuteAnAction(),true);
//		assertEquals("scheduler should return true, it is not",i.pickAndExecuteAnAction(),true);
//		assertEquals("scheduler should return false, it is not",i.pickAndExecuteAnAction(),false);

	}
	
	//public void Test
}
