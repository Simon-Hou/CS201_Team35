package restaurantLinda.gui;

import restaurantLinda.CookAgent;
import restaurantLinda.WaiterAgent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

public class CookGui implements Gui {

	public static final int WINDOWX = AnimationPanel.WINDOWX;		//Same as animation panel
    public static final int WINDOWY = AnimationPanel.WINDOWY;
	
	CookAgent agent;
	

	private int personSize=AnimationPanel.PERSONSIZE;
    private int xPos, yPos;//default waiter position
    private int xDestination, yDestination;//default start position
    private goal destination=goal.none;
    private enum goal{none, refrigerator, stoveCooking, stovePlating, platingWindow};
    private Point home = new Point(WINDOWX-personSize, (int)(WINDOWY*0.5));
    
    private List<MyImage> cookingFoods = new ArrayList<MyImage>();			//Doesn't really need to be a list, since we're only using 1 image at a time...but this does make it possible 
    private List<MyImage> carriedItems = new ArrayList<MyImage>();
    private List<MyImage> platedFoods = new ArrayList<MyImage>();
    int plateNum=0; //Just a cheat to cycle through plating areas (lazy way to avoid plate-stacking)
    private MyImage currentItem;
	private String bufferText;
	public final String path="../../images/";

    public CookGui(CookAgent agent) {
    	this.agent = agent;
    	
    	xPos = xDestination = home.x;
        yPos = yDestination = home.y;
        
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

        if (destination!=goal.none)		//Gui has an actual destination that agent wants to be notified about
        {
        	if (xPos == xDestination && yPos == yDestination && destination==goal.refrigerator){
            	destination=goal.stoveCooking;
        		xDestination = AnimationPanel.STOVE.x;
        		yDestination = AnimationPanel.STOVE.y-personSize;
        		currentItem = new MyImage("RawFood",xPos,yPos);
        		carriedItems.add(currentItem);
            }
        	else if(xPos == xDestination && yPos == yDestination && destination==goal.stoveCooking){
        		currentItem.x=AnimationPanel.STOVE.x;
        		currentItem.y = AnimationPanel.STOVE.y;
        		carriedItems.remove(currentItem);
        		cookingFoods.add(currentItem);
        		currentItem=null;
        		destination = goal.none;
        		agent.msgAtDestination();
        	}
        	else if (xPos == xDestination && yPos == yDestination && destination==goal.stovePlating){
            	try{
	        		currentItem.x = xPos;
	        		currentItem.y = yPos;
	        		
	        		destination=goal.platingWindow;
	            	xDestination = WINDOWX-100;
	        		yDestination = AnimationPanel.REFRIGERATOR.height+(40*plateNum++);
	        		
	        		plateNum %= (WINDOWY-AnimationPanel.REFRIGERATOR.height-AnimationPanel.STOVE.height)/40;
      		
	        		for (MyImage i: cookingFoods){
	        			if (i.type.equals("RawFood")){
	        				cookingFoods.remove(i);
	        				break;
	        			}
	        		}
	        		carriedItems.add(currentItem);
            	}
            	catch(NullPointerException e){
            		//Nothing to do. Was just checking to make sure the image has been created, otherwise do nothing until it has been (rare scenario where cook is already at the location)
            	}
            }
        	else if (xPos == xDestination && yPos == yDestination && destination==goal.platingWindow){
        		destination = goal.none;
        		
        		currentItem.x-=50;
        		carriedItems.remove(currentItem);
        		platedFoods.add(currentItem);
        		currentItem=null;
        		
        		agent.msgAtDestination();
        	}
        }  
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(xPos, yPos, personSize, personSize);
        
        for (MyImage icon: cookingFoods)
        	icon.draw(g);
        
        for (MyImage icon: carriedItems)
        	icon.draw(g);
        
        if (bufferText!=null){
			g.setColor(Color.BLACK);
			g.drawString(bufferText, xPos, yPos+15);
		}
			  
    }

    public boolean isPresent() {
        return true;
    }
    
    public void DoCooking(){
    	destination = goal.refrigerator;

    	xDestination = AnimationPanel.REFRIGERATOR.x;
    	yDestination = AnimationPanel.REFRIGERATOR.y+AnimationPanel.REFRIGERATOR.height;
    }
    
    public void DoPlating(String food){
    	destination = goal.stovePlating;
    	
    	xDestination = AnimationPanel.STOVE.x;
		yDestination = AnimationPanel.STOVE.y-personSize;
		
		currentItem = new MyImage(food);
    }
    
    public void DoGoToDefault(){
    	xDestination = home.x;
    	yDestination = home.y;
    }
    
    
    public void DoTalk(String text){
		bufferText=text;
	}
    
    public MyImage getCurrentItem(){
    	return currentItem;
    }
    
    public void setPlates(List<MyImage> plates){
    	platedFoods = plates;
    }
}
