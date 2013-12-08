package restaurant.restaurantYocca.gui;

import public_Gui.Gui;
import restaurant.restaurantYocca.CustomerRole;
import restaurant.restaurantYocca.HostRole;
import restaurant.restaurantYocca.RestaurantYocca;
import interfaces.restaurantYocca.Customer;

import java.awt.*;

public class CustomerGui implements Gui {
    
	public CustomerRole agent;
	private boolean isPresent = true;
	private boolean isHungry = false;
	private boolean hasArrived = false;
    
//	RestaurantGui gui;
	RestaurantYocca restaurant;
    
	private int cashierX = 230;
	private int cashierY = 40;
	private int homeX;
	private int homeY;
	private int xPos, yPos;
	private int xDestination, yDestination;
	private String foodString = "";
	private String statusString = "";
	private enum Command {noCommand, GoToSeat, LeaveRestaurant};
	private Command command=Command.noCommand;

	
	public CustomerGui(CustomerRole agent) {
        this.agent = agent;
    }    
    
	public int xTable;
	public int yTable;
	
	public CustomerGui(CustomerRole c, RestaurantYocca rYocca){ //HostAgent m) {
		agent = c;
		xPos = 0;
		yPos = 300;
		restaurant = rYocca;
		try {
			yDestination = 30 + rYocca.host.waitingCustomers.size() * 25;
		} catch (NullPointerException e) {
			yDestination = 20;
			System.out.println(yDestination);
		}
		xDestination = 5;
	}
  
//	public RestaurantGui getGui() {
//		return gui;
//	}

	public void updatePosition() {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;
        
		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;
        
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				agent.p.setTiredLevel(20);
				agent.leaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				//gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
		if (xPos == xDestination && yPos == yDestination && isAtCashier() && (hasArrived == false)){
			agent.msgAtCashier();   	
        } 		
		if (xPos == xDestination && yPos == yDestination && isAtWaitingArea() && (hasArrived == false)) {
        	agent.msgAtWaitingArea();   	
        } 
	}
    
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, 20, 20);
		g.setColor(Color.BLACK);
        g.drawString(foodString, xPos+5, yPos+15);
	}
    
	public boolean isPresent() {
		return isPresent;
	}
	
	public boolean isAtCashier() {
		if ((xPos == cashierX + 10) && (yPos == cashierY - 10)) {
			return true;
		}
		return false;
	}
	
	public boolean isAtWaitingArea() {
		if ((xPos == homeX) && (yPos == homeY)) {
			return true;
		}
		return false;
	}
	
	
	public void setHungry() {
		isHungry = true;
		agent.gotHungry();
		setPresent(true);
	}

	public boolean isHungry() {
		return isHungry;
	}
    
	public void setPresent(boolean p) {
		isPresent = p;
	}
	
	public boolean getArrived() {
		return hasArrived;
	}
	
	public void setArrived(boolean arrived) {
		hasArrived = arrived;
	}
	
	public int getTableXLocation(int tableNum) {
		int xLoc = 0;
		if (tableNum == 1) {
			xLoc = 100;
		}
		if (tableNum == 2) {
			xLoc = 200;
		}
		if (tableNum == 3) {
			xLoc = 300;
		}
		if (tableNum == 4) {
			xLoc = 400;
		}
		return xLoc;
	}
	
	public int getTableYLocation(int tableNum) {
		int yLoc = 0;
		if (tableNum == 1) {
			yLoc = 300;
		}
		if (tableNum == 2) {
			yLoc = 300;
		}
		if (tableNum == 3) {
			yLoc = 300;
		}
		if (tableNum == 4) {
			yLoc = 300;
		}
		return yLoc;
	}
	
	public void setFoodString(String s) {
		foodString = s;
	}
	
	public String getFoodString() {
		return foodString;
	}
    
	public void DoGoToSeat(int seatnumber, int xLoc, int yLoc) {//later you will map seatnumber to table coordinates.
		xTable = xLoc;
		yTable = yLoc;
		xDestination = xTable;
		yDestination = yTable;
		command = Command.GoToSeat;
	}
	
	public void DoGoToCashier() {
		xDestination = cashierX + 10;
		yDestination = cashierY - 10;
	}
	
	public void DoGoToWaitingArea() {
		yDestination = 30;
		xDestination = 5;
		homeX = 5;
		homeY = 30;
		System.out.println("YDestination: " + yDestination);
		System.out.println("Waiting Customer Size: " + restaurant.host.waitingCustomers.size() * 25);
	}
    
	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}

}
