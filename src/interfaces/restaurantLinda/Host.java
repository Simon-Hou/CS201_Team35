package interfaces.restaurantLinda;

import interfaces.Occupation;

public interface Host extends Occupation{
	
	public abstract void msgIWantFood(Customer cust);
	
	public abstract void msgIWillWait(Customer cust, boolean wait);

	public abstract void msgCustomerLeaving(Waiter waiter, Customer cust, int t);
	
	public abstract void msgIWantBreak(Waiter waiter);
	
	public abstract void msgOffBreak(Waiter waiter);

	public abstract void addTable(int size);

	public abstract void addWaiter(Waiter m);

	public abstract boolean isPresent();

}
