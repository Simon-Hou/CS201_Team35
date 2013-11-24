package market.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import market.MarketCustomerRole;
import public_Gui.Gui;

public class MarketCustomerGui implements Gui{

	MarketCustomerRole role;
	
	private final int panelX = 400;
	private final int panelY = 500;
	
	//default start position
	 private int xPos = 190, yPos = 50;
	 private int xDestination = 190, yDestination = 80;
	 
	 
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

	        if (xPos == xDestination && yPos == yDestination )
	        {
	           role.msgAtDestination();
	        }
		
	}


	public void draw(Graphics2D g) {
		 g.setColor(Color.GREEN);
	     g.fillRect(xPos, yPos, 20, 20);
		
	}

	public void DoGoToHost(){
	
		
		xDestination = panelX -82;
		yDestination = 90;
	}

	public boolean isPresent() {
		return true;
	}

}
