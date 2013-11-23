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
    private int crossWalk = 290;
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
    private boolean transitionDone = true;
    private boolean choseRand = false;
    
    private Semaphore crossingStreet = new Semaphore(0,true);
    
    public PersonGui(PersonAgent agent) {
        this.agent = agent;
    }
    
    public PersonGui(PersonAgent c, SimCityGui gui, int xPos, int yPos, int xDest, int yDest){ //HostAgent m) {
		super(xPos, yPos, Color.blue, "Dude");
    	agent = c;
		xDestination = xDest;
		yDestination = yDest;
		this.gui = gui;
		rectangle = new Rectangle(xPos, yPos, 10, 10);
		
	}
	public void updatePosition() {
		
		System.out.println(choseRand);
		if (readyToGoInnerSidewalk) {
			System.out.println("In ReadyToGoInnerSidewalk");
			if (b1(getXPos(),getYPos())) {
				goVertical(cWalk1i);
				if (getYPos() == cWalk1i) {
    				readyToGoInnerSidewalk = false;
    			}
			}
			if (b2(getXPos(),getYPos())) {
				goHorizontal(cWalk2i);
				if (getXPos() == cWalk2i) {
    				readyToGoInnerSidewalk = false;
    			}
			}
			if (b3(getXPos(),getYPos())) {
				goVertical(cWalk3i);
				if (getYPos() == cWalk3i) {
    				readyToGoInnerSidewalk = false;
    			}
			}
			if (b4(getXPos(),getYPos())) {
				goHorizontal(cWalk4i);
				if (getXPos() == cWalk4i) {
    				readyToGoInnerSidewalk = false;
    			}
			}
		}
		
		if (readyToGoOuterSidewalk) {
			System.out.println("In ReadyToGoOuterSidewalk");
			if (b1(getXPos(),getYPos())) {
				goVertical(cWalk1o);
				if (getYPos() == cWalk1o) {
    				readyToGoOuterSidewalk = false;
    			}
			}
			if (b2(getXPos(),getYPos())) {
				goHorizontal(cWalk2o);
				if (getXPos() == cWalk2o) {
    				readyToGoOuterSidewalk = false;
    			}
			}
			if (b3(getXPos(),getYPos())) {
				goVertical(cWalk3o);
				if (getYPos() == cWalk3o) {
    				readyToGoOuterSidewalk = false;
    			}
			}
			if (b4(getXPos(),getYPos())) {
				goHorizontal(cWalk4o);
				if (getXPos() == cWalk4o) {
    				readyToGoOuterSidewalk = false;
    			}
			}
		}
		
    	if (os1(getXPos(),getYPos()) && !readyToGoInnerSidewalk && !readyToGoOuterSidewalk) {
    		System.out.println("In os1");
			if (!transitionDone) {
    			goVertical(yRand);
    			if (getYPos() == yRand) {
    				transitionDone = true;
    			}
			}
        	if (os1(xDestination,yDestination) && transitionDone) {
        		goHorizontal(xDestination);
    			if (getXPos() == xDestination) {
    				goVertical(yDestination);
    			}
        	}
    		if (is(xDestination,yDestination) && transitionDone) {
    			goHorizontal(crossWalk);
    			goVertical(cWalk1o);
    			if (getXPos() == crossWalk && getYPos() == cWalk1o) {
    				readyToGoInnerSidewalk = true;
    				crossWalk1i();
    			}
    		}
        	if (os(xDestination,yDestination) && !os1(xDestination,yDestination) && transitionDone) {

        		if (choseRand == false) {
    				if (os2(xDestination,yDestination)) {
    					xRand = (int) (520 + (Math.random() * 29));
    				}
    				else xRand = (int) (70 - (Math.random() * 29));
    				choseRand = true;
    			}
    			else {
    				goHorizontal(xRand);
    				if (getXPos() == xRand) {
    					if (getYPos() == 80) {
        					choseRand = false;
    					}
    					goVertical(yDestination);
    				}	
    			}
        	}
    	}
    	if (os2(getXPos(),getYPos()) && !readyToGoInnerSidewalk && !readyToGoOuterSidewalk) {
    		System.out.println("In os2");
        	if (os2(xDestination,yDestination)) {
        		goVertical(yDestination);
    			if (getYPos() == yDestination) {
    				goHorizontal(xDestination);
    			}
        	}
    		if (is(xDestination,yDestination)) {
    			goVertical(crossWalk);
    			goHorizontal(cWalk2o);
    			if (getYPos() == crossWalk && getXPos() == cWalk2o) {
    				readyToGoInnerSidewalk = true;
    				crossWalk2i();
    			}
    		}
        	if (os(xDestination,yDestination) && !os2(xDestination,yDestination)) {
        		if (choseRand == false) {
    				if (os1(xDestination,yDestination)) {
    					yRand = (int) (70 - (Math.random() * 29));
    				}
    				else yRand = (int) (520 + (Math.random() * 29));
    				choseRand = true;
    			}
        		if (getYPos() == 81 || getYPos() == 519) {
    				choseRand = false;
    				transitionDone = false;
    			}	
    			goVertical(yRand);	
        	}
    	}
    	if (os3(getXPos(),getYPos()) && !readyToGoInnerSidewalk && !readyToGoOuterSidewalk) {
    		System.out.println("In os3");
			if (!transitionDone) {
    			goVertical(yRand);
    			if (getYPos() == yRand) {
    				transitionDone = true;
    			}
			}
        	if (os3(xDestination,yDestination) && transitionDone) {
        		goHorizontal(xDestination);
    			if (getXPos() == xDestination) {
    				goVertical(yDestination);
    			}
        	}
    		if (is(xDestination,yDestination) && transitionDone) {
    			goHorizontal(crossWalk);
    			goVertical(cWalk3o);
    			if (getXPos() == crossWalk && getYPos() == cWalk3o) {
    				readyToGoInnerSidewalk = true;
    				crossWalk3i();
    			}
    		}
        	if (os(xDestination,yDestination) && !os3(xDestination,yDestination) && transitionDone) {
        		if (choseRand == false) {
    				if (os2(xDestination,yDestination)) {
    					xRand = (int) (520 + (Math.random() * 29));
    				}
    				else xRand = (int) (70 - (Math.random() * 29));
    				choseRand = true;
    			}
    			else {
    				goHorizontal(xRand);
    				if (getXPos() == xRand) {
    					if (getYPos() == 520) {
        					choseRand = false;
    					}
    					goVertical(yDestination);
    				}	
    			}
        	}
    	}
    	if (os4(getXPos(),getYPos()) && !readyToGoInnerSidewalk && !readyToGoOuterSidewalk) {
    		System.out.println("In os4");
        	if (os4(xDestination,yDestination)) {
        		goVertical(yDestination);
    			if (getYPos() == yDestination) {
    				goHorizontal(xDestination);
    			}
        	}
    		if (is(xDestination,yDestination)) {
    			goVertical(crossWalk);
    			goHorizontal(cWalk4o);
    			if (getYPos() == crossWalk && getXPos() == cWalk4o) {
    				readyToGoInnerSidewalk = true;
    				crossWalk4i();
    			}
    		}
        	if (os(xDestination,yDestination) && !os4(xDestination,yDestination)) {
        		if (choseRand == false) {
    				if (os1(xDestination,yDestination)) {
    					yRand = (int) (70 - (Math.random() * 29));
    				}
    				else yRand = (int) (520 + (Math.random() * 29));
    				choseRand = true;
    			}
        		if (getYPos() == 81 || getYPos() == 519) {
    				choseRand = false;
    				transitionDone = false;
    			}	
    			goVertical(yRand);	
        	}
    	}
   
    	if (is1(getXPos(),getYPos()) && !readyToGoOuterSidewalk && !readyToGoInnerSidewalk) {
    		System.out.println("In is1");
			if (!transitionDone) {
    			goVertical(yRand);
    			if (getYPos() == yRand) {
    				transitionDone = true;
    			}
			}
        	if (is1(xDestination,yDestination) && transitionDone) {
        		goHorizontal(xDestination);
    			if (getXPos() == xDestination) {
    				goVertical(yDestination);
    				//atDestination
    			}
        	}
    		if ((os(xDestination,yDestination) || is(xDestination,yDestination)) && !is1(xDestination,yDestination) && !os1(xDestination,yDestination) && transitionDone) {
    			System.out.println("In is1's thing");
    			if (choseRand == false) {
    				if (is2(xDestination,yDestination) || os2(xDestination,yDestination)) {
    					xRand = (int) (400 + (Math.random() * 29));
    				}
    				else xRand = (int) (190 - (Math.random() * 29));
    				choseRand = true;
    				System.out.println("XRand: " + xRand);
    				System.out.println("XDest: " + xDestination);
    			}
    			else {
    				goHorizontal(xRand);
    				if (getXPos() == xRand) {
    					if (getYPos() == 200) {
        					choseRand = false;
    					}
    					goVertical(yDestination);
    				}	
    			}
    		}
    		else if (os1(xDestination,yDestination)) {
        		goHorizontal(crossWalk);
        		goVertical(cWalk1i);
        		if (getXPos() == crossWalk && getYPos() == cWalk1i) {
        			readyToGoOuterSidewalk = true;
        			crossWalk1o();
        		}
        	}
    	}

    	if (is2(getXPos(),getYPos()) && !readyToGoOuterSidewalk && !readyToGoInnerSidewalk) {
    		System.out.println("In is2");
        	if (is2(xDestination,yDestination)) {
        		goVertical(yDestination);
    			if (getYPos() == yDestination) {
    				goHorizontal(xDestination);
    				//atDestination
    			}
        	}
    		if ((os(xDestination,yDestination) || is(xDestination,yDestination)) && !is2(xDestination,yDestination) && !os2(xDestination,yDestination)) {
    			System.out.println(yRand);
    			if (choseRand == false) {
    				if (is3(xDestination,yDestination) ||os3(xDestination,yDestination)) {
    					yRand = (int) (400 + (Math.random() * 29));
    				}
    				else yRand = (int) (190 - (Math.random() * 29));
    				choseRand = true;
    			}
    			if (getYPos() == 201 || getYPos() == 399) {
    				choseRand = false;
    				transitionDone = false;
    			}	
    			goVertical(yRand);
    		}
    		if (os2(xDestination,yDestination)) {
        		goVertical(crossWalk);
        		goHorizontal(cWalk2i);
        		if (getYPos() == crossWalk && getXPos() == cWalk2i) {
        			readyToGoOuterSidewalk = true;
        			crossWalk2o();
        		}
        	}
    	}
    	
    	if (is3(getXPos(),getYPos()) && !readyToGoOuterSidewalk && !readyToGoInnerSidewalk) {
    		System.out.println("In is3");
			if (!transitionDone) {
    			goVertical(yRand);
    			if (getYPos() == yRand) {
    				transitionDone = true;
    			}
			}
        	if (is3(xDestination,yDestination) && transitionDone) {
        		goHorizontal(xDestination);
    			if (getXPos() == xDestination) {
    				goVertical(yDestination);
    				//atDestination
    			}
        	}
    		if ((os(xDestination,yDestination) || is(xDestination,yDestination)) && !is3(xDestination,yDestination) && !os3(xDestination,yDestination) && transitionDone) {
    			if (choseRand == false) {
    				if (is2(xDestination,yDestination) || os2(xDestination,yDestination)) {
    					xRand = (int) (400 + (Math.random() * 29));
    				}
    				else xRand = (int) (190 - (Math.random() * 29));
    				choseRand = true;
    			}
    			else {
    				goHorizontal(xRand);
    				if (getXPos() == xRand) {
    					if (getYPos() == 400) {
        					choseRand = false;
    					}
    					goVertical(yDestination);
    				}	
    			}
    		}
    		else if (os3(xDestination,yDestination)) {
        		goHorizontal(crossWalk);
        		goVertical(cWalk3i);
        		if (getXPos() == crossWalk && getYPos() == cWalk3i) {
        			readyToGoOuterSidewalk = true;
        			crossWalk3o();
        		}
        	}
    	}
    
    	if (is4(getXPos(),getYPos()) && !readyToGoOuterSidewalk && !readyToGoInnerSidewalk) {
    		System.out.println("In is4");
        	if (is4(xDestination,yDestination)) {
        		goVertical(yDestination);
    			if (getYPos() == yDestination) {
    				goHorizontal(xDestination);
    				//atDestination
    			}
        	}
    		if ((os(xDestination,yDestination) || is(xDestination,yDestination)) && !is4(xDestination,yDestination) && !os4(xDestination,yDestination)) {
    			if (choseRand == false) {
    				if (is3(xDestination,yDestination) || os3(xDestination,yDestination)  ) {
    					yRand = (int) (400 + (Math.random() * 29));
    				}
    				else yRand = (int) (190 - (Math.random() * 29));
    				choseRand = true;
    			}
    			if (getYPos() == 201 || getYPos() == 399) {
    				choseRand = false;
    				transitionDone = false;
    			}	
    			goVertical(yRand);
    		}
    		if (os4(xDestination,yDestination)) {
        		goVertical(crossWalk);
        		goHorizontal(cWalk4i);
        		if (getYPos() == crossWalk && getXPos() == cWalk4i) {
        			readyToGoOuterSidewalk = true;
        			crossWalk4o();
        		}
        	}
    	}
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
    	if (y >= 0 && y <= 80) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean os2(int x, int y) {
    	if ((x >= 520 && x <= 600) && (y > 80 && y < 520)) {
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
    	if ((x >= 0 && x <= 80) && (y > 80 && y < 520)) {
    		return true;
    	}
    	else return false;
	}
   
    public boolean is1(int x, int y) {
    	if (((x >= 160 && x <= 440) && (y >= 160 && y <= 200)) || ((x > 200 && x < 400) && (y > 200 && y < 240))) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean is2(int x, int y) {
    	if ((x >= 400 && x <= 440 && y > 200 && y < 400) || (x >= 360 && x <= 440 && y >= 240 && y <= 360)) {
    		return true;
    	}
    	else return false;
    }
	
    public boolean is3(int x, int y) {
    	if ((x >= 160 && x <= 440 && y >= 400 && y <= 440) || (x > 200 && x < 400 && y >= 360 && y < 400)) {
    		return true;
    	}
    	else return false;
	}
	
    public boolean is4(int x, int y) {
    	if ((x >= 160 && x <= 200 && y > 200 && y < 400) || (x >= 160 && x <= 240 && y >= 240 && y <= 360)) {
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
    
    public void goHorizontal(int xDest) {
		if (rectangle.x < xDest)
            rectangle.x++;
        else if (rectangle.x > xDest)
            rectangle.x--;
    }
    
    public void goVertical(int yDest) {
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
