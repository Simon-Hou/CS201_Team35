package market;

public class Restaurant {
	
	CashierRole cashier;
	CookRole cook;

	public class CashierRole {
		
		void msgHereIsInvoice ( int invoice){
			
		}
		
	}
	
	public class CookRole {
		
		void msgHereIsDelivery(BusinessOrder order){
			
		}
		
		void msgWeHaveNothing(){
			
		}
		
	}

}
