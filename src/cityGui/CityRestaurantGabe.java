package cityGui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

public class CityRestaurantGabe extends CityRestaurant{

	public CityRestaurantGabe(int x, int y) {
		super(x, y);
		
	}

    //Host, cook, waiters, customers, markets, cashier that must be hacked in
	public CookRole cook = new CookRole("Cook",0);
    public HostRole host = new HostRole("Host");
    public CashierRole cashier = new CashierRole("Cashier");
    private int NUM_MARKETS = 3;
    public List<MarketRole> Markets = new ArrayList<MarketRole>();
    /*public MarketAgent m1 = new MarketAgent("Joel");
    public MarketAgent m2 = new MarketAgent("Bill");
    public MarketAgent m3 = new MarketAgent("Bob");*/
    
    /*private WaiterAgent waiter1 = new WaiterAgent("John");
    public WaiterGui waiter1Gui = new WaiterGui(waiter1);
    private WaiterAgent waiter2 = new WaiterAgent("Martha");
    public WaiterGui waiter2Gui = new WaiterGui(waiter2);*/

    private Vector<CustomerRole> customers = new Vector<CustomerRole>();

    private JPanel restLabel = new JPanel();
    public ListPanel customerPanel = new ListPanel(this, "Customers");
    public WaiterPanel waiterPanel = new WaiterPanel(this);
    private JPanel group = new JPanel();

    public RestaurantGui gui; //reference to main gui
    
    public int[] xTables;
    public int[] yTables;

    public CityRestaurantGabe(int x, int y,RestaurantGui gui) {
    	
    	super(x,y);
    	
        this.gui = gui;
        
        /*waiter1.setGui(waiter1Gui);
        waiter1.setCook(cook);
        waiter1.setHost(host);
        host.addWaiter(waiter1);
        
        waiter2.setGui(waiter2Gui);
        waiter2.setCook(cook);
        waiter2.setHost(host);
        host.addWaiter(waiter2);*/
        
        //host.hostGui.xTables = super.x_table;
        gui.stand.setCook(cook);
        cook.setRevolvingStand(gui.stand);
        

        //gui.animationPanel.addGui(waiter1Gui);
        //gui.animationPanel.addGui(waiter2Gui);
        host.startThread();
        //waiter1.startThread();
        //waiter2.startThread();
        
        cashier.startThread();
        
        MarketRole firstM = new MarketRole("market0",0,100,100,100);
        firstM.setCashier(cashier);
        firstM.startThread();
        cook.addMarket(firstM);
        
        MarketRole secondM = new MarketRole("market1",100,100,100,100);
        secondM.setCashier(cashier);
        secondM.startThread();
        cook.addMarket(secondM);
        
        MarketRole thirdM = new MarketRole("market2",100,100,100,100);
        thirdM.setCashier(cashier);
        thirdM.startThread();
        cook.addMarket(thirdM);
        
        CookGui cGui = new CookGui(cook);
        gui.animationPanel.addGui(cGui);
        cook.setGui(cGui);
        
        cook.startThread();
        /*for(int i = 0;i<NUM_MARKETS;++i){
        	MarketAgent m = new MarketAgent("m"+i,100,100,100,100);
        	Markets.add(m);
        	cook.addMarket(m);
        	m.startThread();
        }*/
        /*cook.addMarket(m1);
        cook.addMarket(m2);
        cook.addMarket(m3);*/

        setLayout(new GridLayout(1, 2, 20, 20));
        group.setLayout(new GridLayout(1, 2, 10, 10));

        group.add(customerPanel);

        initRestLabel();
        add(restLabel);
        add(waiterPanel);
        add(group);
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    private void initRestLabel() {
        JLabel label = new JLabel();
        //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

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
    /*public void showInfo(String type, String name) {

        if (type.equals("Customers")) {

            for (int i = 0; i < customers.size(); i++) {
                CustomerAgent temp = customers.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        }
    }*/
    
    public void setCustHungry(String name){
    	for (int i = 0; i < customers.size(); i++) {
            CustomerRole temp = customers.get(i);
            if (temp.getName() == name)
                gui.setCustomerHungry(temp);
        }
    	
    	
    }

    /**
     * Adds a customer or waiter to the appropriate list
     *
     * @param type indicates whether the person is a customer or waiter (later)
     * @param name name of person
     */
    public void addPerson(String type, String name) {

    	if (type.equals("Customers")) {
    		CustomerRole c = new CustomerRole(name);	
    		CustomerGui g = new CustomerGui(c, gui);

    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setGui(g);
    		c.setCashier(cashier);
    		customers.add(c);
    		c.startThread();
    	}
    }
	
	
	@Override
	public void initializeRestaurant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAnimationPanel(SimCityGui city) {
		// TODO Auto-generated method stub
		
	}

}
