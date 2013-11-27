package interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import role.Role;
import util.Bus;
import util.BusStop;
import util.Loc;

public interface Person {
	
//	public List<ImageIcon> upSprites = null;
//	public List<ImageIcon> downSprites = null;
//	public List<ImageIcon> leftSprites = null;
//	public List<ImageIcon> rightSprites = null;

	public abstract void msgStateChanged();
	
	public abstract void msgBusAtStop(Bus b,BusStop stop);
	public abstract void msgCarArrivedAtLoc(Loc destination);
	
	public abstract void addToWallet(int amount);
	public abstract void takeFromWallet(int amount);
	public abstract int getWalletAmount();
	
	public abstract void addFoodToBag(String type, int quantity);
	
	public abstract void addToAccount(int accNumber,int amount);
	public abstract void takeFromAccount(int accNumber,int amount);
	
	public abstract void createAccount(int accountNumber,int amount,String name,String passWord);
	
	public abstract void addLoan(int accountNumber,int cash,int loanNumber);
	
	
	public abstract void msgThisRoleDone(Role role);

	public abstract void msgDoneEating();
	
	public abstract String getName();
	
	public abstract void putInBag(String item,int amount);
	
	
}
