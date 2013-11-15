package market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import role.Role;
import interfaces.MarketDeliveryMan;

public class MarketDeliveryManRole extends Role implements MarketDeliveryMan {

	//-----------------------------DATA--------------------------------
	private List<BusinessOrder> orders = new ArrayList<BusinessOrder>();
	private List<MyPayment> payments = new ArrayList<MyPayment>();
	
	
	
	//-----------------------------MESSAGES--------------------------------
	public void msgDeliverThisOrder(BusinessOrder order){
		orders.add(order);
		stateChanged();
		
	}
	
	public void msgHereIsPayment(int payment){
		MyPayment pay = new MyPayment(payment);
		payments.add(pay);
		stateChanged();
		
	}
	
	
	//-----------------------------SCHEDULER--------------------------------
	protected boolean pickAndExecuteAnAction() {
		
		if (!orders.isEmpty()){
			DeliverOrder(orders.get(0));
			return true;
		}
		
		if (!payments.isEmpty()){
			DeliverPayment(payments.get(0));
			return true;
		}
		
		
		return false;
	}

	
	//-----------------------------ACTIONS--------------------------------
	private void DeliverOrder(BusinessOrder order){
		
	}
	
	private void DeliverPayment(MyPayment payment){
		
	}
	
	
	//-----------------------------UTILITIES--------------------------------
	private class MyPayment {
		int amount;
	
		MyPayment(int payment){
			amount = payment;
		}
	}
	
}
