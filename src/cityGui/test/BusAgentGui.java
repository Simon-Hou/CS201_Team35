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
import util.Bus;
import util.BusAgent;
import util.Loc;

public class BusAgentGui extends CityComponent implements Gui {

	
	public int xDestination,yDestination;
	public boolean doingMove;
	public BusAgent bus;
	
	public static int gridScale = 30;
	
	
	
	
	
	
	@Override
	public void updatePosition() {
    	if(rectangle.x<xDestination){
    		rectangle.x++;
    	}
    	else if(rectangle.x>xDestination){
    		rectangle.x--;
    	}
    	
    	if(rectangle.y<yDestination){
    		rectangle.y++;
    	}
    	else if(rectangle.y>yDestination){
    		rectangle.y--;
    	}
    	if(doingMove && rectangle.x == xDestination && rectangle.y == yDestination){
    		bus.waitingForStop.release();
    		doingMove = false;
    	}
    	
    }
	
	public void move(int x, int y){
    	
    	doingMove = true;
    	
    	this.xDestination = gridScale*x;
    	this.yDestination = gridScale*y;
    	
    	try {
			atMove.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
	
	


	public void paint(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillRect(rectangle.x, rectangle.y, 50, 20);
    }  
	
	@Override
	public void draw(Graphics2D g) {
		
		
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
