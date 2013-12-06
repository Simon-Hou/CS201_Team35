package restaurant.restaurantGabe.gui;


import restaurant.restaurantGabe.CustomerRole;
//import restaurant.OldHostAgent;
import restaurant.restaurantGabe.WaiterRole;

import java.awt.*;

import cityGui.Gui;

public class WaiterGui implements Gui {

    private WaiterRole agent = null;

    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position
    public int xCook = 385;
    public int yCook = 90;

    private static int N_TABLES = 3;
    //public static final int[] xTables = {200,300,400};
    //public static final int[] yTables = {250,250,250};
    
    public int[] xTables;
    public int[] yTables;
    
    public int homePos;
    int xHome = 40;
    int yHome = 20;
    
    //this will display the food that is being brought to the customer
    private String food;
    
    boolean waitingForCust = true;
    boolean waitingToGetToCook = false;

    public WaiterGui(WaiterRole agent) {
        this.agent = agent;
    }
    
    public WaiterGui(WaiterRole agent,int homePos) {
        this.agent = agent;
        this.homePos = homePos;
        this.xHome = 70 + 30*homePos;
        this.yHome = 20;
        //System.out.println(""+xHome);
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

        for(int i = 0;i<N_TABLES;++i){
	        if (xPos == xDestination && yPos == yDestination
	        		& (xDestination == xTables[i] + 20) & (yDestination == yTables[i] - 20)) {
	           if(!waitingForCust){
	        	   waitingForCust = true;
	        	   agent.msgAtTable();
	           }
	        	//agent.msgAtTable();
	           //break;
	        }
        }
        if (xPos == xDestination && yPos == yDestination && waitingToGetToCook){
        	waitingToGetToCook = false;
     	   	agent.msgAtCook();
        }
    }
    static int host_width = 20;
    static int host_height = 20;
    
    
    
    public void atTable(){
    	agent.msgAtTable();
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, host_width, host_height);
        if(food!=null){
        	g.setColor(Color.black);
        	g.drawString(food, xPos, yPos+host_height/2);
        }
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(CustomerRole customer,int table_num) {
        xDestination = xTables[table_num] + 20;
        yDestination = yTables[table_num] - 20;
        customer.customerGui.DoGoToSeatDirected(this,xTables[table_num], yTables[table_num]);
    }
    
    public void DoGoToTable(int table_num,boolean waitingForCust){
        xDestination = xTables[table_num] + 20;
        yDestination = yTables[table_num] - 20;
        this.waitingForCust = waitingForCust;
    }

    public void DoLeaveCustomer() {
        xDestination = xHome;
        yDestination = yHome;
    }
    
    public void DoGoToHome(){
    	xDestination = xHome;
        yDestination = yHome;
        //System.out.println("Going to (" +xHome+","+yHome+")");
    }
    
    public void DoGiveCookOrder(){
        xDestination = xCook;
        yDestination = yCook;
        waitingToGetToCook = true;
    }
    
    public void doPutOrderOnStand(){
    	xDestination = xCook+10;
    	yDestination = yCook+20;
    	waitingToGetToCook = true;
    	
    }

    public void DoGetOrder(){
    	xDestination = xCook;
        yDestination = yCook - 35;
        waitingToGetToCook = true;
    }
    
    public void setFood(String food){
    	this.food = food;
    }
    
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
