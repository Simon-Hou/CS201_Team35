package restaurantLinda.interfaces;

public interface Host {
	
	public abstract void msgIWantFood(Customer cust);
	
	public abstract void msgIWillWait(Customer cust, boolean wait);

	public abstract void msgCustomerLeaving(Waiter waiter, Customer cust, int t);
	
	public abstract void msgIWantBreak(Waiter waiter);
	
	public abstract void msgOffBreak(Waiter waiter);

}
