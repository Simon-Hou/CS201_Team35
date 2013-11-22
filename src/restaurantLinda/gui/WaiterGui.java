package restaurantLinda.gui;

import restaurantLinda.WaiterAgent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

import astar.*;

public class WaiterGui extends GuiPerson {	
	private WaiterAgent agent = null;
	RestaurantGui gui;

    private int personSize=30;
    private Map<Integer,Point> tableMap;
    private List<MyImage> platedFoods;
    private goal destination=goal.none;
    private enum goal{none,customer,table,cook,plating};
    private CustomerGui customer;
    
    Position homePosition;
    
    int wait=0;
    int attempts=1;
    
    
    private List<MyImage> carriedItems = Collections.synchronizedList(new ArrayList<MyImage>());
    private List<MyImage> stillItems = Collections.synchronizedList(new ArrayList<MyImage>());
	private String bufferText;
	

    public WaiterGui(WaiterAgent agent, RestaurantGui gui, int position, AStarTraversal aStar) {
        this.agent = agent;
        this.gui = gui;
        this.aStar = aStar;
        
        int limit = (WINDOWX-300)/cellSize;
        if (position>=limit){
        	position%=limit;
        }
        
        homePosition = new Position(1+position, WINDOWY/cellSize-1);
        
        xPos = xDestination = xfinal = homePosition.getX()*cellSize;
        yPos = yDestination = yfinal = homePosition.getY()*cellSize;
        System.out.println("homePosition = " + homePosition);
        previousPosition = currentPosition = new Position(xPos/cellSize, yPos/cellSize);
        currentPosition.moveInto(aStar.getGrid());
        
    }
    
    public void DoGoToCustomer(CustomerGui customer) {
    	bufferText = null;
    	destination=goal.customer;
    	this.customer = customer;
    	xfinal = customer.getDestination().x + personSize;
    	yfinal = customer.getDestination().y + personSize;
    	CalculatePath(new Position(xfinal/cellSize, yfinal/cellSize)); 
    }

    public void DoSeatCustomer(CustomerGui customer, int table) {
    	bufferText = "Follow Me";
        destination=goal.table;
        xfinal = tableMap.get(table).x + personSize;
        yfinal = tableMap.get(table).y - personSize;
        CalculatePath(new Position(xfinal/cellSize, yfinal/cellSize));
        customer.DoGoToSeat(tableMap.get(table).x,tableMap.get(table).y);
    }
    
    public void DoGoToTable(int table){
    	destination= goal.table;
    	 xfinal = tableMap.get(table).x + personSize;
         yfinal = tableMap.get(table).y - personSize;
    	CalculatePath(new Position(xfinal/cellSize, yfinal/cellSize));
    }
    
    public void DoGoToCook(){
    	destination = goal.cook;
    	xfinal = WINDOWX-150-personSize;
    	yfinal = (int) (WINDOWY*0.5);
    	CalculatePath(new Position(xfinal/cellSize, yfinal/cellSize));
    }
    
    public void DoGoToPlatingArea(String food){
    	destination = goal.plating;
    	
    	synchronized(platedFoods){
    		for(MyImage plate: platedFoods){
    			if (plate.type.equals(food)){
    				xfinal = plate.x-personSize;
    				yfinal = plate.y;
    				platedFoods.remove(plate);
    				stillItems.add(plate);
    				break;
    			}
    		}
    	}  	
    	CalculatePath(new Position(xfinal/cellSize, yfinal/cellSize));
    }
    
    public void DoServeFood(String food,int table){
    	synchronized(stillItems){
	    	for (MyImage icon: stillItems){
	    		if (icon.type.equals(food)){
	    			icon.x = xPos;
	    			icon.y = yPos;
	    			carriedItems.add(icon);
	    			stillItems.remove(icon);
	    			break;
	    		}
	    	}
    	}
    	DoGoToTable(table);
    }
    
    public void DoRelinquishFood(){
    	carriedItems.clear();
    	bufferText=null;
    }
    
    public void DoGoToDefault(){
    	bufferText = null;
    	xfinal = homePosition.getX()*cellSize;
    	yfinal = homePosition.getY()*cellSize;
    	CalculatePath(homePosition);
    }
    
    public void DoGoOnBreak(){
    	bufferText="Break";
    	xfinal = homePosition.getX()*cellSize;
    	yfinal = homePosition.getY()*cellSize;
    	CalculatePath(homePosition);
    }
    
    public void DoGoOffBreak(){
    	bufferText = null;
    	DoGoToDefault();
    }
    
    public void DoTalk(String text){
		bufferText=text;
	}
    
    public void UpdateInfo(){
    	gui.updateWaiter(agent);
    }

    public void updatePosition() {
    	
    	if (destination!=goal.none && moveAndCheckDestination())		//Gui has an actual destination that agent wants to be notified about
        {        	
    		path = null;
    		xDestination = xfinal;
    		yDestination = yfinal;
    		destination=goal.none;
            agent.msgAtDestination();
        }
    	
    	synchronized(carriedItems){
	    	for (MyImage icon: carriedItems)
	        	icon.updatePosition(xPos,yPos);
    	}
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, personSize, personSize);
        
        synchronized(carriedItems){
	        for (MyImage icon: carriedItems)
	        	icon.draw(g);
        }
        
        synchronized(stillItems){
	        for (MyImage icon: stillItems)
	        	icon.draw(g);
        }
        
        if (bufferText!=null){
			g.setColor(Color.BLACK);
			g.drawString(bufferText, xPos, yPos+15);
		}
			  
    }
    	
    public void setTables(Map<Integer,Point> map)
    {
     	tableMap = map;
    }
     
    public void setPlates(List<MyImage> plates){
      	platedFoods = plates;
    }
    
    public String getPosition(){
    	return xPos + " " + yPos;
    }
}
