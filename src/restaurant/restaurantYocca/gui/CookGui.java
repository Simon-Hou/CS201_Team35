package restaurant.restaurantYocca.gui;


import public_Gui.Gui;
import restaurant.restaurantYocca.CookRole;
import restaurant.restaurantYocca.CustomerRole;
import restaurant.restaurantYocca.Table;
import restaurant.restaurantYocca.WaiterRole;
import restaurant.restaurantYocca.WaiterRole.MyCustomer;
import interfaces.restaurantYocca.Customer;
import interfaces.restaurantYocca.Waiter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.TableStringConverter;

public class CookGui implements Gui {

    private CookRole agent;

//    RestaurantGui gui;
    
    private int xPos = 345, yPos = 29;//default waiter position
    private int xDestination = 345, yDestination = 29;//default start position
    private boolean startPosition = true;
    private boolean hasArrived = false;
    private boolean onBreak = false;
    private String statusString = "";
    private String aString = "";
    private String bString = "";
    private String cString = "";
    private String dString = "";

    
    public CookGui(CookRole agent) {
        this.agent = agent;
		xPos = 345;
		yPos = 29;
		xDestination = 345;
		yDestination = 29;
    }
    
//    public CookGui(CookRole c, RestaurantGui gui){ //HostAgent m) {
//		agent = c;
//		xPos = 345;
//		yPos = 29;
//		xDestination = 345;
//		yDestination = 29;
//		this.gui = gui;
//		//gui.animationPanel.add(this);
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

        if ((xPos == xDestination) && (yPos == yDestination) && (hasArrived == false) && isAtCookingStation() && !isAtPlateStation() && !isOutOfRestaurant()) {
        	agent.msgAtCookingStation();        
        }
        if (xPos == xDestination && yPos == yDestination && (hasArrived == false) && isAtPlateStation() && !isAtCookingStation() && !isOutOfRestaurant()) {
        	agent.msgAtPlateStation();
        }
        if (xPos == xDestination && yPos == yDestination && (hasArrived == false) && isAtOrderStand() && !isAtCookingStation() && !isOutOfRestaurant()) {
        	agent.msgAtOrderStand();
        }
        if (xPos == xDestination && yPos == yDestination && (hasArrived == false) && isOutOfRestaurant() && !isAtCookingStation() && !isAtPlateStation()) {
        	agent.msgOutOfRetaurant();
        }
    }

	public boolean isAtOrderStand() {
		if (xPos == 395 && yPos == 30) {
			return true;
		}
		return false;
	}
	
	public boolean isAtPlateStation() {
		if (xPos == 370 && yPos == 30) {
			return true;
		}
		return false;
	}
	
	public boolean isAtCookingStation() {
		if ((xPos == 335) && (yPos == 0)) {
			return true;
		}
		return false;
	}
	
	public boolean isAtHome() {
		if ((xPos == 335) && (yPos == 0)) {
			return true;
		}
		return false;
	}
	
	public boolean isOutOfRestaurant() {
		if ((xPos == -20) && (yPos == -20)) {
			return true;
		}
		return false;
	}

	public boolean getArrived() {
		return hasArrived;
	}
	
	public void setArrived(boolean arrived) {
		hasArrived = arrived;
	}
	
	public boolean getStartPosition() {
		return startPosition;
	}
	
	public void setStatusString(String s) {
		statusString = s;
	}
	
	public String getStatusString() {
		return statusString;
	}
	
	public void setAString(String s) {
		aString = s;
	}
	
	public String getAString() {
		return aString;
	}
	
	public void setBString(String s) {
		bString = s;
	}
	
	public String getBString() {
		return bString;
	}
	
	public void setDString(String s) {
		aString = s;
	}
	
	public String getDString() {
		return aString;
	}
	
	public void setCString(String s) {
		bString = s;
	}
	
	public String getCString() {
		return bString;
	}
	
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString(aString, 315, 20);
        g.setColor(Color.WHITE);
        g.drawString(bString, 315, 10);
        g.setColor(Color.WHITE);
        g.drawString(cString, 305, 20);
        g.setColor(Color.WHITE);
        g.drawString(dString, 305, 10);
    }    	

    public boolean isPresent() {
        return true;
    }
    
    public void DoGoToCookingStation() {
    	xDestination = 335;
    	yDestination = 0;
    }
    
	public void DoGoToPlateStation() {
		xDestination = 370;
    	yDestination = 30;
	}
	
	public void DoGoToOrderStand() {
		xDestination = 395;
    	yDestination = 30;
	}
	
	public void DoGoToHomePosition() {
		xDestination = 345;
    	yDestination = 30;
	}

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setCook(CookRole c) {
    	agent = c;
    }

	public void DoLeaveRestaurant() {
		// TODO Auto-generated method stub
		
	}

}
