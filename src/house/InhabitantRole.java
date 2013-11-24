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
	
	
	//data
		String name;
		LivingUnit myRoom;
		Person self;
		enum InhabitantState {IDLE,HUNGRY,FOODREADY, EXIT};
		InhabitantState s=InhabitantState.IDLE;
		String foodEaten=null;

		//msg
		public void msgGotHungry(){ //called by Person
		s=InhabitantState.HUNGRY;
		stateChanged();
	}
		public void msgFoodReady(){ //called by Timer//may be able to merge all into one action
		s=InhabitantState.FOODREADY;
		stateChanged();
	}
		public void msgLeaveHouse(){
	s=InhabitantState.EXIT;
		stateChanged();

	}

		
		

	

	//scheduler
	public boolean pickAndExecuteAnAction() {
		
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
