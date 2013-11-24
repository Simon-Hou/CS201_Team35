package restaurant.restaurantLinda.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import restaurant.restaurantLinda.gui.AnimationPanel;
import restaurant.restaurantLinda.gui.MyImage;
import restaurant.restaurantLinda.gui.RestaurantGui;
import restaurant.restaurantLinda.gui.RestaurantPanel;
import astar.AStarNode;
import astar.AStarTraversal;
import astar.Position;

public abstract class GuiPerson implements Gui{
	
	public static final int WINDOWX = AnimationPanel.WINDOWX;		//Same as animation panel
    public static final int WINDOWY = AnimationPanel.WINDOWY;
    public final int cellSize = RestaurantPanel.cellSize;
	
    protected int personSize=30;
    protected int xPos, yPos;
    protected int xDestination, yDestination;
    protected int xfinal, yfinal;
    
    AStarTraversal aStar;
    Position previousPosition;
    Position currentPosition;
    Position diagonalPosition;
    List<Position> path;
    
    enum movementType {undefined, upleft, upright, downleft, downright};
    
    int wait=0;
    int attempts=1;
    
    
    private List<MyImage> carriedItems = new ArrayList<MyImage>();
    private List<MyImage> stillItems = new ArrayList<MyImage>();
	private String bufferText;

    public boolean isPresent() {
        return true;
    }
        
    //Returns a boolean stating whether or not the gui has traveled to the 'final destination'
    public boolean moveAndCheckDestination() {
    	
    	if (wait>0){
    		wait--;
    		return false;
    	}
    	
    	//if (destination == goal.customer){
    	//	CalculatePath(new Position(customer.getPosition().x/personSize+1,customer.getPosition().y/personSize+1)); 
    	//}
    	
    	movementType type = movementType.undefined;
    	
    	if (xPos < xDestination && yPos==yDestination)
            xPos++;
        else if (xPos > xDestination && yPos==yDestination)
            xPos--;
        else if (yPos < yDestination && xPos==xDestination)
            yPos++;
        else if (yPos > yDestination && xPos==xDestination)
            yPos--;
        else if (xPos <xDestination && yPos < yDestination)
        	type = movementType.downright;
        else if (xPos <xDestination && yPos > yDestination)
        	type = movementType.upright;
        else if (xPos >xDestination && yPos < yDestination)
        	type = movementType.downleft;
        else if (xPos >xDestination && yPos > yDestination)
        	type = movementType.upleft;
    	
    	switch(type){
	    	case downright:
	    		xPos++;
	    		yPos++;
	    		break;
	    	case upright:
	    		xPos++;
	    		yPos--;
	    		break;
	    	case downleft:
	    		xPos--;
	    		yPos++;
	    		break;
	    	case upleft:
	    		xPos--;
	    		yPos--;
	    		break;
	    	default:
	    		break;
    	}
        
        
                
        if (xfinal==xPos && yfinal ==yPos)
        {        	
    		xDestination = xfinal;
    		yDestination = yfinal;
    		return true;
        }
        else if (xPos == xDestination && yPos == yDestination){
        	if (previousPosition!=currentPosition){
        		previousPosition.release(aStar.getGrid());
        		previousPosition = currentPosition;
        	}

        	//1 means we reached our destination
        	if (path!=null && path.size()>1)
        		MoveToNextPosition();
        	else if (path!=null){
        		path=null;
        		if (xDestination!=xfinal || yDestination!=yfinal){
        			xDestination=xfinal;
        			yDestination=yfinal;
        		}
        	}
        }
        
        return false;
    }
    
    void CalculatePath(Position to){
    	path = null;
    	currentPosition.release(aStar.getGrid());
    	
    	boolean wasOpen = to.open(aStar.getGrid());
    	if(!wasOpen)
    		to.release(aStar.getGrid());
    	
    	//System.out.println("[Gaut] " + guiWaiter.getName() + " moving from " + currentPosition.toString() + " to " + to.toString());
    	System.gc();
    	
    	AStarNode aStarNode = (AStarNode)aStar.generalSearch(previousPosition, to);
    	path = aStarNode.getPath();
    	
    	if(!wasOpen)
    		to.moveInto(aStar.getGrid());
    	
    	currentPosition = path.get(0);
    	currentPosition.moveInto(aStar.getGrid());

    	if (xPos/cellSize < currentPosition.getX())
	    	xDestination = currentPosition.getX()*cellSize;
    	else if (xPos/cellSize == currentPosition.getX())
    		xDestination = xPos;
    	else
    		xDestination = (currentPosition.getX()+1)*cellSize - personSize;   
    		
    	
    	if (yPos/cellSize < currentPosition.getY())
    		yDestination = currentPosition.getY()*cellSize;
    	else if (yPos/cellSize == currentPosition.getY())
    		yDestination = yPos;
    	else
    		yDestination = (currentPosition.getY()+1)*cellSize - personSize;
    	
    	//System.out.println("new path: " + path + ", finalDestination: " + xfinal + " " + yfinal);
    	
    	
    }
    
    void MoveToNextPosition(){
	    //Try and get lock for the next step.
	    //int attempts    = 1;
	    boolean gotPermit   = new Position(path.get(1).getX(), path.get(1).getY()).moveInto(aStar.getGrid());

	    //Did not get lock. Lets make n attempts.
	    /*while (!gotPermit && attempts < 3) {
			System.out.println("[Gaut] " + agent.getName() + " got NO permit for " + path.get(1).toString() + " on attempt " + attempts);
	
			//Wait for 1sec and try again to get lock.
			try { Thread.sleep(1000); }
			catch (Exception e){}
	
			gotPermit   = new Position(path.get(1).getX(), path.get(1).getY()).moveInto(aStar.getGrid());
			attempts ++;
	    }*/
	    
	    if (!gotPermit && attempts<3){
	    	//System.out.println(agent.getName() + "got NO permit for " + path.get(1).toString() + " on attempt " + attempts);
	    	wait=20;
	    	return;
	    }

	    //Did not get lock after trying n attempts. So recalculating path.            
	    if (!gotPermit) {
			//System.out.println("[Gaut] " + agent.getName() + " No Luck even after " + attempts + " attempts! Lets recalculate");
			attempts=1;
			CalculatePath(path.get(path.size()-1));
			return;
	    }

	    //Got the required lock. Lets move.
	    //System.out.println("[Gaut] " + agent.getName() + " got permit for " + path.get(0).toString());
	    attempts=1;
	    currentPosition = path.get(1);
	    currentPosition.moveInto(aStar.getGrid());

	    path.remove(0);
	    
	    
	    if (xPos/cellSize < currentPosition.getX())
	    	xDestination = currentPosition.getX()*cellSize;
    	else if (xPos/cellSize == currentPosition.getX())
    		xDestination = xPos;
    	else
    		xDestination = (currentPosition.getX()+1)*cellSize - personSize;   
    		
    	
    	if (yPos/cellSize < currentPosition.getY())
    		yDestination = currentPosition.getY()*cellSize;
    	else if (yPos/cellSize == currentPosition.getY())
    		yDestination = yPos;
    	else
    		yDestination = (currentPosition.getY()+1)*cellSize - personSize;
    	
    	//System.out.println("Position: " + xPos + " " + yPos + "     Destination: "  + xDestination + " " + yDestination);
    }

}
