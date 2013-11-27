package cityGui.test;


import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.table.TableStringConverter;

import cityGui.CityComponent;
import cityGui.CityPanel;
import cityGui.SimCityGui;
import person.PersonAgent;
import public_Gui.Gui;
import util.Loc;

public class PersonGui extends CityComponent implements Gui {

    private PersonAgent person;
    
//	public List<ImageIcon> up = new ArrayList<ImageIcon>();
//    java.net.URL up1 = getClass().getResource("personImages/up1.png");
//	ImageIcon u1 = new ImageIcon(up1);
//	java.net.URL up2 = getClass().getResource("personImages/up2.png");
//	ImageIcon u2 = new ImageIcon(up2);
//	java.net.URL up3 = getClass().getResource("personImages/up3.png");
//	ImageIcon u3 = new ImageIcon(up3);
//	public List<ImageIcon> down = new ArrayList<ImageIcon>();
//	java.net.URL down1 = getClass().getResource("personImages/down1.png");
//	ImageIcon d1 = new ImageIcon(down1);
//	java.net.URL down2 = getClass().getResource("personImages/down2.png");
//	ImageIcon d2 = new ImageIcon(down2);
//	java.net.URL down3 = getClass().getResource("personImages/down3.png");
//	ImageIcon d3 = new ImageIcon(down3);
//	public List<ImageIcon> left = new ArrayList<ImageIcon>();
//	java.net.URL left1 = getClass().getResource("personImages/left1.png");
//	ImageIcon l1 = new ImageIcon(left1);
//	java.net.URL left2 = getClass().getResource("personImages/left2.png");
//	ImageIcon l2 = new ImageIcon(left2);
//	java.net.URL left3 = getClass().getResource("personImages/left3.png");
//	ImageIcon l3 = new ImageIcon(left3);
//	public List<ImageIcon> right = new ArrayList<ImageIcon>();
//	java.net.URL right1 = getClass().getResource("personImages/right1.png");
//	ImageIcon r1 = new ImageIcon(right1);
//	java.net.URL right2 = getClass().getResource("personImages/right2.png");
//	ImageIcon r2 = new ImageIcon(right2);
//	java.net.URL right3 = getClass().getResource("personImages/right3.png");
//	ImageIcon r3 = new ImageIcon(right3);

    ImageIcon currentImage;
	
//	public List<ImageIcon> bup = new ArrayList<ImageIcon>();
//    java.net.URL bup1 = getClass().getResource("personImages/bup1.png");
//	ImageIcon bu1 = new ImageIcon(bup1);
//	java.net.URL bup2 = getClass().getResource("personImages/bup2.png");
//	ImageIcon bu2 = new ImageIcon(bup2);
//	java.net.URL bup3 = getClass().getResource("personImages/bup3.png");
//	ImageIcon bu3 = new ImageIcon(bup3);
//	public List<ImageIcon> bdown = new ArrayList<ImageIcon>();
//	java.net.URL bdown1 = getClass().getResource("personImages/bdown1.png");
//	ImageIcon bd1 = new ImageIcon(bdown1);
//	java.net.URL bdown2 = getClass().getResource("personImages/bdown2.png");
//	ImageIcon bd2 = new ImageIcon(bdown2);
//	java.net.URL bdown3 = getClass().getResource("personImages/bdown3.png");
//	ImageIcon bd3 = new ImageIcon(bdown3);
//	public List<ImageIcon> bleft = new ArrayList<ImageIcon>();
//	java.net.URL bleft1 = getClass().getResource("personImages/bleft1.png");
//	ImageIcon bl1 = new ImageIcon(bleft1);
//	java.net.URL bleft2 = getClass().getResource("personImages/bleft2.png");
//	ImageIcon bl2 = new ImageIcon(bleft2);
//	public List<ImageIcon> bright = new ArrayList<ImageIcon>();
//	java.net.URL bright1 = getClass().getResource("personImages/bright1.png");
//	ImageIcon br1 = new ImageIcon(bright1);
//	java.net.URL bright2 = getClass().getResource("personImages/bright2.png");
//	ImageIcon br2 = new ImageIcon(bright2);

