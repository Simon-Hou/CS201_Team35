package house;

import public_Object.Food;
import interfaces.Inhabitant;
import interfaces.Person;
import role.Role;

public class InhabitantRole extends Role implements Inhabitant {

	//Constructor
	public InhabitantRole(String name,Person p){
		this.name = name;
		this.self = p;
	}
	
	public String getName(){
		return name;
	}
	
	
	//data
	String name;
	LivingUnit myRoom = new LivingUnit();
	Person self;
	enum InhabitantState {IDLE,HUNGRY,FOODREADY, TIRED,EXIT};
	public InhabitantState s=InhabitantState.IDLE;
	String foodEaten=null;

	//msg
	
	public void msgTired(){
		this.s = InhabitantState.TIRED;
		self.msgStateChanged();
	}
	
	public void msgGotHungry(){ //called by Person
		s=InhabitantState.HUNGRY;
		self.msgStateChanged();
	}
	public void msgFoodReady(){ //called by Timer//may be able to merge all into one action
		s=InhabitantState.FOODREADY;
		self.msgStateChanged();
	}
	public void msgLeaveHouse(){
		s=InhabitantState.EXIT;
		self.msgStateChanged();

	}

		
		

	

	//scheduler
	public boolean pickAndExecuteAnAction() {
		if(s==InhabitantState.TIRED){
			Sleep();
			return true;
		}
		
		if(s==InhabitantState.HUNGRY){
			GetAndCook();
		}
		if(s==InhabitantState.FOODREADY){
			PlateAndEat();
		}
		if(s==InhabitantState.EXIT){
			ExitHouse();
		}
				
		return false;
	}
	
	//action
	
	private void Sleep(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s = InhabitantState.EXIT;
		Do("I HAVE AWAKENED");
		return;
	}
	
	private void GetAndCook(){
		PickFood();
		DoGetAndCook();
	}
	private void PlateAndEat(){
		DoPlateAndEat();
		self.msgDoneEating();
	}
	private void ExitHouse(){
		DoExit();
		myRoom.inhabitant=null;
		self.msgThisRoleDone(this);
	}
	
	private void PickFood(){
		for(Food food : myRoom.inventory){
			if(food.quantity>0){
				food.quantity--;
				foodEaten=food.type;
				break;
			}
		}
	}
	//Animation
	public void DoGetAndCook(){
		
	}
	public void DoPlateAndEat(){
		
	}

	public void DoExit(){
		
	}
}
