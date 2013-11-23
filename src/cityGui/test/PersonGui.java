package cityGui.test;


import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.table.TableStringConverter;

import cityGui.CityComponent;
import cityGui.CityPanel;
import cityGui.SimCityGui;
import person.PersonAgent;
import public_Gui.Gui;

public class PersonGui extends CityComponent implements Gui {

    private PersonAgent agent;

    SimCityGui gui;
    
    private int xPos = 20, yPos = 29;//default waiter position
    private int xDestination = 395, yDestination = 29;//default start position
    private int crossWalk = 300;
    public int cWalk1i = 161;
    private int cWalk2i = 429;
    private int cWalk3i = 429;
    private int cWalk4i = 161;
    private int cWalk1o = 69;
    private int cWalk2o = 521;
    private int cWalk3o = 521;
    private int cWalk4o = 69;
    private int xRand;
    private int yRand;
    
    private boolean startPosition = true;
    private boolean hasArrived = false;
    private boolean readyToGoInnerSidewalk = false;
    private boolean readyToGoOuterSidewalk = false;
    private boolean choseRand = false;
    
    private Semaphore crossingStreet = new Semaphore(0,true);
    
    public PersonGui(PersonAgent agent) {
        this.agent = agent;
    }
    
    public PersonGui(PersonAgent c, SimCityGui gui){ //HostAgent m) {
		super(250, 50, Color.blue, "Dude");
    	agent = c;
		xPos = 200;
		yPos = 50;
		xDestination = 400;
		yDestination = 300;
		this.gui = gui;
		rectangle = new Rectangle(250, 50, 10, 10);
		
	}
	public void updatePosition() {
		
		if (readyToGoInnerSidewalk) {
			if (b1(getXPos(),getYPos())) {
				System.out.println("Current value: " + cWalk1i);
				System.out.println("YPos: " + getYPos());
				goVertical(getYPos(),cWalk1i);
				if (getXPos() == crossWalk && getYPos() == cWalk1i) {
					System.out.println("YUP");
    				readyToGoInnerSidewalk = false;
    			}
			}
			if (b2(getXPos(),getYPos())) {
				goHorizontal(getXPos(),cWalk2i);
			}
			if (b3(getXPos(),getYPos())) {
				goVertical(getYPos(),cWalk3i);
			}
			if (b4(getXPos(),getYPos())) {
				goHorizontal(getXPos(),cWalk4i);
			}
		}
//		
//		if (readyToGoOuterSidewalk) {
//			if (b1(getXPos(),getYPos())) {
//				goVertical(getYPos(),cWalk1o);
//			}
//			if (b2(getXPos(),getYPos())) {
//				goHorizontal(getXPos(),cWalk2o);
//			}
//			if (b3(getXPos(),getYPos())) {
//				goVertical(getYPos(),cWalk3o);
//			}
//			if (b4(getXPos(),getYPos())) {
//				goHorizontal(getXPos(),cWalk4o);
//			}
//		}
		
    	if (os1(getXPos(),getYPos())) {
    		if (is(xDestination,yDestination)) {
    			System.out.println("YA");
    			goHorizontal(getXPos(), crossWalk);
    			goVertical(getYPos(), cWalk1o);
    			if (getXPos() == crossWalk && getYPos() == cWalk1o && readyToGoInnerSidewalk == false) {
    				readyToGoInnerSidewalk = true;
    				crossWalk1i();
    			}
    			if (readyToGoInnerSidewalk) {
    				goVertical(getYPos(), cWalk1i);
    			}
    		}
        	if (os2(xDestination,yDestination)) {
        		
        	}
        	if (os3(xDestination,yDestination)) {
        		
        	}
        	if (os4(xDestination,yDestination)) {
        		
        	}   		
    	}
//    	if (os2(getXPos(),getYPos())) {
//    		if (is(xDestination,yDestination)) {
//    			goVertical(getYPos(), crossWalk);
//    			goHorizontal(getXPos(), crossWalk2o);
//    		}
//        	if (os2(xDestination,yDestination)) {
//        		
//        	}
//        	if (os3(xDestination,yDestination)) {
//        		
//        	}
//        	if (os4(xDestination,yDestination)) {
//        		
//        	}   		
//    	}
//    	if (os3(getXPos(),getYPos())) {
//    		if (is(xDestination,yDestination)) {
//    			goHorizontal(getXPos(), crossWalk);
//    			goVertical(getYPos(), crossWalk3o);
//    		}
//        	if (os2(xDestination,yDestination)) {
//        		
//        	}
//        	if (os3(xDestination,yDestination)) {
//        		
//        	}
//        	if (os4(xDestination,yDestination)) {
//        		
//        	}   		
//    	}
//    	if (os4(getXPos(),getYPos())) {
//    		if (is(xDestination,yDestination)) {
//    			goVertical(getYPos(), crossWalk);
//    			goHorizontal(getXPos(), crossWalk4o);
//    		}
//        	if (os2(xDestination,yDestination)) {
//        		
//        	}
//        	if (os3(xDestination,yDestination)) {
//        		
//        	}
//        	if (os4(xDestination,yDestination)) {
//        		
//        	}   		
//    	}
   
    	if (is1(getXPos(),getYPos())) {
    		if (is2(xDestination,yDestination)) {
    			if (choseRand == false) {
    				xRand = xDestination += (Math.random() * 29);
    				choseRand = true;
    			}
    			goHorizontal(getXPos(),xRand);
    			if (getXPos() == xRand && choseRand == true) {
    				goVertical(getXPos(),yDestination);
    				if (getYPos() == yDestination) {
    					goHorizontal(xDestination,yDestination);
    				}
    			}
    			
    		}
    		if (is4(xDestination,yDestination)) {
    			goHorizontal(getXPos(),xDestination);
    			if (getXPos() == xDestination) {
    				
    			}
    		}
        	if (os2(xDestination,yDestination)) {
        		
        	}
        	if (os3(xDestination,yDestination)) {
        		
        	}
        	if (os4(xDestination,yDestination)) {
        		
        	}   		
    	}
    	if (is2(getXPos(),getYPos())) {
    		if (is2(xDestination,yDestination)) {
    			goVertical(getXPos(),yDestination);
				if (getYPos() == yDestination) {
					goHorizontal(xDestination,yDestination);
				}
    		}
        	if (os2(xDestination,yDestination)) {
        		
        	}
        	if (os3(xDestination,yDestination)) {
        		
        	}
        	if (os4(xDestination,yDestination)) {
        		
        	}   		
    	}
//    	if (is3(getXPos(),getYPos())) {
//    		if (is(xDestination,yDestination)) {
//    			
//    		}
//        	if (os2(xDestination,yDestination)) {
//        		
//        	}
//        	if (os3(xDestination,yDestination)) {
//        		
//        	}
//        	if (os4(xDestination,yDestination)) {
//        		
//        	}   		
//    	}
//    	if (is4(getXPos(),getYPos())) {
//    		if (is(xDestination,yDestination)) {
//    			
//    		}
//        	if (os2(xDestination,yDestination)) {
//        		
//        	}
//        	if (os3(xDestination,yDestination)) {
//        		
//        	}
//        	if (os4(xDestination,yDestination)) {
//        		
//        	}   		
//    	}
//    	
//
//        if ((xPos == xDestination) && (yPos == yDestination) && (hasArrived == false) && isAtCookingStation() && (!isAtPlateStation())) {
//        	agent.msgAtCookingStation();        
//        }
//        if (xPos == xDestination && yPos == yDestination && (hasArrived == false) && isAtPlateStation() && (!isAtCookingStation())) {
//        	agent.msgAtPlateStation();
//        }
    }

	
	public boolean isAtPlateStation() {
		if (xPos == 395 && yPos == 30) {
			return true;
		}
		return false;
	}
	
