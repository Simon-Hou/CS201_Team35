package restaurant.restaurantGabe.test.mock;


import restaurant.Restaurant;
//import restaurant.interfaces.Cashier;
import restaurant.restaurantGabe.interfaces.Customer;

/**
 * A sample MockCustomer built to unit test a CashierAgent.
 *
 * @author Monroe Ekilah
 *
 */
public class MockCustomer extends Mock implements Customer {

	/**
	 * Reference to the Cashier under test that can be set by the unit test.
	 */
	//public Cashier cashier;

	public EventLog log = new EventLog();
	
	@Override
	public String getName(){
		return super.getName();
	}
	
	
	public MockCustomer(String name) {
		super(name);

	}
	
	
	@Override
	public void msgHereIsChange(int change){
		
		log.add(new LoggedEvent("Being given $"+change+" in change"));
		
	}
	
	
	@Override
	public void msgPayNextTime(){
		
		log.add(new LoggedEvent("I'll pay next time"));
		
	}


	@Override
	public void msgAtRestaurant(Restaurant r) {
		// TODO Auto-generated method stub
		
	}
	
	

	/*@Override
	public void HereIsYourTotal(double total) {
		log.add(new LoggedEvent("Received HereIsYourTotal from cashier. Total = "+ total));

		if(this.name.toLowerCase().contains("thief")){
			//test the non-normative scenario where the customer has no money if their name contains the string "theif"
			cashier.IAmShort(this, 0);

		}else if (this.name.toLowerCase().contains("rich")){
			//test the non-normative scenario where the customer overpays if their name contains the string "rich"
			cashier.HereIsMyPayment(this, Math.ceil(total));

		}else{
			//test the normative scenario
			cashier.HereIsMyPayment(this, total);
		}
	}

	@Override
	public void HereIsYourChange(double total) {
		log.add(new LoggedEvent("Received HereIsYourChange from cashier. Change = "+ total));
	}

	@Override
	public void YouOweUs(double remaining_cost) {
		log.add(new LoggedEvent("Received YouOweUs from cashier. Debt = "+ remaining_cost));
	}*/

}
