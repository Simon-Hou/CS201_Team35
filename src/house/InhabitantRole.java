package house;

import java.util.Timer;
import java.util.TimerTask;

import public_Object.Food;
import house.gui.InhabitantGui;
import interfaces.Inhabitant;
import interfaces.Person;
import role.Role;

public class InhabitantRole extends Role implements Inhabitant {

	//Constructor
	public InhabitantRole(){
		
	}
	
	public InhabitantRole(String name,Person p){
		this.self = p;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	
	//data
	String name;
	LivingUnit myRoom;
	Person self;
	//enum InhabitantState {IDLE,HUNGRY,FOODREADY, TIRED,EXIT};
	//public InhabitantState s=InhabitantState.IDLE;
	boolean wantEat=false;
	boolean wantSleep=false;
	boolean wantLeave=false;
	boolean foodReady=false;
	InhabitantGui gui;

	String foodEating=null;
	Timer timer;
	
	//msg
	
	public void msgTired(){
		//Do("I am tired");
		wantSleep=true;
		self.msgStateChanged();
	}
	
	public void msgGotHungry(){ //called by Person
		wantEat=true;
		self.msgStateChanged();
	}
	public void msgFoodReady(){ //called by Timer//may be able to merge all into one action
		foodReady=true;
		self.msgStateChanged();
	}
	public void msgLeaveHouse(){
		wantLeave=true;
		self.msgStateChanged();

	}

		
		

	

	//scheduler
	public boolean pickAndExecuteAnAction() {
		//Do("Deciding what to do");
		if(foodReady){
			PlateAndEat();
			return true;
		}
		if(wantEat){
			GetAndCook();
			return true;
		}
		if(wantSleep){
			Sleep();
			return true;
		}
		if(wantLeave){
			ExitHouse();
			return true;
		}
		
		//gui.DoIdle();		
		return false;
	}
	
	//action
	
	private void Sleep(){
		Do("Going to sleep");
		wantSleep=false;
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		self.msgThisRoleDone(this);
		//gui.DoSleep();
		//Do("I HAVE AWAKENED");
		return;
	}
	
	private void GetAndCook(){
		wantEat=false;
		gui.DoGoToFridge();
		PickFood();
		gui.DoGoToGrill();
		gui.DoIdle();
		timer.schedule(new TimerTask() {
			
			public void run() {
				print("Cooking");
				foodReady=true;
				//isHungry = false;
				stateChanged();
			}
		},
		500);
		
	}
	private void PlateAndEat(){
		foodReady=false;
		gui.DoPlateAndEat();
		self.msgDoneEating();
	}
	private void ExitHouse(){
		gui.DoExit();
		if(myRoom!=null){
			myRoom.inhabitant=null;
		}
		self.msgThisRoleDone(this);
	}
	
	private void PickFood(){
		for(Food food : myRoom.inventory){
			if(food.quantity>0){
				food.quantity--;
				foodEating=food.type;
				break;
			}
		}
	}
	//Animation
	
	
	//utilities
	public void setGui(InhabitantGui g){
		gui=g;
		
	}
	
	
}
