package bank.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import bank.BankCustomerRole;

public class BankCustomerGui implements Gui{

	private BankCustomerRole role = null;
	private boolean isPresent = false;
	private String dialogue = "";


	//RestaurantGui gui;//TODO bank Gui?

	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToTellerWindow, LeaveBank};
	private Command command=Command.noCommand;
	//private boolean hasFood = false;//want hasMoney?


	public static final int BankCustomerWidth = 20;
	public static final int BankCustomerHeight = 20;
	public static final int xDoor = -40;
	public static final int yDoor = 100;
	

	public BankCustomerGui(BankCustomerRole bcr/*, RestaurantGui gui*/){ 
		role = bcr;
		xPos = xDoor;
		yPos = yDoor;
		xDestination = xDoor;
		yDestination = yDoor;
		//this.gui = gui;//TODO figure out the Gui!
	}

	public void updatePosition(int x, int y) {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;

		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToTellerWindow) role.msgAtDestination();//release a semaphore?
			else if (command==Command.LeaveBank) {
				role.msgAtDestination();
				//role.msgAnimationFinishedLeaveRestaurant();//set this role.
				//System.out.println("about to call gui.setCustomerEnabled(agent);");
				//isHungry = false;//carrying money again?
				//gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, BankCustomerWidth, BankCustomerHeight);
//		if (hasFood) {
//			g.setColor(Color.WHITE);//plate
//			g.fillRect(xPos+BankCustomerWidth/2-5, yPos+BankCustomerHeight-5, BankCustomerWidth/2+10, BankCustomerHeight/2+10);
//			if (!agent.doneEating){
//				g.setColor(food);//TODO change color of stuff
//				g.fillRect(xPos+BankCustomerWidth/2, yPos+BankCustomerHeight, BankCustomerWidth/2, BankCustomerHeight/2);
//			}
//        }
		g.setColor(Color.BLACK);
		g.drawString(dialogue, xPos, yPos);
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setDialogue(String dial) {
		dialogue = dial;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}
	
	public void DoGoToTellerWindow(int windowX, int windowY) {
		xDestination = windowX;
		yDestination = windowY;
		command = Command.GoToTellerWindow;
	}
	
	public void DoGoToSpot(int x, int y) {
		xDestination = x;
		yDestination = y;
	}

	public void DoExitBank() {
		xDestination = xDoor;
		yDestination = yDoor;
		command = Command.LeaveBank;
	}
}
