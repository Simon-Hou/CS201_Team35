package restaurantLinda.gui;

import restaurantLinda.CustomerRole;
import restaurantLinda.WaiterRole;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener {
	public static int WINDOWX = 1000;
    public static int WINDOWY = 600;
	
	/* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	JFrame animationFrame = new JFrame("Restaurant Animation");
	AnimationPanel animationPanel = new AnimationPanel();
	
    /* restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     */    
    private RestaurantPanel restPanel = new RestaurantPanel(this);
    
    /* infoPanel holds information about the clicked customer, if there is one*/
    private JPanel infoPanel = new JPanel();
    private JLabel infoLabel = new JLabel();
    private JCheckBox stateCB = new JCheckBox();//part of infoLabel
    private JButton breakButton = new JButton("Ask for break");
    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */
    
    private JButton pauseButton = new JButton("Pause");

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() {
        
    	setBounds(50, 50, WINDOWX, WINDOWY);

    	setLayout(new BoxLayout((Container) getContentPane(),
    	         BoxLayout.Y_AXIS));

        Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * .37));
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);
        add(restPanel,BorderLayout.NORTH);
                
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER,50,10));
        p.setPreferredSize(new Dimension(WINDOWX, (int) (WINDOWY * .13)));
        
     // Now, setup the info panel
        Dimension infoDim = new Dimension((int)(WINDOWX*.7), (int) (WINDOWY * .1));
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));

        stateCB.setVisible(false);
        stateCB.addActionListener(this);
        
        breakButton.setVisible(false);
        breakButton.addActionListener(this);

        infoPanel.setLayout(new GridLayout(1, 2, 30, 0));
        
        infoLabel = new JLabel("<html><pre><i>Click Add to make customers</i></pre></html>"); 
        infoPanel.add(infoLabel);
        infoPanel.add(stateCB); 
        infoPanel.add(breakButton);
        p.add(infoPanel);
        pauseButton.addActionListener(this);
        p.add(pauseButton);       
        add(p);
        animationPanel.setPreferredSize(new Dimension(WINDOWX, (int) (WINDOWY * .5)));
        animationPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        add(animationPanel); 
    }
    /**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
    public void updateInfoPanel(Object person) {
        stateCB.setVisible(true);
        currentPerson = person;
        breakButton.setVisible(false);

        if (person instanceof CustomerRole) {
            CustomerRole customer = (CustomerRole) person;
            stateCB.setText("Hungry?");
          //Should checkmark be there? 
            stateCB.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
            stateCB.setEnabled(!customer.getGui().isHungry());
          // Hack. Should ask customerGui
            infoLabel.setText(
               "<html><pre>     Name: " + customer.getName() + " </pre></html>");
        }
        else if (person instanceof WaiterRole){
        	WaiterRole waiter = (WaiterRole) person;
        	WaiterRole.BreakStatus status = waiter.getBreakStatus();
        	switch(status){
        		case none:
        		case finished:
        			breakButton.setText("Ask for break");
        			breakButton.setEnabled(true);
        			break;
        		case wantBreak:
        		case asked:
        			breakButton.setText("Asking host");
        			breakButton.setEnabled(false);
        			break;
        		case hasPermission:
        		case onBreak:
        			breakButton.setText("Cancel break");
        			breakButton.setEnabled(true);
        			break;
        	}
        	
        	breakButton.setVisible(true);
        	//stateCB.setText("On Break?");
        	stateCB.setText(waiter.getGui().getPosition());
        	stateCB.setSelected(status==WaiterRole.BreakStatus.onBreak);
        	stateCB.setEnabled(false);
        	infoLabel.setText(
                    "<html><pre>     Name: " + waiter.getName() + " </pre></html>");
        }
        infoPanel.validate();
    }
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stateCB) {
            if (currentPerson instanceof CustomerRole) {
                CustomerRole c = (CustomerRole) currentPerson;
                c.getGui().setHungry();
                stateCB.setEnabled(false);
            }
        }
        else if (e.getSource() == breakButton){
        	if (currentPerson instanceof WaiterRole){
        		WaiterRole w = (WaiterRole) currentPerson;
        		if (breakButton.getText().equals("Ask for break")){
        			breakButton.setText("Asking host for break");
        			breakButton.setEnabled(false);
        			w.msgWantBreak();
        		}
        		else if (breakButton.getText().equals("Cancel break")){
        			breakButton.setText("Ask for break");
        			w.msgBreakDone();
        		}
        	}
        }
        else if (e.getSource() == pauseButton){
        	if (pauseButton.getText().equals("Pause")){
        		animationPanel.pause();
        		restPanel.pause();
        		pauseButton.setText("Restart");
        	}
        	else {
        		animationPanel.restart();
        		restPanel.restart();
        		pauseButton.setText("Pause");
        	}
        		
        }
    }
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
    public void setCustomerEnabled(CustomerRole c) {
        if (currentPerson instanceof CustomerRole) {
            CustomerRole cust = (CustomerRole) currentPerson;
            if (c.equals(cust)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
        }
    }
    
    public void updateWaiter(WaiterRole w){
    	if (currentPerson instanceof WaiterRole){
    		WaiterRole waiter = (WaiterRole) currentPerson;
    		if (w.equals(waiter)){
    			WaiterRole.BreakStatus status = waiter.getBreakStatus();
            	switch(status){
            		case none:
            		case finished:
            			breakButton.setText("Ask for break");
            			breakButton.setEnabled(true);
            			break;
            		case wantBreak:
            		case asked:
            			breakButton.setText("Asking host");
            			breakButton.setEnabled(false);
            			break;
            		case hasPermission:
            		case onBreak:
            			breakButton.setText("Cancel break");
            			breakButton.setEnabled(true);
            			break;
            	}
            	stateCB.setSelected(status==WaiterRole.BreakStatus.onBreak);
    		}
    	}
    }
    
    
    /**
     * Main routine to get gui started
     */
    public static void main(String[] args) {
        RestaurantGui gui = new RestaurantGui();
        gui.setTitle("csci201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
