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
	public Semaphore atMove = new Semaphore(1,true);
	public BusAgent bus;
	
	public SimCityGui gui;
	public boolean clockwise;
	
	public static int gridScale = 30;
	public boolean moving = false;
	
	
	public BusAgentGui(BusAgent bus,SimCityGui gui,boolean clockwise){
    	super(165,130,Color.YELLOW,"Bus");
    	if(!clockwise){
    		this.x = 460;
    		this.y = 90;
    	}
    	this.bus = bus;
    	this.gui = gui;
    	this.xDestination = this.x;
    	this.yDestination = this.y;
    	this.clockwise = clockwise;
    	rectangle = new Rectangle(x, y, 50, 20);
    }
	
	
	
	
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		if(!moving){
			return;
		}
		
		if(clockwise){
        	if(topLaneF()){
        		rectangle.x++;
        	}
        	if(topRightCornerF()){
        		rectangle = new Rectangle(450,130,20,50);
        	}
        	if(rightLaneF()){
        		rectangle.y++;
        	}
        	if(bottomRightCornerF()){
        		rectangle = new Rectangle(420,450,50,20);
        	}
        	if(bottomLaneF()){
        		rectangle.x--;
        	}
        	if(bottomLeftCornerF()){
        		rectangle = new Rectangle(130,420,20,50);
        	}
        	if(leftLaneF()){
        		rectangle.y--;
        	}
        	if(topLeftCornerF()){
        		rectangle = new Rectangle(130,130,50,20);
        	}
        }
		else{
			if(topLaneB()){
        		rectangle.x--;

        	}
        	if(topLeftCornerB()){
        		rectangle = new Rectangle(90,90,20,50);
        	}
        	if(leftLaneB()){
        		rectangle.y++;
        	}
        	if(bottomLeftCornerB()){
        		rectangle = new Rectangle(90,490,50,20);
        	}
        	if(bottomLaneB()){
        		rectangle.x++;
        	}
        	if(bottomRightCornerB()){
        		rectangle = new Rectangle(490,460,20,50);
        	}
        	if(rightLaneB()){
        		rectangle.y--;
        	}

        	if(topRightCornerB()){
        		rectangle = new Rectangle(460,90,50,20);
        		
        	}





		}
		if(moving && rectangle.x==this.xDestination && rectangle.y ==this.yDestination){
			moving = false;
			bus.msgAtStop();
//			this.bus.atStopFreeze.release();
//			this.bus.updateBus();
		}
		
		
	}
	
	
	
	/*@Override
	public void updatePosition() {
		if(topLaneF() && rectangle.x>=380){
			rectangle = new Rectangle(450,122,20,50);
		}
		if(rightLaneF() && rectangle.y>=420){
			rectangle = new Rectangle(420,450,50,20);
		}
		if(bottomLaneF() && rectangle.x<=150){
			rectangle = new Rectangle(120,420,20,50);
		}
		if(leftLaneF() && rectangle.y<=150){
			rectangle = new Rectangle(rectangle.x,130,50,20);
		}
		
		if(topLaneB() && rectangle.x<=90){
			rectangle = new Rectangle(90,90,20,50);
		}
		if(rightLaneB() && rectangle.y>=420){
			rectangle = new Rectangle(420,450,50,20);
		}
		if(bottomLaneB() && rectangle.x<=150){
			rectangle = new Rectangle(120,420,20,50);
		}
		if(leftLaneB() && rectangle.y<=90){
			rectangle = new Rectangle(90,90,50,20);
		}
		
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
    		atMove.release();
    		doingMove = false;
    	}
    	
    }*/
	
	public void goTo(int x, int y){
		moving = true;
		this.xDestination = x;
		this.yDestination = y;
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
	
	
	
	public boolean topLaneF(){
    	if(rectangle.y==130 && (rectangle.x>=120 && rectangle.x<=430)){
    		return true;
    	}
    	return false;
    }
    public boolean topRightCornerF(){
    	if(rectangle.x>=430 && rectangle.y==130){
    		return true;
    	}
    	return false;
    }
    public boolean topLaneB(){
    	//System.out.println("YEAAAAAAAA");
    	//System.out.println(rectangle.x+" "+rectangle.y);
    	if(rectangle.y==90 && (rectangle.x>=90 && rectangle.x<=460)){
    		//System.out.println("NOOOOOO");
    		return true;
    	}
    	return false;
    }
    public boolean topRightCornerB(){
    	if(rectangle.x==490 && rectangle.y<=90){
    		return true;
    	}
    	return false;
    }
    public boolean rightLaneF(){
    	if(rectangle.x==450 && rectangle.y>=130 && rectangle.y<=420){
    		return true;
    	}
    	return false;
    }
    public boolean bottomRightCornerF(){
    	if(rectangle.x==450 && rectangle.y>=130 && rectangle.y>=420){
    		return true;
    	}
    	return false;
    }
    public boolean rightLaneB(){
    	if(rectangle.x==490 && rectangle.y>=90 && rectangle.y<=460){
    		return true;
    	}
    	return false;	
    }
    public boolean bottomRightCornerB(){
    	if(rectangle.x>=460 && rectangle.y==490 ){
    		return true;
    	}
    	return false;
    }
    public boolean bottomLaneF(){
    	if(rectangle.y == 450 && rectangle.x>=130 && rectangle.x<=420){
    		return true;
    	}
    	return false;
    }
    public boolean bottomLeftCornerF(){
    	if(rectangle.x<=130 && rectangle.y==450){
    		return true;
    	}
    	return false;
    }
    
	
	 public boolean bottomLaneB(){
	    	if(rectangle.y == 490 && rectangle.x>=90 && rectangle.x<=460){
	    		return true;
	    	}
	    	return false;
	    }
	    public boolean bottomLeftCornerB(){
	    	if(rectangle.x==90 && rectangle.y>=460){
	    		return true;
	    	}
	    	return false;
	    }
	    public boolean leftLaneF(){
	    	if(rectangle.x == 130 && rectangle.y<=450 && rectangle.y>=130){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    public boolean topLeftCornerF(){
	    	if(rectangle.x ==130 && rectangle.y<=130){
	    		return true;
	    	}
	    	return false;
	    }
	    public boolean leftLaneB(){
	    	if(rectangle.x == 90 && rectangle.y<=460 && rectangle.y>=90){
	    		return true;
	    	}
	    	return false;
	    }
	    public boolean topLeftCornerB(){
	    	if(rectangle.x <=90 && rectangle.y==90){
	    		return true;
	    	}
	    	return false;
	    }
	

}
