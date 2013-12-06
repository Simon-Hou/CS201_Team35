package cityGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import agent.Agent;
import restaurant.restaurantGabe.CashierRole;
import restaurant.restaurantGabe.CookRole;
import restaurant.restaurantGabe.CustomerRole;
import restaurant.restaurantGabe.HostRole;
import restaurant.restaurantGabe.gui.CookGui;
import restaurant.restaurantGabe.gui.CustomerGui;
import restaurant.restaurantGabe.gui.ListPanel;
//import restaurant.restaurantGabe.gui.MarketRole;
import restaurant.restaurantGabe.gui.RestaurantGui;
import restaurant.restaurantGabe.gui.WaiterPanel;
import restaurant.restaurantGabe.util.RevolvingStand;

public class CityRestaurantGabe extends CityRestaurant{


	public CityRestaurantCardGabe animationPanel;
	public RevolvingStand stand  = new RevolvingStand();
	
	public int numWaitingCusts;
    
    public int numWaitingSpots = 10;
    public boolean[] waitingSpots = new boolean[numWaitingSpots];
    
    int[] x_table = {120,195,270};
    int[] y_table = {275,215,155};
	
    
    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public CityRestaurantGabe(int x, int y) {
    	
    	super(x, y);
    	
    	numWaitingCusts = 0;
    	
    	//numWaitingSpots = 10;
    	for(int i = 0;i<numWaitingSpots;i++){
    		waitingSpots[i] = true;
    	}
    	
 
    	
        int WINDOWX = 850;
        int WINDOWY = 850;
        
        //x_table  = {200,300,400};
        //y_table = {250,250,250};
        
        animationPanel.x_table = x_table;
        animationPanel.y_table = y_table;
        

    }

    
    public void actionPerformed(ActionEvent e){
    	/*if(e.getSource() == pauseButton){
    		pauseSystem();
    		
    	}*/
    }
    
    
    
    
    

//    public static void main(String[] args) {
//        RestaurantGui gui = new RestaurantGui();
//        gui.setTitle("csci201 Restaurant");
//        gui.setVisible(true);
//        gui.setResizable(false);
//        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//    
    
    
    
    
    
	
	@Override
	public void initializeRestaurant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAnimationPanel(SimCityGui city) {
		// TODO Auto-generated method stub
		
	}
	
	
    public void setCustomerHungry(CustomerRole c){
    	//System.out.println("Setting GUI to hungry");
    	c.getGui().setHungry();
    	
    }
	
	

}
