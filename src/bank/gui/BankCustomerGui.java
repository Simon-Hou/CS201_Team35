package bank.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import bank.BankCustomerRole;
import person.PersonAgent;

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
	public static final int xDoor = 40;
	public static final int yDoor = 100;

	ImageIcon currentImage;

	public BankCustomerGui(BankCustomerRole bcr/*, RestaurantGui gui*/){ 
		isPresent = true;
		role = bcr;
		xPos = xDoor;
		yPos = yDoor;
		xDestination = xDoor;
		yDestination = yDoor;
		//this.gui = gui;//TODO figure out the Gui!
	}

	public void updatePosition(int x, int y) {
		if (xPos < xDestination) {
			xPos++;
			currentImage = ((PersonAgent)this.role.person).rightSprites.get(xPos % ((PersonAgent)this.role.person).rightSprites.size());
		}
		else if (xPos > xDestination) {
			xPos--;
			currentImage = ((PersonAgent)this.role.person).leftSprites.get(xPos % ((PersonAgent)this.role.person).leftSprites.size());
		}
		if (yPos < yDestination) {
			yPos++;
			currentImage = ((PersonAgent)this.role.person).downSprites.get(yPos % ((PersonAgent)this.role.person).downSprites.size());
		}
		else if (yPos > yDestination) {
			yPos--;
			currentImage = ((PersonAgent)this.role.person).upSprites.get(yPos % ((PersonAgent)this.role.person).upSprites.size());
		}
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToTellerWindow) {
				role.msgAtDestination();//release a semaphore?
			}
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
		//g.fillRect(xPos, yPos, BankCustomerWidth, BankCustomerWidth);
		g.drawImage(currentImage.getImage(),xPos,yPos,BankCustomerWidth,BankCustomerWidth,null);
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
