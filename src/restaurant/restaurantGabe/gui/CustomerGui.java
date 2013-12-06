package restaurant.restaurantGabe.gui;

import restaurant.restaurantGabe.CustomerRole;
//import restaurant.restaurantGabe.OldHostAgent;

import java.awt.*;

public class CustomerGui implements Gui{

	private CustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;
	
	static private int goneX = -40;
	static private int goneY = -40;

	//private HostAgent host;
	RestaurantGui gui;
	
	String food = null;
	
	private int waitingSpot;
	
	private WaiterGui tempWaiter = null;

	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, LeaveRestaurant};
	private Command command=Command.noCommand;

	public static final int[] xTables = {200,300,400};
	public static final int[] yTables = {250,250,250};

	public CustomerGui(CustomerRole c, RestaurantGui gui){ //HostAgent m) {
		agent = c;
		xPos = -40;
		yPos = -40;
		xDestination = -40;
		yDestination = -40;
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

		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat){
				agent.msgAnimationFinishedGoToSeat();
				tempWaiter.atTable();
			}
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				gui.updateList(agent);
				//System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				//gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
	}

	static int customer_width = 20;
	static int customer_height = 20;
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, customer_width, customer_height);
		if(food!=null){
			g.setColor(Color.black);
			g.drawString(food, xPos, yPos+customer_height/2);
		}
	}
	
	public void DoGoToRestaurant(){
		
		for(int i = 0;i<gui.numWaitingSpots;++i){
			if(gui.waitingSpots[i]){
				this.xDestination = 20;
				this.yDestination = 70 + 30*i;
				gui.waitingSpots[i] = false;
				this.waitingSpot = i;
				return;
			}
		}
		
		this.xDestination = -20;
		this.yDestination = -20;
		this.waitingSpot = -1;
		
		//gui.numWaitingCusts++;
	}

	public boolean isPresent() {
		return isPresent;
	}
	
	public void setHungry() {
		isHungry = true;
		agent.msgGotHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	public void DoGoToSeat(int seatnumber) {//later you will map seatnumber to table coordinates.
		gui.numWaitingCusts--;
		if(this.waitingSpot!=-1){
			gui.waitingSpots[this.waitingSpot] = true;
		}
		
		xDestination = xTables[seatnumber];
		yDestination = yTables[seatnumber];
		command = Command.GoToSeat;
		//System.out.println("I'm supposed to be sitting");
		//gui.updateList(agent);
	}
	
	public void DoGoToSeatDirected(WaiterGui wGui,int xDest, int yDest) {
		if(this.waitingSpot!=-1){
			gui.waitingSpots[this.waitingSpot] = true;
		}
		
		xDestination = xDest;
		yDestination = yDest;
		command = Command.GoToSeat;
		tempWaiter = wGui;
	}
	
	public void setFood(String food){
		this.food = food;
	}

	public void DoExitRestaurant() {
		xDestination = goneX;
		yDestination = goneY;
		command = Command.LeaveRestaurant;
		//gui.updateList(agent);
	}
}