package market.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import market.MarketCustomerRole;
import public_Gui.Gui;

public class MarketCustomerGui implements Gui{

	MarketCustomerRole role;
	MarketAnimation animation;
	
	private final int panelX = 400;
	private final int panelY = 500;
	
	private final int itemDropX = 190;
	private final int itemDropY = 120;
	
	private final int cashierX = 60;
	private final int cashierY = 90;
	
	private final int exitX = 190;
	private final int exitY = 55;
	
	//default start position
	private final int xInitial = 190;
	private final int yInitial = -40;
	 private int xPos = 190, yPos = -40;
	 private int xDestination = 190, yDestination = -40;
	 
	 private boolean gotToDestination = true;
	 
	public MarketCustomerGui(MarketCustomerRole r){
		role = r;
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

	        if (!gotToDestination){
	        	if (xPos == xDestination && yPos == yDestination && !(xDestination == itemDropX && yDestination == itemDropY) && !(xDestination == xInitial && yDestination == yInitial))
	        	{
	        		role.msgAtDestination();
	        		gotToDestination = true;
	        	}
	        }
	}


	public void draw(Graphics2D g) {
		 g.setColor(Color.GREEN);
	     g.fillRect(xPos, yPos, 20, 20);
		
	}

	public void DoGoToHost(){
	
		gotToDestination = false;
		xDestination = panelX -82;
		yDestination = 90;
	}
	
	public void DoGoToItemDrop(){
		xDestination = itemDropX;
		yDestination = itemDropY;
	}
	
	public void DoGoToCashier(){
		gotToDestination = false;
		xDestination = cashierX;
		yDestination = cashierY;
	}

	public void DoGoToExit(){
		gotToDestination = false;
		xDestination = exitX;
		yDestination = exitY;
	}
	
	public void DoExitRestaurant(){

		xDestination = xInitial;
		yDestination = yInitial-30;
	}
	public boolean isPresent() {
		return true;
	}

	public void setAnimation(MarketAnimation ma){
		animation = ma;
	}
}
