package house;

import interfaces.Inhabitant;
import interfaces.Person;
import role.Role;

public class InhabitantRole extends Role implements Inhabitant {

	
	//data
		Home myHome;
		Person self;
		enum InhabitantState {IDLE,HUNGRY,FOODREADY, EXIT};
		InhabitantState s=InhabitantState.IDLE;

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
	protected boolean pickAndExecuteAnAction() {
		
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

		}
		private void PlateAndEat(){

		}
		private void ExitHouse(){

		}

}
