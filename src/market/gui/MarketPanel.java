package market.gui;

import java.awt.Color;

import market.*;
import person.*;
import util.CityMap;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import interfaces.MarketCustomer;
import interfaces.Person;




public class MarketPanel extends JPanel implements ActionListener{

	private MarketAnimation animation; //= new MarketAnimation();
	
	private JLabel title = new JLabel("Market");
	private List<InventoryItem> inventoryList = new ArrayList<InventoryItem>();
	public JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    
    
    private JButton startButton = new JButton("Enter");
    private JButton deliveryButton = new JButton("Delivery");
	

    
    //AGENTS
    CityMap map = new CityMap();
    
    Market market = new Market();
    
    private List<MarketCustomerRole> customers = new ArrayList<MarketCustomerRole>();
   // private MarketCustomerRole customer;
    private MarketCashierRole cashier;
    private MarketHostRole host;
    private MarketEmployeeRole employee;
    private MarketDeliveryManRole deliveryMan;
    
    
    
	public MarketPanel(MarketAnimation anim){
		animation = anim;
		
		//-----------------------CONTROLS---------------------------
        setLayout(new FlowLayout());
		add(title);
		
		view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
		pane.setViewportView(view);
        add(pane);
	
         
        Dimension paneSize = new Dimension (400,200);
        pane.setPreferredSize(paneSize);
        pane.setMinimumSize(paneSize);
        pane.setMaximumSize(paneSize);
        
      

        
         Dimension buttonSize = new Dimension(paneSize.width-20, (int) (paneSize.height / 10));
         
         for(Map.Entry<String, Integer> entry : market.inventory.entrySet()){
        	 InventoryItem item = new InventoryItem(entry.getKey(), entry.getValue(), market, this);
        	 item.setPreferredSize(buttonSize);
             item.setMinimumSize(buttonSize);
             item.setMaximumSize(buttonSize);
             inventoryList.add(item);
             view.add(item);
         }
         

		
         validate();
         
         startButton.addActionListener(this);
         add(startButton);
         
         deliveryButton.addActionListener(this);
         add(deliveryButton);
         
         
         //--------------------AGENT/GUI TESTING SETUP----------------------

         PersonAgent p1 = new PersonAgent("Host", map);
         host = new MarketHostRole("Host", p1);
         p1.activeRole = host;
         p1.startThread();
        
         PersonAgent p2 = new PersonAgent("Employee", map);
         employee = new MarketEmployeeRole("Employee", p2);
         p2.activeRole = employee;
         p2.startThread();
        
         PersonAgent p3 = new PersonAgent("Cashier", map);
         cashier = new MarketCashierRole("Cashier", p3);
         p3.activeRole = cashier;
         p3.startThread();
         
         PersonAgent p4 = new PersonAgent("Delivery Man", map);
         deliveryMan = new MarketDeliveryManRole("Delivery Man", p4);
         p4.activeRole = deliveryMan;
         p4.startThread();
         
         market.cashier = cashier;
         market.host = host;
         market.setMarketPanel(this);
         
       //set contacts for all
         host.addEmployee(employee);
         host.setMarket(market);
         employee.setCashier(cashier);
         employee.addDeliveryMan(deliveryMan);
         deliveryMan.setCashier(cashier);
         cashier.setMarket(market);
         
        // employee:
         MarketEmployeeGui egui = new MarketEmployeeGui(employee);
         employee.setGui(egui);
         animation.addGui(egui);
	}
	
	

	
	public void actionPerformed(ActionEvent e) {
		System.err.println("Something was pressed.");
		
		if (e.getSource() == startButton){
			System.out.println("A customer has entered the market");
			addCustomer();
			
		}
		
		if (e.getSource() == deliveryButton){
			System.out.println("A business order is being called in");
			addDelivery();
			
		}
		
		for (InventoryItem item : inventoryList){
			if (e.getSource() == item.minus && item.inventory >0){
				item.inventory--;
				market.inventory.put(item.choice, item.inventory);
				updateInventory();
			}
			else if (e.getSource() == item.plus){
				item.inventory++;
				//item.inventoryLabel.setText(item.inventory.toString());
				//market.inventory.remove(item.choice);
				market.inventory.put(item.choice, item.inventory);
				updateInventory();
			}
		}
		
	}

	private void addCustomer(){
		
        PersonAgent p1 = new PersonAgent("Customer", map);
        MarketCustomerRole customer = new MarketCustomerRole("Customer", p1);
        customer.addToShoppingList("Pizza", 12 );
        customer.addToShoppingList("Car", 1);
        p1.activeRole = customer;
       p1.startThread();
       customers.add(customer);
       
       
       MarketCustomerGui custGui = new MarketCustomerGui(customer);
       customer.setGui(custGui);
       animation.addGui(custGui);

    
       
       //testing, this should come from the person agent
       customer.msgYouAreAtMarket(market);
       
       animation.enterCustomer();
	}
	
	private void addDelivery(){
		BusinessOrder order = new BusinessOrder();
		OrderItem item = new OrderItem("Steak", 5);
		OrderItem item2 = new OrderItem("Salad", 4);
		order.addItem(item);
		order.addItem(item2);
		host.msgBusinessWantsThis(order);
	}
	
	public void removeCustomer(){
		
		animation.enterCustomer();
		//remove from animation gui list
		//remove role from customers list in here
		
	}
	
	public void updateInventory(){
		for (InventoryItem item : inventoryList){
			item.inventory = market.inventory.get(item.choice);
			item.inventoryLabel.setText(item.inventory.toString());
		}
	}
	
	//Utilities
	
	private class InventoryItem extends JPanel{
		
		MarketPanel mp;
		Market market;
		
		String choice;
		 Integer inventory;
		
		JButton minus = new JButton("-");
		JLabel choiceLabel = new JLabel();
		JLabel inventoryLabel = new JLabel();
		JButton plus = new JButton("+");
		
		InventoryItem(String name, int invent, Market mark, MarketPanel mp){
			this.mp = mp;
			choice = name;
			inventory = invent;
			market = mark;
			
			setLayout(new GridLayout(1,5,5,5));
			setBorder(BorderFactory.createRaisedBevelBorder());
			
			
			choiceLabel.setText(choice);
			inventoryLabel.setText(inventory.toString());
		
			//choiceLabel.setHorizontalTextPosition(JLabel.RIGHT);
			//inventoryLabel.setVerticalTextPosition(SwingConstants.CENTER);
		
	        // button1.addActionListener(this);
			minus.addActionListener(mp);
			plus.addActionListener(mp);
			
			
			add(minus);
			add(choiceLabel);
			add(inventoryLabel);
			add(plus);
			
		}
	}
	
	void setAnimation (MarketAnimation ma){
		animation = ma;
	}
	
}