    SimCityGui gui;
    
    public int xPos = 20, yPos = 29;//default waiter position
    public int xDestination = 395, yDestination = 29;//default start position
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
    private boolean visible = true;
    
    public boolean onTheMove = false;
    public boolean waitingForBus = false;
    
    private Semaphore crossingStreet = new Semaphore(0,true);
     
    public PersonGui(PersonAgent agent) {
        this.person = agent;
    }
    
    public PersonGui(PersonAgent c, SimCityGui gui, int xPos, int yPos, int xDest, int yDest){ //HostAgent m) {
    	super(xPos, yPos, Color.blue, "Dude");
    	person = c;
		xDestination = xDest;
		yDestination = yDest;
		this.gui = gui;
		rectangle = new Rectangle(xPos, yPos, 10, 10);

		this.person.spriteChoice = (int) (Math.random() * gui.upAll.size());
		
		this.person.upSprites = gui.upAll.get(this.person.spriteChoice);
		this.person.downSprites = gui.downAll.get(this.person.spriteChoice);
		this.person.rightSprites = gui.rightAll.get(this.person.spriteChoice);
		this.person.leftSprites = gui.leftAll.get(this.person.spriteChoice);
		
		currentImage = this.person.downSprites.get(0);
		
//		up.add(u1); up.add(u2); up.add(u1); up.add(u3);
//		down.add(d1); down.add(d2); down.add(d1); down.add(d3);
//		left.add(l1); left.add(l2); left.add(l1); left.add(l3);
//		right.add(r1); right.add(r2); right.add(r1); right.add(r3);
//		bup.add(u1); bup.add(u2); bup.add(u1); bup.add(u3);
//		bdown.add(d1); bdown.add(d2); bdown.add(d1); bdown.add(d3);
//		bleft.add(l1); bleft.add(l2);
//		bright.add(r1); bright.add(r2);
	}
    
    public void doGoToBuilding(Loc loc){
    	this.xDestination = loc.x;
    	this.yDestination = loc.y;
    	onTheMove = true;
    }
    
    public void doGoToBus(Loc loc){
    	this.xDestination = loc.x;
    	this.yDestination = loc.y;
    	onTheMove = true;
    	waitingForBus = true;
    }
    
    public void onBus(){
    	waitingForBus = false;
    	visible = false;
    }
    
    public void offBus(){
    	visible = true;
    }
    
