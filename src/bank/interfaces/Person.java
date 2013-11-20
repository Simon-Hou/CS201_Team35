package bank.interfaces;

import role.Role;

public interface Person {

	
	public abstract void msgStateChanged();
	
	public abstract void addToWallet(int amount);
	public abstract void takeFromWallet(int amount);
	
	public abstract void addToAccount(int accNumber,int amount);
	public abstract void takeFromAccount(int accNumber,int amount);
	
	public abstract void createAccount(int accountNumber,int amount,String name,String passWord);
	
	public abstract void addLoan(int accountNumber,int cash,int loanNumber);
	
	
	public abstract void msgThisRoleDone(Role role);
	
}
