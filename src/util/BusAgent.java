package util;

import interfaces.Person;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.Timer;

import cityGui.CityComponent;
import cityGui.test.AStarTraversalVehicle;
import cityGui.test.BusGui;
import agent.Agent;
import astar.AStarNode;
import astar.Position;

public class BusAgent extends Agent implements ActionListener{

	
	//Data
	List<Person> passengers = new ArrayList<Person>();
	
	static int TIME_AT_STOP = 3000;
	Timer timer;// = new Timer(TIME_AT_STOP, this);

	public List<BusStop> stops = new ArrayList<BusStop>();
	public int currentStop = 0;
	int randomOffset = (int) Math.floor(3*Math.random());
	public int TIME_BETWEEN_STOPS = 3;
	public Semaphore atStopFreeze = new Semaphore(1,true);
	
	public Semaphore waitingForStop = new Semaphore(1,true);
	
	public BusAgentGui gui;
	public Position currentPosition;
	public AStarTraversalVehicle aStar;
	
	public boolean timeToGo = false;
	public boolean arrivedAtStop = true;
	
	
	//Constructor 
	
	public BusAgent(){
		timer = new Timer(TIME_AT_STOP, this);
		timer.start();
		timer.addActionListener(this);
	}
	
	
	//Messages
	
	public void msgTimeToGo(){
		timeToGo = true;
		timer.stop();
		stateChanged();
	}
	
	public void msgAtStop(){
		waitingForStop.release();
	}
	
	
	//Scheduler
	
	protected boolean pickAndExecuteAnAction() {
		
		if(timeToGo){
			GoToNextStop();
			return true;
		}
		
		return false;
	}
	
	
	//Actions
	
	private void GoToNextStop(){
		
		timeToGo = false;
		
		doGoToNextStop();
		
		currentStop = (currentStop+1)%stops.size();
		
		for(Person p:passengers){
			p.msgBusAtStop(this, stops.get(currentStop));
		}
		
		for(Person p:stops.get(currentStop).peopleWaiting){
			p.msgBusAtStop(this, stops.get(currentStop));
		}
		
		timer.start();
		
	}
	
	
	//GUI
	
	private void doGoToNextStop(){
		
		
		
	}

	
	//Action Listener Stuff
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		msgTimeToGo();
		
	}
	
	
	
	
	 //this is just a subroutine for waiter moves. It's not an "Action"
    //itself, it is called by Actions.
    void guiMoveFromCurrentPostionTo(Position to){
        //System.out.println("[Gaut] " + guiWaiter.getName() + " moving from " + currentPosition.toString() + " to " + to.toString());

    	//to = new Position(2,5);
    	Loc l = CityComponent.findNearestGridLoc(new Point(gui.rectangle.x,gui.rectangle.y));
    	currentPosition = new Position(l.x,l.y);
    	//System.out.println("("+currentPosition.getX()+","+currentPosition.getY()+")");
    	//System.out.println("("+to.getX()+","+to.getY()+")");



    	//Do("CALLING PERSON MOVE");

        AStarNode aStarNode = (AStarNode)aStar.generalSearch(currentPosition, to);
        List<Position> path = aStarNode.getPath();
       // Do("Got here, path calculated");
        Boolean firstStep   = true;
        Boolean gotPermit   = true;

        for (Position tmpPath: path) {
            //The first node in the path is the current node. So skip it.
            if (firstStep) {
                firstStep   = false;
                continue;
            }

            //Try and get lock for the next step.
            int attempts    = 1;
            gotPermit       = new Position(tmpPath.getX(), tmpPath.getY()).moveInto(aStar.getGrid());

            //Did not get lock. Lets make n attempts.
            while (!gotPermit && attempts < 3) {
                //System.out.println("[Gaut] " + guiWaiter.getName() + " got NO permit for " + tmpPath.toString() + " on attempt " + attempts);

                //Wait for 1sec and try again to get lock.
                try { Thread.sleep(1000); }
                catch (Exception e){}

                gotPermit   = new Position(tmpPath.getX(), tmpPath.getY()).moveInto(aStar.getGrid());
                attempts ++;
            }

            //Did not get lock after trying n attempts. So recalculating path.
            if (!gotPermit) {
                //System.out.println("[Gaut] " + guiWaiter.getName() + " No Luck even after " + attempts + " attempts! Lets recalculate");
                guiMoveFromCurrentPostionTo(to);
                break;
            }

            //Got the required lock. Lets move.
            //System.out.println("[Gaut] " + guiWaiter.getName() + " got permit for " + tmpPath.toString());
            currentPosition.release(aStar.getGrid());
            currentPosition = new Position(tmpPath.getX(), tmpPath.getY ());
            //System.out.println(gui==null);
            gui.move(currentPosition.getX(), currentPosition.getY());
        }
        /*
        boolean pathTaken = false;
        while (!pathTaken) {
            pathTaken = true;
            //print("A* search from " + currentPosition + "to "+to);
            AStarNode a = (AStarNode)aStar.generalSearch(currentPosition,to);
            if (a == null) {//generally won't happen. A* will run out of space first.
                System.out.println("no path found. What should we do?");
                break; //dw for now
            }
            //dw coming. Get the table position for table 4 from the gui
            //now we have a path. We should try to move there
            List<Position> ps = a.getPath();
            Do("Moving to position " + to + " via " + ps);
            for (int i=1; i<ps.size();i++){//i=0 is where we are
                //we will try to move to each position from where we are.
                //this should work unless someone has moved into our way
                //during our calculation. This could easily happen. If it
                //does we need to recompute another A* on the fly.
                Position next = ps.get(i);
                if (next.moveInto(aStar.getGrid())){
                    //tell the layout gui
                    guiWaiter.move(next.getX(),next.getY());
                    currentPosition.release(aStar.getGrid());
                    currentPosition = next;
                }
                else {
                    System.out.println("going to break out path-moving");
                    pathTaken = false;
                    break;
                }
            }
        }
        */
    }
	

}
