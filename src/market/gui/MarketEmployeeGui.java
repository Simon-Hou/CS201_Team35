package market.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import public_Gui.Gui;
import market.MarketEmployeeRole;

public class MarketEmployeeGui implements Gui{

	MarketEmployeeRole role;
	
	private final int panelX = 400;
	private final int panelY = 500;
	
	private final int itemDropX = 190;
	private final int itemDropY = 165;
	
	private final int xCashier = 15;
	private final int yCashier = 150;
	
	private final int xDock = 30;
	private final int yDock = 420;
	
	//default start position
	private final int xInitial = 370;
	private final int yInitial = 200;
	 private int xPos = 370, yPos = 200;
	 private int xDestination = 370, yDestination = 200;

	 private boolean gotToDestination = true;
	 
	 
	public MarketEmployeeGui(MarketEmployeeRole e){
		role = e;
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
	        	if (xPos == xDestination && yPos == yDestination && !(xDestination == xInitial && yDestination == yInitial) )
	        	{
	        		role.msgAtDestination();
	        		gotToDestination = true;
	        	}
	        }
		
	}

	@Override
	public void draw(Graphics2D g) {
		 g.setColor(Color.BLUE);
	     g.fillRect(xPos, yPos, 20, 20);
		
	}
	
	public void DoGetItem(String i){
		
		
		switch(i){
		case "Steak": xDestination = 245; yDestination = 315; break;
		case "Chicken":  xDestination = 245; yDestination = 245; break;
		case "Pizza": xDestination = 115; yDestination = 245; break;
		case "Salad": xDestination = 115; yDestination = 315; break;
		case "Car": xDestination = 115; yDestination = 385; break;
		case "Other": xDestination = 245; yDestination = 385; break;
		default: System.err.println("We don't have that item!"); return;
		}
		gotToDestination = false;
	}
	
	public void DoGiveCustomerItems(){
		xDestination = itemDropX;
		yDestination = itemDropY;
		gotToDestination = false;
	}
	
	public void DoGoHomePosition(){
		xDestination = xInitial;
		yDestination = yInitial;
	}
	
	public void DoGoToCashier(){
		xDestination = xCashier;
		yDestination = yCashier;
		gotToDestination = false;
	}
	
	public void DoGoToDock(){
		xDestination = xDock;
		yDestination = yDock;
		gotToDestination = false;
	}

	@Override
	public boolean isPresent() {
		return true;
	}
}
