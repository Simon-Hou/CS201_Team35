package market;

public class OrderItem {
	
	int quantityOrdered;
	int quantityReceived;
	String choice;
	
	OrderItem(int ordered, int received, String item){
		quantityOrdered = ordered;
		quantityReceived = received;
		choice = item;
	}
}
