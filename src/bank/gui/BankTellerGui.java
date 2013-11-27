package bank.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import person.PersonAgent;
import bank.BankTellerRole;


public class BankTellerGui implements Gui{
	
	private BankTellerRole role = null;
	private boolean isPresent = false;
	private String dialogue = "";
	
	ImageIcon currentImage;
	//RestaurantGui gui;//TODO bank Gui?

	public int xPos;
	public int yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSpot, GoToSafe, LeaveBank};
	private Command command=Command.noCommand;
	//private boolean hasFood = false;//want hasMoney?


	public static final int BankTellerWidth = 20;
	public static final int BankTellerHeight = 20;
	public static final int xDoor = -40;
	public static final int yDoor = 100;
	public static final int xSafe = 100;
	public static final int ySafe = 100;
	public int xSpot = 0;
	public int ySpot = 0;
	
	private int spriteCounter = 6;
	
	public BankTellerGui(BankTellerRole btr) {
		role = btr;
		xPos = xDoor;
		yPos = yDoor;
//		xDestination = xDoor;
//		yDestination = yDoor;
		xDestination = 10;
		yDestination = 10;
		currentImage = ((PersonAgent)this.role.person).downSprites.get(0);
	}
	
	public void updatePosition(int x, int y) {
		if (xPos < xDestination) {
			xPos++;
			spriteCounter++;
			if (spriteCounter % 6 == 0) {
				currentImage = ((PersonAgent)this.role.person).rightSprites.get(spriteCounter % ((PersonAgent)this.role.person).rightSprites.size());
			}
		}
		else if (xPos > xDestination) {
			xPos--;
			spriteCounter++;
			if (spriteCounter % 6 == 0) {
				currentImage = ((PersonAgent)this.role.person).leftSprites.get(spriteCounter % ((PersonAgent)this.role.person).leftSprites.size());
			}			
		}
		if (yPos < yDestination) {
			yPos++;
			spriteCounter++;
			if (spriteCounter % 6 == 0) {
				currentImage = ((PersonAgent)this.role.person).downSprites.get(spriteCounter % ((PersonAgent)this.role.person).downSprites.size());
			}
		}
		else if (yPos > yDestination) {
			yPos--;
			spriteCounter++;
			if (spriteCounter % 6 == 0) {
				System.out.println(xPos % ((PersonAgent)this.role.person).upSprites.size());
				System.out.println(((PersonAgent)this.role.person).upSprites.size());
				currentImage = ((PersonAgent)this.role.person).upSprites.get(spriteCounter % ((PersonAgent)this.role.person).upSprites.size());
			}
		}
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSpot) {
				role.msgAtDestination();//release a semaphore?
			}
			else if (command == Command.GoToSafe) {
				role.msgAtDestination();
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
		g.setColor(Color.CYAN);
		//g.fillRect(xPos, yPos, BankTellerWidth, BankTellerHeight);
	    g.drawImage(currentImage.getImage(),xPos,yPos,BankTellerWidth,BankTellerWidth,null);
		g.setColor(Color.BLACK);
		//g.drawString(dialogue, xPos, yPos);
	}
	
	public void DoGoToPosition() {//TODO figure out how to get the proper thing here......
		xDestination = xSpot;
		yDestination = ySpot;
		command = Command.GoToSpot;
	}
	
	public void DoExitBank() {
		xDestination = xDoor;
		yDestination = yDoor;
		command = Command.LeaveBank;
	}
	
	public boolean isPresent() {
		return true;
	}

	public void initialSpot(int xPos2, int yPos2) {
		xSpot = xPos2;
		ySpot = yPos2;
		xDestination = xSpot;
		yDestination = ySpot;
		command = Command.GoToSpot;
	}
}
