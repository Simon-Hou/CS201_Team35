package restaurant.restaurantGabe.gui;


import restaurant.restaurantGabe.CashierRole;
import restaurant.restaurantGabe.CookRole;
//import restaurant.OldCustomerAgent;
//import restaurant.OldHostAgent;
import restaurant.restaurantGabe.HostRole;
import restaurant.restaurantGabe.StandWaiterRole;
import restaurant.restaurantGabe.WaiterRole;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class WaiterPanel extends JPanel implements ActionListener {

    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    
    
    private JPanel view = new JPanel();
    
    private JButton addPersonB = new JButton("Add");
    private List<WaiterRole> waiterList = new ArrayList<WaiterRole>();
    private List<JCheckBox> breakBoxes = new ArrayList<JCheckBox>();
    
    private JTextField nameField = new JTextField();

    private RestaurantPanel restPanel;
    private HostRole host;
    private CookRole cook;
    private CashierRole cashier;
    
    int numWaiters;
    
    public WaiterPanel(RestaurantPanel rp) {
    	numWaiters = 0;
        restPanel = rp;
        host = restPanel.host;
        cook = restPanel.cook;
        cashier = restPanel.cashier;

        setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
        add(new JLabel("<html><pre> <u>" + "Waiters" + "</u><br></pre></html>"));

        addPersonB.addActionListener(this);
        add(addPersonB);
        nameField.setMaximumSize(new Dimension(500,30));
        add(nameField);
        

        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        pane.setViewportView(view);
        add(pane);
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	//See if the action was the add person button
    	if(e.getSource()==addPersonB){
    		String name = nameField.getText();
        	if(name.length()==0){
        		return;
        	}
        	else{
        		for(int i = 1;i<10;++i){
                	if(nameField.getText().equals(""+ i)){
                		for(int wait = 0;wait<i;wait++){
                			addWaiter("w"+wait);
                		}
                		return;
                	}
                }
        		
        		
        		//v2.2 Grading Scenarios
        		if(nameField.getText().equals("Scenario1")){
        			for(int wait = 0;wait<3;wait++){
            			restPanel.customerPanel.addPerson("c"+wait);
            			addWaiter("w"+wait);
            		}
        			return;
        		}
        		if(nameField.getText().equals("Scenario2")){
        			for(int wait = 0;wait<3;wait++){
            			restPanel.customerPanel.addPerson("c"+wait);
            			//addWaiter("w"+wait);
            		}
        			/*try {
        			    Thread.sleep(6000);
        			} catch(InterruptedException ex) {
        			    Thread.currentThread().interrupt();
        			}
        			for(int wait = 0;wait<3;wait++){
            			//restPanel.customerPanel.addPerson("c"+wait);
            			addWaiter("w"+wait);
            		}*/
        			
        			return;
        		}
        		if(nameField.getText().equals("Scenario3")){
        			restPanel.cook.setNoFoodsMarketInvSpeed(100, 3);
        			return;
        		}
        		if(nameField.getText().equals("Scenario4")){
        			restPanel.cook.setNoFoodsSpeed(3);
        			return;
        		}
        		if(nameField.getText().equals("Scenario5")){
        			restPanel.cashier.setMoney(0);
        			restPanel.cook.setNoFoodsMarketInvSpeed(100, 3);
        			return;
        		}
        		
        		
        		//v2.1 Grading Scenarios
        		if(nameField.getText().equals("v2.1Scenario1")){
            		addWaiter("w"+0);
            		restPanel.customerPanel.addPerson("c"+0);
        			//restPanel.cook.setNoFoodsMarketInvSpeed(100, 40);
        			return;
        		}
        		if(nameField.getText().equals("v2.1Scenario2")){
            		addWaiter("w"+0);
            		restPanel.customerPanel.addPerson("c"+0);
        			restPanel.cook.setNoFoodsMarketInvSpeed(100, 3);
        			return;
        		}
        		if(nameField.getText().equals("v2.1Scenario3")){
        			for(int wait = 0;wait<3;wait++){
            			restPanel.customerPanel.addPerson("c"+wait);
            			addWaiter("w"+wait);
            		}
        			return;
        		}
        		if(nameField.getText().equals("v2.1Scenario4")){
        			for(int wait = 0;wait<3;wait++){
            			restPanel.customerPanel.addPerson("c"+wait);
            			addWaiter("w"+wait);
            		}
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario1")){
            		addWaiter("w"+0);
            		restPanel.customerPanel.addPerson("c"+0);
        			restPanel.cook.setNoFoodsMarketInvSpeed(100, 40);
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario2")){
        			
            		addWaiter("w"+0);
            		restPanel.customerPanel.addPerson("c"+0);
            		
        			restPanel.cook.setNoFoodsMarketInvSpeed(0, 40);
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario3")){
        			for(int wait = 0;wait<3;wait++){
            			addWaiter("w"+wait);
            		}
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario4")){
        			for(int wait = 0;wait<3;wait++){
            			restPanel.customerPanel.addPerson("c"+wait);
            			addWaiter("w"+wait);
            		}
        			restPanel.customerPanel.addPerson("Stay");
        			restPanel.customerPanel.addPerson("Leave");
        			
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario5")){
        			addWaiter("w0");
        			restPanel.customerPanel.addPerson("Poor");
        			
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario6")){
        			addWaiter("w0");
        			restPanel.customerPanel.addPerson("Cheap");
        			
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario7")){
        			addWaiter("w0");
        			restPanel.customerPanel.addPerson("Cheap");
        			restPanel.cook.setSalad(0);
        			return;
        		}
        		if(nameField.getText().equals("NN-v2.1Scenario8")){
        			addWaiter("w0");
        			restPanel.customerPanel.addPerson("Flake");
        			return;
        		}
        		addWaiter(name);
        	}
    	}
    	
    	//see if the action was the make waiter go on break button
    	for(int i = 0;i<waiterList.size();++i){
    		if(breakBoxes.get(i)==e.getSource() && breakBoxes.get(i).isSelected() && breakBoxes.get(i).isEnabled()){
    			
    			breakBoxes.get(i).setEnabled(false);
    			waiterList.get(i).setWantsBreak(true);
    			//breakBoxes.get(i).setSelected(true);
    			
    			return;
    		}
    		if(breakBoxes.get(i) == e.getSource() && !breakBoxes.get(i).isSelected()){
    			waiterList.get(i).msgBreakOver();
    			return;
    			//breakBoxes.get(i).setSelected(false);
    			//breakBoxes.get(i).setEnabled(true);
    		}
    	}
    	
    }
    
    public void enableWaiter(WaiterRole w){
    	
    	for(int i = 0;i<waiterList.size();++i){
    		if(waiterList.get(i)==w){
    			//breakBoxes.get(i).setSelected(false);
    			breakBoxes.get(i).setEnabled(true);
    			return;
    		}
    	}
    }
    
    public void resetWaiter(WaiterRole w){
    	//System.out.println("In here");
    	//this comment will do nothing
    	for(int i = 0;i<waiterList.size();++i){
    		if(waiterList.get(i)==w){
    			//System.out.println("HI "+w.getName());
    			breakBoxes.get(i).setEnabled(true);
    			breakBoxes.get(i).setSelected(false);
    			//return;
    		}
    	}
    }
    
    private void addWaiter(String name){
    	
    	JLabel nameLabel = new JLabel(name);
    	JCheckBox breakButton = new JCheckBox();
    	breakButton.addActionListener(this);
    	JPanel p = new JPanel();
    	p.add(nameLabel);
    	p.add(breakButton);
    	
    	
    	breakBoxes.add(breakButton);
    	view.add(p);
    	
    	//instantiate waiter and gui
    	StandWaiterRole newW = new StandWaiterRole(name);
    	newW.setStand(this.restPanel.gui.stand);
    	
    	
    	WaiterGui waiterGui= new WaiterGui(newW,numWaiters);
    	numWaiters++;
    	//System.out.println(""+numWaiters);
    	newW.setGui(waiterGui);
    	newW.setWaiterPanel(this);
    	waiterList.add(newW);
    	
    	//connect waiter to other agents
    	newW.setCook(cook);
    	newW.setHost(host);
    	newW.setCashier(cashier);
    	restPanel.host.addWaiter(newW);
    	
    	//add waiter gui to animation
    	restPanel.gui.animationPanel.addGui(waiterGui);
    	
    	//tell waiter where the tables are
    	waiterGui.xTables = restPanel.xTables;
    	waiterGui.yTables = restPanel.yTables;
    	
    	//start waiter
    	newW.startThread();
    	

    	validate();
    }
}
