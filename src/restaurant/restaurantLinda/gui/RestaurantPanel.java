package restaurant.restaurantLinda.gui;

import restaurant.ProducerConsumerMonitor;
import restaurant.Restaurant;
import restaurant.restaurantLinda.CashierRole;
import restaurant.restaurantLinda.CookRole;
import restaurant.restaurantLinda.CustomerRole;
import restaurant.restaurantLinda.HostRole;
import restaurant.restaurantLinda.OriginalWaiterRole;
import restaurant.restaurantLinda.ProducerConsumerWaiterRole;
import restaurant.restaurantLinda.RestaurantOrder;
import restaurant.restaurantLinda.WaiterRole;

import interfaces.Person;
import javax.swing.*;

import astar.AStarTraversal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel {

	static int cellSize = 50;
	static int gridX = AnimationPanel.WINDOWX/cellSize;
    static int gridY = AnimationPanel.WINDOWY/cellSize;
    //Create grid for AStar
    Semaphore[][] grid = new Semaphore[gridX][gridY];

    ProducerConsumerMonitor<RestaurantOrder> orderMonitor = new ProducerConsumerMonitor<RestaurantOrder>();
    
    //Host, cook, waiters and customers
    private HostRole host = new HostRole("Sarah");
	private CookRole cook;
	private CashierRole cashier;
    private Vector<CustomerRole> customers = new Vector<CustomerRole>();
    private Vector<WaiterRole> waiters = new Vector<WaiterRole>();

    private JPanel restLabel = new JPanel();
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    private ListPanel waiterPanel = new ListPanel(this,"Waiters");
    private TablePanel tablePanel = new TablePanel(this);
    private Map<Integer,Point> tableMap=new HashMap<Integer,Point>();
    
    private RestaurantGui gui; //reference to main gui

    Restaurant r;
    
    public RestaurantPanel(RestaurantGui gui) {
    	
        this.gui = gui;
        cashier =  new CashierRole("Cashier", r);
        cook = new CookRole("Cook", orderMonitor, r);
        CookGui cg = new CookGui(cook);
        cg.setPlates(gui.animationPanel.platedFoods);
        gui.animationPanel.addGui(cg);
        cook.setGui(cg);
        
        host.startThread();
        cook.startThread();
        cashier.startThread();

        JPanel group = new JPanel();
        setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
        group.setLayout(new GridLayout(1, 2, 20, 10));
        group.add(customerPanel);
        group.add(waiterPanel);
        
        tablePanel.setTables(tableMap);
        tablePanel.setPreferredSize(new Dimension((int) (RestaurantGui.WINDOWX*.2), (int) (RestaurantGui.WINDOWY*.35)));
        
        initRestLabel();
        add(restLabel);
        add(group);
        add(tablePanel);
        
       
        //initialize the semaphores
        for (int i=0; i<gridX ; i++)
    	    for (int j = 0; j<gridY; j++)
    	    	grid[i][j]=new Semaphore(1,true);
        try{
        	//Plating area
        	for (int i=gridX-(150/cellSize); i<gridX-(100/cellSize); i++){
        		for (int j=0; j<gridY; j++){
        			grid[i][j].acquire();
        		}
        	}
        	//Refrigerator
        	for (int i=0; i<(AnimationPanel.REFRIGERATOR.width/cellSize); i++){
        		for (int j=0; j<(AnimationPanel.REFRIGERATOR.height/cellSize); j++){
        			grid[(AnimationPanel.REFRIGERATOR.x/cellSize)+i][(AnimationPanel.REFRIGERATOR.y/cellSize)+j].acquire();
        		}
        	}
        	//Stove
        	for(int i=0; i<(AnimationPanel.STOVE.width/cellSize); i++){
        		for (int j=0; j<(AnimationPanel.STOVE.height/cellSize); j++){
        			grid[(AnimationPanel.STOVE.x/cellSize)+i][(AnimationPanel.STOVE.y/cellSize)+j].acquire();
        		}
        	}
        	
        }catch (Exception e) {
    	    System.out.println("Unexpected exception caught in during setup:"+ e);
    	}
        
        //Cheat to make tables and a market
        addTable(1,200,150);
        addTable(1,350,150);
        addTable(1,500,150);                
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    private void initRestLabel() {
        JLabel label = new JLabel();
        //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        restLabel.setLayout(new BorderLayout());
        restLabel.setPreferredSize(new Dimension((int) (RestaurantGui.WINDOWX*.2), (int) (RestaurantGui.WINDOWY*.35)));
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td>" +
                		"</tr></table><h3><u> Menu</u></h3><table>" +
                		"<tr><td>Steak</td><td>$16.00</td></tr>" +
                		"<tr><td>Chicken</td><td>$11.00</td></tr>" +
                		"<tr><td>Salad</td><td>$6.00</td></tr>" +
                		"<tr><td>Pizza</td><td>$9.00</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        restLabel.add(new JLabel("               "), BorderLayout.EAST);
        restLabel.add(new JLabel("               "), BorderLayout.WEST);
    }

    /**
     * When a customer or waiter is clicked, this function calls
     * updatedInfoPanel() from the main gui so that person's information
     * will be shown
     *
     * @param type indicates whether the person is a customer or waiter
     * @param name name of person
     */
    public void showInfo(String type, String name) {
        if (type.equals("Customers")) {
            for (int i = 0; i < customers.size(); i++) {
                CustomerRole temp = customers.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        }
        else if (type.equals("Waiters")){
        	for (int i = 0; i < waiters.size(); i++) {
                WaiterRole temp = waiters.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        }
    }

    /**
     * Adds a customer or waiter to the appropriate list
     *
     * @param type indicates whether the person is a customer or waiter (later)
     * @param name name of person
     */
    public void addPerson(String type, String name, boolean hungry) {

    	if (type.equals("Customers")) {
    		CustomerRole c = new CustomerRole(name);	
    		CustomerGui g = new CustomerGui(c, gui);
    		if (hungry)
    			g.setHungry();

    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setGui(g);
    		customers.add(c);
    		c.startThread();
    	}
    	else if (type.equals("Waiters")){
    		WaiterRole w; 
    		if (name.contains("producerConsumer"))
    				w= new ProducerConsumerWaiterRole(name,host,cook,cashier, orderMonitor);
    		else
    			w= new OriginalWaiterRole(name,host,cook,cashier);
    		WaiterGui wg = new WaiterGui(w,gui, waiters.size(), new AStarTraversal(grid));
    		wg.setTables(tableMap);
    		wg.setPlates(gui.animationPanel.platedFoods);
    		
    		gui.animationPanel.addGui(wg);
    		host.addWaiter(w);
    		w.setGui(wg);
    		waiters.add(w);
    		w.startThread();
    	}
    }
     
    public boolean addTable(int size, int xLoc, int yLoc) {
    	xLoc = xLoc/cellSize;
    	yLoc = yLoc/cellSize;
		if(tryAddTable(AnimationPanel.TABLESIZE/cellSize, xLoc, yLoc)) {
			tableMap.put(tableMap.size()+1, new Point(xLoc*cellSize,yLoc*cellSize));
			gui.animationPanel.addTable(new Point(xLoc*cellSize,yLoc*cellSize));
			host.addTable(size);
			System.out.println("Added table " + (tableMap.size()+1));
			return true;
		}
		System.out.println("Cannot add table " + (tableMap.size()+1));
		return false;
	}
    
    //Takes in the reduced coordinates and sizes 
    private boolean tryAddTable(int size, int x, int y)
    {
    	try
		{
			int acqCnt = -1;
			int[][] acqList = new int[size*size][2];
			for (int i=0; i<size; i++) {
				for (int j=0; j<size; j++) {
					boolean acquired = grid[x+i][y+j].tryAcquire();
					if(acquired) {
						acqCnt++;
						acqList[acqCnt][0] = x+i;
						acqList[acqCnt][1] = y+j;
					}
				    if(!acquired) {
						for(int k=0; k<=acqCnt; k++) {
							grid[acqList[k][0]][acqList[k][1]].release();
						}
						return false;
					}
				}
			}
		}catch (Exception e)
		{
		    System.out.println("Unexpected exception caught in during setup:"+ e);
		}
    	return true;
    }
    
    
    public void pause(){
    	cook.pause();
    	host.pause();
    	for (CustomerRole c: customers){
    		c.pause();
    	}
    	for (WaiterRole w: waiters){
    		w.pause();
    	}	
    }
    
    public void restart(){
    	cook.restart();
    	host.restart();
    	for (CustomerRole c: customers){
    		c.restart();
    	}
    	for (WaiterRole w: waiters){
    		w.restart();
    	}    	
    }


}
