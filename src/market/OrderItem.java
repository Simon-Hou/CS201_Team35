package market;

public class OrderItem {
	
	int quantityOrdered;
	int quantityReceived;
	String choice;
	

	public OrderItem( String choice, int quantity){
		this.choice = choice;
		this.quantityOrdered = quantity;
		quantityReceived = 0;
	}

	OrderItem(int ordered, int received, String item){
		quantityOrdered = ordered;
		quantityReceived = received;
		choice = item;

	}
}