	public void updatePosition() {
		
		if (!atDestination()) {
//			System.out.println("XDest: " + xDestination);
//			System.out.println("YDest: " + yDestination);
//			System.out.println("XPos: " + getXPos());
//			System.out.println("YPos: " + getYPos());
//			if (transitionDone) {
//				System.out.println("Transition is done");
//			}
			if (readyToGoInnerSidewalk) {
				//System.out.println("In inner sidewalk method");
				if (b1(getXPos(),getYPos())) {
					goVertical(cWalk1i);
					if (getYPos() == cWalk1i) {
	    				readyToGoInnerSidewalk = false;
	    				transitionDone = true;
	    			}
				}
				if (b2(getXPos(),getYPos())) {
					goHorizontal(cWalk2i);
					if (getXPos() == cWalk2i) {
	    				readyToGoInnerSidewalk = false;
	    				transitionDone = true;
	    			}
				}
				if (b3(getXPos(),getYPos())) {
					goVertical(cWalk3i);
					if (getYPos() == cWalk3i) {
	    				readyToGoInnerSidewalk = false;
	    				transitionDone = true;
	    			}
				}
				if (b4(getXPos(),getYPos())) {
					goHorizontal(cWalk4i);
					if (getXPos() == cWalk4i) {
	    				readyToGoInnerSidewalk = false;
	    				transitionDone = true;
	    			}
				}
			}
			
			if (readyToGoOuterSidewalk) {
				System.out.println("Going to outer sidewalk");
				if (b1(getXPos(),getYPos())) {
					goVertical(cWalk1o);
					if (getYPos() == cWalk1o) {
	    				readyToGoOuterSidewalk = false;
	    				transitionDone = true;
	    			}
					//else System.out.println("Didnt reach crosswalk destination");
	
				}
				if (b2(getXPos(),getYPos())) {
					goHorizontal(cWalk2o);
					if (getXPos() == cWalk2o) {
	    				readyToGoOuterSidewalk = false;
	    				transitionDone = true;
	    			}
				}
				if (b3(getXPos(),getYPos())) {
					goVertical(cWalk3o);
					if (getYPos() == cWalk3o) {
	    				readyToGoOuterSidewalk = false;
	    				transitionDone = true;
	    			}
				}
				if (b4(getXPos(),getYPos())) {
					goHorizontal(cWalk4o);
					if (getXPos() == cWalk4o) {
	    				readyToGoOuterSidewalk = false;
	    				transitionDone = true;
	    			}
				}
			}
			
			if (topLeftCorner(getXPos(),getYPos()) && topLeftCorner(xDestination,yDestination)) {
				//System.out.println("In top left corner");
				goHorizontal(40);
				goVertical(40);
			}
			
			if (topRightCorner(getXPos(),getYPos()) && topRightCorner(xDestination,yDestination)) {
				//System.out.println("In top right corner");
				goHorizontal(550);
				goVertical(40);
			}
			
			if (bottomLeftCorner(getXPos(),getYPos()) && bottomLeftCorner(xDestination,yDestination)) {
				//System.out.println("In bottom left corner");
				goHorizontal(40);
				goVertical(550);
			}
			
			if (bottomRightCorner(getXPos(),getYPos()) && bottomRightCorner(xDestination,yDestination)) {
				//System.out.println("In bottom right corner");
				goHorizontal(550);
				goVertical(550);
			}
			
	    	if (os1(getXPos(),getYPos()) && !readyToGoInnerSidewalk && !readyToGoOuterSidewalk) {
				//System.out.println("XPos: " + getXPos() + " In os1");
	    		if (!transitionDone) {
	    			goVertical(yRand);
	    			if (getYPos() == yRand) {
	    				transitionDone = true;
	    			}
				}
	        	if (os1(xDestination,yDestination) && transitionDone) {
	        		//System.out.println("XDestination: " + getXPos() + " In os1");
	        		goHorizontal(xDestination);
	    			if (getXPos() == xDestination && !atDestination()) {
	//            		System.out.println("XPosition: " + getXPos() + " is at XDestination: " + xDestination);
	//            		System.out.println("YPosition: " + getYPos() + " needs to be at YDestination: " + yDestination);
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
	    					if (getYPos() == 70) {
	        					choseRand = false;
	    					}
	    					goVertical(yDestination);
	    				}	
	    			}
	        	}
	    	}
	    	if (os2(getXPos(),getYPos()) && !readyToGoInnerSidewalk && !readyToGoOuterSidewalk) {
	    		//System.out.println("In os2");
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
	//        		if (getYPos() == 81 || getYPos() == 519) {
	//    				choseRand = false;
	//    				transitionDone = false;
	//    			}	
	        		if (getYPos() == 71 || getYPos() == 519) {
	    				choseRand = false;
	    				transitionDone = false;
	    			}	
	    			goVertical(yRand);	
	        	}
	    	}
	    	if (os3(getXPos(),getYPos()) && !readyToGoInnerSidewalk && !readyToGoOuterSidewalk) {
	    		//System.out.println("In os3");
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
	    		//System.out.println("In os4");
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
	        		if (getYPos() == 71 || getYPos() == 519) {
	    				choseRand = false;
	    				transitionDone = false;
	    			}	
	    			goVertical(yRand);	
	        	}
	    	}
	   
	    	if (is1(getXPos(),getYPos()) && !readyToGoOuterSidewalk && !readyToGoInnerSidewalk) {
	    		//System.out.println("XPos: " + getXPos() + " In is1");
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
	    					if (getYPos() == 190) {
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
	    		//System.out.println("In is2");
	    		if (is2(xDestination,yDestination)) {
	        		goVertical(yDestination);
	    			if (getYPos() == yDestination) {
	    				goHorizontal(xDestination);
	    				//atDestination
	    			}
	        	}
	    		if ((os(xDestination,yDestination) || is(xDestination,yDestination)) && !is2(xDestination,yDestination) && !os2(xDestination,yDestination)) {
	    			if (choseRand == false) {
	    				if (is3(xDestination,yDestination) ||os3(xDestination,yDestination)) {
	    					yRand = (int) (400 + (Math.random() * 29));
	    				}
	    				else yRand = (int) (190 - (Math.random() * 29));
	    				choseRand = true;
	    			}
	//    			if (getYPos() == 201 || getYPos() == 399) {
	//    				choseRand = false;
	//    				transitionDone = false;
	//    			}	
	    			if (getYPos() == 191 || getYPos() == 399) {
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
	    		//System.out.println("In is3");
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
	    		//System.out.println("In is4");
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
	//    			if (getYPos() == 201 || getYPos() == 399) {
	//    				choseRand = false;
	//    				transitionDone = false;
	//    			}	
	    			if (getYPos() == 191 || getYPos() == 399) {
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
	    	
	    	//System.out.println("Pos: ("+rectangle.x+","+rectangle.y+")");
	    	//System.out.println("Dest: ("+xDestination+","+yDestination+")");
	    	if(onTheMove && rectangle.x==this.xDestination && rectangle.y==this.yDestination){
	    		onTheMove = false;
	    		person.msgAtDestination();
	    		
	    	}
		}
    }

	public boolean getArrived() {
		return hasArrived;
	}
	
	public void setArrived(boolean arrived) {
		hasArrived = arrived;
	}
	
	public void setLoc(Loc loc){
		rectangle.x = loc.x;
		rectangle.y = loc.y;
	}
	
	public boolean getStartPosition() {
		return startPosition;
	}
	
    public void paint(Graphics g) {
    	if(!visible){
    		return;
    	}
    	if (!atDestination() || waitingForBus) {
    		g.drawImage(currentImage.getImage(),getXPos(),getYPos(),10,10,null);
    	}
    }    	

    public boolean isPresent() {
        return true;
    }

    public int getXPos() {
        return rectangle.x;
    }

    public int getYPos() {
        return rectangle.y;
    }
    
    public boolean os1(int x, int y) {
    	if (x >= 40 && x <= 550 && y >= 40 && y <= 70) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean os2(int x, int y) {
    	if (x >= 520 && x <= 550 && y > 70 && y < 520) {
    		return true;
    	}
    	else return false;
    }
	
    public boolean os3(int x, int y) {
    	if (x >= 40 && x <= 550 && y >= 520 && y <= 550) {
    		return true;
    	}
    	else return false;
	}
	
    public boolean os4(int x, int y) {
    	if (x >= 40 && x <= 70 && y > 70 && y < 520) {
    		return true;
    	}
    	else return false;
	}
   
    public boolean is1(int x, int y) {
    	if (x >= 160 && x <= 430 && y >= 160 && y <= 190) {
    		return true;
    	}
    	else return false;
    }
    
    public boolean is2(int x, int y) {
    	if ((x >= 400 && x <= 430 && y > 190 && y < 400)) {
    		return true;
    	}
    	else return false;
    }
	
    public boolean is3(int x, int y) {
    	if ((x >= 160 && x <= 430 && y >= 400 && y <= 430)) {
    		return true;
    	}
    	else return false;
	}
	
    public boolean is4(int x, int y) {
    	if ((x >= 160 && x <= 190 && y > 190 && y < 400)) {
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
    
    public boolean topLeftCorner(int x, int y) {
    	if (getYPos() >= 0 && getYPos() <= 40 && getXPos() >= 0 && getXPos() <= 40) {
    		return true;
    	}
    	return false;
    }
    public boolean topRightCorner(int x, int y) {
    	if (getYPos() >= 0 && getYPos() <= 40 && getXPos() >= 550 && getXPos() <= 600) {
    		return true;
    	}
    	return false;
    	
    }
    public boolean bottomLeftCorner(int x, int y) {
    	if (getYPos() > 550 && getYPos() <= 600 && getXPos() >= 0 && getXPos() <= 40) {
    		return true;
    	}
    	return false;
    }
    public boolean bottomRightCorner(int x, int y) {
    	if (getYPos() > 550 && getYPos() <= 600 && getXPos() >= 550 && getXPos() <= 600) {
    		return true;
    	}
    	return false;
    }
    
    public boolean atDestination() {
    	if (getXPos() == xDestination && getYPos() == yDestination) {
    		return true;
    	}
    	return false;
    }
    
    public void goHorizontal(int xDest) {
    	if (rectangle.x == xDest) {
    		return;
    	}
    	if (!this.person.belongings.bike) {	
    		if (rectangle.x < xDest) {
    			rectangle.x++;
    			currentImage = this.person.rightSprites.get(rectangle.x % this.person.rightSprites.size());
    		}
    		else if (rectangle.x > xDest) {
    			rectangle.x--;
    			currentImage = this.person.leftSprites.get(rectangle.x % this.person.leftSprites.size());
    		}
    	}
    }
//    	else {
//    		if (this.rectangle.x < xDest) {
//    			this.rectangle.x++;
//    			currentImage = this.bright.get(rectangle.x % 2);
//    		}
//    		if (this.rectangle.x < xDest) {
//    			this.rectangle.x++;
//    			currentImage = this.bright.get(rectangle.x % 2);
//    		}
//    		if (this.rectangle.x > xDest) {
//    			this.rectangle.x--;
//    			currentImage = this.bleft.get(rectangle.x % 2);
//    		}
//    		if (this.rectangle.x > xDest) {
//    			this.rectangle.x--;
//    			currentImage = this.bleft.get(rectangle.x % 2);
//    		}
//    	}
//    }
    
    public void goVertical(int yDest) {
    	if (this.rectangle.y == yDest) {
    		return;
    	}
    	else if (!this.person.belongings.bike) {
    		if (this.rectangle.y < yDest) {
    			this.rectangle.y++;
    			currentImage = this.person.downSprites.get(rectangle.y % this.person.downSprites.size());
    		}
    		else if (rectangle.y > yDest) {
    			this.rectangle.y--;
    			currentImage = currentImage = this.person.upSprites.get(rectangle.y % this.person.upSprites.size());
    		}
    	}
//    	else {
//    		if (this.rectangle.y < yDest) {
//    			this.rectangle.y++;
//    			currentImage = this.bdown.get(rectangle.y % 4);
//    		}
//    		if (this.rectangle.y < yDest) {
//    			this.rectangle.y++;
//    			currentImage = this.bdown.get(rectangle.y % 4);
//    		}
//    		if (this.rectangle.y > yDest) {		
//    			this.rectangle.y--;
//    			currentImage = this.bup.get(rectangle.y % 4);
//    		}
//    		if (this.rectangle.y > yDest) {
//    			this.rectangle.y--;
//    			currentImage = this.bup.get(rectangle.y % 4);
//    		}
//    	}
//		if (rectangle.x < xDest) {
//            rectangle.x++;
//			currentImage = this.right.get(0/*rectangle.x % 4*/);
//		}
//        else if (rectangle.x > xDest) {
//            rectangle.x--;
//			currentImage = this.left.get(0/*rectangle.x % 4*/);
//        }
    }
    
//    public void goVertical(int yDest) {
//        if (rectangle.y < yDest) {
//            rectangle.y++;
//            currentImage = this.down.get(0/*rectangle.y % 4*/);
//        }
//        else if (rectangle.y > yDest) {
//        	rectangle.y--;
//        	currentImage = this.up.get(0/*rectangle.y % 4*/);
//        }
//    }
    
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

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
