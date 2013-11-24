package UnitTests.mock.restaurantLindaMock;


import restaurantLinda.*;
import restaurantLinda.gui.CustomerGui;
import interfaces.restaurantLinda.*;

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
        public Cashier cashier;
        public Host host;

        public MockCustomer(String name) {
                super(name);
        }

		@Override
		public void msgRestaurantFull() {
			log.add(new LoggedEvent("Received message from host that restaurant is full"));
			
			if (name.contains("wait")){
				host.msgIWillWait(this, true);
			}
			else{
				log.add(new LoggedEvent("Decided not to wait. Did not send message to host"));
			}
			
		}

		@Override
		public void msgFollowMe(Waiter w, Menu m) {
			log.add(new LoggedEvent("Received followMe from waiter " + w.getName() + "and also received a menu: " + m));
		}

		@Override
		public void msgWhatDoYouWant() {
			log.add(new LoggedEvent("Received message from waiter asking to order"));
			
		}

		@Override
		public void msgRedoOrder(Menu menu, String food) {
			log.add(new LoggedEvent("Received message to redo order. Old choice (that is out) is " + food + " and was given a new menu: " + menu));
		}

		@Override
		public void msgHereIsFood(String food) {
			log.add(new LoggedEvent("Food " + food + " has arrived"));
		}

		@Override
		public void msgHereIsCheck(Check bill, Cashier cashier) {
			log.add(new LoggedEvent("Received check " + bill + " and have been assigned to Cashier " + cashier));
			
		}

		@Override
		public void msgPaymentReceived(int owed) {
			log.add(new LoggedEvent("Received message from cashier and owe " + owed));
			
		}

		@Override
		public CustomerGui getGui() {
			return null;
		}

}