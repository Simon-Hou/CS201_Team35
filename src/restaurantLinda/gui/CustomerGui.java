package restaurantLinda.gui;

import restaurantLinda.CustomerRole;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class CustomerGui implements Gui{

	private CustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	RestaurantGui gui;

	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, GoToCashier, LeaveRestaurant};
	private Command command=Command.noCommand;

	private int personSize=AnimationPanel.PERSONSIZE;
	
	private List<MyImage> carriedItems = new ArrayList<MyImage>();
	private String bufferText;
    private Dimension bufferSize;
    public final String path="../../images/";

	public CustomerGui(CustomerRole c, RestaurantGui gui){ //HostAgent m) {
		agent = c;
		xPos = -2*personSize;
		yPos = -2*personSize;
		xDestination = xPos;
		yDestination = yPos;
		//maitreD = m;
		this.gui = gui;
	}

	public void updatePosition() {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;
		
		for (MyImage icon: carriedItems)
        	icon.updatePosition(xPos,yPos);

		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
			else if (command==Command.GoToCashier){
				agent.msgAnimationFinishedGoToCashier();
			}
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				bufferText = null;
				carriedItems.clear();
				//System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, personSize, personSize);
		
		if (bufferText!=null){
			g.setColor(Color.BLACK);
			g.drawString(bufferText, xPos, yPos+15);
		}
			
		for (MyImage icon: carriedItems)
        	icon.draw(g);
		
	}

	public boolean isPresent() {
		return isPresent;
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
	
	public void DoWaitInLine(int position){
		int limit = (AnimationPanel.WINDOWX-300)/(personSize+5);
        if (position>=limit){
        	position%=limit;
        }
        
        xDestination = 50+(personSize+5)*position;
        yDestination = 0;
	}
	
	public void DoMoveInLine(){
		xDestination -=personSize+5;
	}
	
	public void DoWaitForWaiter(){
		yDestination+=personSize+10;
	}

	public void DoGoToSeat(int xLoc, int yLoc) {
		xDestination = xLoc;
		yDestination = yLoc;
		command = Command.GoToSeat;
	}
	
	public void DoGoToCashier(){
		xDestination = 0;
		yDestination = 100+personSize;
		command = Command.GoToCashier;
	}

	public void DoExitRestaurant() {
		xDestination = -2*personSize;
		yDestination = -2*personSize;
		command = Command.LeaveRestaurant;
	}
	
	public void DoTalk(String text){
		bufferText=text;
	}
	
	public void DoReceiveFood(String choice){
		bufferText=null;
		carriedItems.add(new MyImage(choice,xPos,yPos));
	}
	
	public void DoFinishFood(){
		carriedItems.clear();
		bufferText=null;
	}
	
	//Utilities
	public Point getDestination(){
		return new Point(xDestination, yDestination);
	}
	
	public Point getLocation(){
		return new Point(xPos,yPos);
	}
}
