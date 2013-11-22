package restaurantLinda.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import restaurantLinda.gui.AnimationPanel;
import restaurantLinda.gui.MyImage;
import restaurantLinda.gui.RestaurantGui;
import restaurantLinda.gui.RestaurantPanel;
import astar.AStarNode;
import astar.AStarTraversal;
import astar.Position;

public abstract class PersonGui implements Gui{
	
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
    List<Position> path;
    
    int wait=0;
    int attempts=1;
    
    
    private List<MyImage> carriedItems = new ArrayList<MyImage>();
    private List<MyImage> stillItems = new ArrayList<MyImage>();
	private String bufferText;

    public boolean isPresent() {
        return true;
    }
    
    void CalculatePath(Position to){
    	path = null;
    	currentPosition.release(aStar.getGrid());
    	
    	boolean wasOpen = to.open(aStar.getGrid());
    	if(!wasOpen)
    		to.release(aStar.getGrid());
    	
    	//System.out.println("[Gaut] " + guiWaiter.getName() + " moving from " + currentPosition.toString() + " to " + to.toString());
    	System.out.println("destination = " + to);
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
    		
    	
    	if (yPos/cellSize <= currentPosition.getY())
    		yDestination = currentPosition.getY()*cellSize;
    	else if (yPos/cellSize == currentPosition.getY())
    		yDestination = yPos;
    	else
    		yDestination = (currentPosition.getY()+1)*cellSize - personSize;
    		
    	//xDestination = currentPosition.getX()*cellSize;
    	//yDestination = currentPosition.getY()*cellSize;
    	
    	System.out.println("new path: " + path + ", finalDestination: " + xfinal + " " + yfinal);
    	
    	
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
    		
    	
    	if (yPos/cellSize <= currentPosition.getY())
    		yDestination = currentPosition.getY()*cellSize;
    	else if (yPos/cellSize == currentPosition.getY())
    		yDestination = yPos;
    	else
    		yDestination = (currentPosition.getY()+1)*cellSize - personSize;
	    //xDestination = currentPosition.getX()*cellSize;
	    //yDestination = currentPosition.getY()*cellSize;;
    	
    	System.out.println("Position: " + xPos + " " + yPos + "     Destination: "  + xDestination + " " + yDestination);
    }

}