	public boolean isAtCookingStation() {
		if ((xPos == 385) && (yPos == 0)) {
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
	
    public void paint(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, 20, 20);
    }    	

    public boolean isPresent() {
        return true;
    }
    
    public void DoGoToCookingStation() {
    	xDestination = 385;
    	yDestination = 0;
    }
    
	public void DoGoToPlateStation() {
		xDestination = 395;
    	yDestination = 30;
	}

    public int getXPos() {
        return rectangle.x;
    }

    public int getYPos() {
        return rectangle.y;
    }
    
    public boolean os1(int x, int y) {
    	if (y >= 0 && y <= 70) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean os2(int x, int y) {
    	if ((x >= 520 && x <= 600) && (y >= 80 && y <= 520)) {
    		return true;
    	}
    	else return false;
    }
	
    public boolean os3(int x, int y) {
    	if (y >= 520 && y <= 600) {
    		return true;
    	}
    	else return false;
	}
	
    public boolean os4(int x, int y) {
    	if ((x >= 0 && x <= 80) && (y >= 80 && y <= 520)) {
    		return true;
    	}
    	else return false;
	}
   
    public boolean is1(int x, int y) {
    	if (((x >= 160 && x <= 440) && (y >= 160 && y <= 200)) || ((x >= 200 && x <= 400) && (y > 200 && y < 240))) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean is2(int x, int y) {
    	if ((x >= 400 && x <= 440 && y >= 200 && y <= 400) || (x >= 360 && x <= 440 && y >= 240 && y <= 360)) {
    		return true;
    	}
    	else return false;
    }
	
    public boolean is3(int x, int y) {
    	if ((x >= 160 && x <= 440 && y >= 400 && y <= 440) || (x >= 200 && x <= 400 && y >= 360 && y < 400)) {
    		return true;
    	}
    	else return false;
	}
	
    public boolean is4(int x, int y) {
    	if ((x >= 160 && x <= 200 && y >= 200 && y <= 400) || (x >= 160 && x <= 240 && y >= 240 && y <= 360)) {
    		return true;
    	}
    	else return false;
	}
    
    public boolean is(int x, int y) {
    	if (is1(x,y) || is2(x,y) || is3(x,y) || is4(x,y)) {
    		return true;
    	}
    	return false;
    }
    
    public boolean b1(int x, int y) {
    	if (getXPos() >= 290 && getXPos() <= 310 && getYPos() >= 40 && getYPos() <= 190) {
    		return true;
    	}
    	return false;
    }
    public boolean b2(int x, int y) {
    	if (getYPos() >= 290 && getYPos() <= 310 && getXPos() >= 400 && getXPos() <= 550) {
    		return true;
    	}
    	return false;
    }
    public boolean b3(int x, int y) {
    	if (getXPos() >= 290 && getXPos() <= 310 && getYPos() >= 400 && getYPos() <= 550) {
    		return true;
    	}
    	return false;
    }
    public boolean b4(int x, int y) {
    	if (getYPos() >= 290 && getYPos() <= 310 && getXPos() >= 40 && getXPos() <= 190) {
    		return true;
    	}
    	return false;
    }    
    
    public boolean os(int x, int y) {
    	if (os1(x,y) || os2(x,y) || os3(x,y) || os4(x,y)) {
    		return true;
    	}
    	return false;
    }
    
    public void goHorizontal(int x, int xDest) {
		if (rectangle.x < xDest)
            rectangle.x++;
        else if (rectangle.x > xDest)
            rectangle.x--;
    }
    
    public void goVertical(int y, int yDest) {
        if (rectangle.y < yDest)
            rectangle.y++;
        else if (rectangle.y > yDest)
            rectangle.y--;
    }
    
    private void crossWalk1o() {   	
    	cWalk1o =  (int) (69 - (Math.random() * 29));
    }
    private void crossWalk2o() {
    	cWalk2o = (int) (521 + (Math.random() * 29));
    }
    private void crossWalk3o() {
    	cWalk3o = (int) (521 + (Math.random() * 29));
    }
    private void crossWalk4o() {
    	cWalk4o = (int) (69 - (Math.random() * 29));
    }
    public void crossWalk1i() {
    	cWalk1i = (int) (161 + (Math.random() * 29));
    	System.out.println("New value: " + cWalk1i);
    }
    public void crossWalk2i() {
    	cWalk2i = (int) (429 - (Math.random() * 29));
    }
    private void crossWalk3i() {
    	cWalk3i = (int) (429 - (Math.random() * 29));
    }
    private void crossWalk4i() {
    	cWalk4i = (int) (161 + (Math.random() * 29));
    }

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
    
}
