package restaurant.restaurantGabe.gui;

import restaurant.restaurantGabe.CustomerRole;
import restaurant.restaurantGabe.util.RevolvingStand;

import javax.swing.*;

import agent.Agent;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener /*implements ActionListener*/ {
    /* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	
	//JFrame animationFrame = new JFrame("Restaurant Animation");
	AnimationPanel animationPanel = new AnimationPanel();
	
	JPanel animationFrame = new JPanel();
	
    /* restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     */    
	public RevolvingStand stand = new RevolvingStand();
    private RestaurantPanel restPanel = new RestaurantPanel(this);
    
    
    private JPanel whole = new JPanel();
    
    /* infoPanel holds information about the clicked customer, if there is one*/
    //private JPanel infoPanel;
    //private JLabel infoLabel; //part of infoPanel
    //private JCheckBox stateCB;//part of infoLabel
    //private JPanel namePanel;
    //private JLabel nameLabel;
    private JPanel programStuff;
    
    
    //will tell the customer gui where to go
    public int numWaitingCusts;
    
    public int numWaitingSpots = 10;
    public boolean[] waitingSpots = new boolean[numWaitingSpots];
    
    int[] x_table = {120,195,270};
    int[] y_table = {275,215,155};

    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */
    
    //private JButton pauseButton = new JButton("Pause");

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() {
    	
    	numWaitingCusts = 0;
    	
    	//numWaitingSpots = 10;
    	for(int i = 0;i<numWaitingSpots;i++){
    		waitingSpots[i] = true;
    	}
    	
    	JMenuBar menuBar = new JMenuBar();
    	JMenu options = new JMenu("Options");
    	
    	JMenuItem pause = new JMenuItem(new AbstractAction("Pause") {
    	    public void actionPerformed(ActionEvent e) {
    	        // Button pressed logic goes here
    	    	pauseSystem();	
    	    }
    	});
    	
    	options.add(pause);
    	menuBar.add(options);
    	setJMenuBar(menuBar);
    	
        int WINDOWX = 850;
        int WINDOWY = 850;
        
        //x_table  = {200,300,400};
        //y_table = {250,250,250};
        
        animationPanel.x_table = x_table;
        animationPanel.y_table = y_table;
        
        restPanel.xTables = x_table;
        restPanel.yTables = y_table;
        
        /*restPanel.waiter2Gui.xTables = x_table;
        restPanel.waiter2Gui.yTables = y_table;*/

        int aPanelwidth = 650;
        int aPanelheight = 400;
        animationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        animationPanel.setPreferredSize(new Dimension(aPanelwidth,aPanelheight));
        animationFrame.setLayout(new FlowLayout());
        //animationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //animationFrame.setBounds(100+WINDOWX, 50 , WINDOWX+100, WINDOWY+100);
        animationFrame.setVisible(true);
    	animationFrame.add(animationPanel);
    	
    	int aFramewidth = 750;
    	int aFrameheight = 500;
    	animationFrame.setPreferredSize(new Dimension(aFramewidth,aFrameheight));
    	//animationFrame.add(restaurantPanel)
    	
    	setBounds(50, 50, WINDOWX, WINDOWY);
    	/*whole.setLayout(new BoxLayout(whole,BoxLayout.PAGE_AXIS));
    	whole.setLayout(new BorderLayout());*/
    	
    	//WORKING
    	//whole.setLayout(new BorderLayout());
    	
    	/*Dimension minAnimationSize = new Dimension(700,700);
    	animationPanel.setMinimumSize(minAnimationSize);
    	animationPanel.setBounds(50,50,500,500);*/
    	
    	//WORKING
    	//whole.add(restPanel,BorderLayout.SOUTH);
    	
    	//LOOK AT JMENU, CARDLAYOUT,GRIDBAG
    	int restPanelwidth = 800;
    	int restPanelheight = 200;
    	whole.setLayout(new FlowLayout());
    	whole.add(animationFrame);
    	whole.add(restPanel);
    	//whole.add(pauseButton);
    	//pauseButton.addActionListener(this);
    	Dimension maxRestSize = new Dimension(restPanelwidth,restPanelheight);
    	restPanel.setPreferredSize(maxRestSize);
    	
    	//add(restPanel);
    	//add(animationFrame);
    	add(whole);
    	
    	//programStuff = new JPanel();
    	//programStuff.setLayout(new BorderLayout());

        /*setLayout(new BoxLayout((Container) getContentPane(), 
        		BoxLayout.X_AXIS));*/
    	//setLayout(new GridLayout(2,1,0,30));
        //setLayout(new GridLayout(2,1,0,30));
        //setLayout(new GridLayout(1,2,30,0));
        //setLayout(new FlowLayout());

        //Dimension restDim = new Dimension((int) (WINDOWX*.9), (int) (WINDOWY));
        //restPanel.setPreferredSize(restDim);
        //restPanel.setMinimumSize(restDim);
        //restPanel.setMaximumSize(restDim);
        //programStuff.add(restPanel,BorderLayout.WEST);
        //programStuff
        
        // Now, setup the info panel
        /*Dimension infoDim = new Dimension((int) (WINDOWX*.2), (int) (WINDOWY));
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));*/

        /*stateCB = new JCheckBox();
        stateCB.setVisible(false);
        stateCB.addActionListener(this);*/

        //infoPanel.setLayout(new GridLayout(2, 1, 30, 0));
        
        /*infoLabel = new JLabel(); 
        infoLabel.setText("<html><pre><i>Click Add to \nmake customers</i></pre></html>");
        infoPanel.add(infoLabel);
        infoPanel.add(stateCB);
        programStuff.add(infoPanel,BorderLayout.EAST);*/
        
        //add(programStuff);
        /*namePanel = new JPanel();
        add(namePanel);*/
        
        /*namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        nameLabel = new JLabel();
        nameLabel.setText("<html><pre><i>Gabriel Mel</i></pre></html>");
        namePanel.add(nameLabel);*/
        
        /*ImageIcon pic = new ImageIcon("/Users/gabrielmel/Desktop/pic");
        namePanel.add(pic);*/
        
        
        //add(namePanel);
        
        
    }
    /**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
    /*public void updateInfoPanel(Object person) {
        stateCB.setVisible(true);
        currentPerson = person;

        if (person instanceof CustomerAgent22) {
            CustomerAgent22 customer = (CustomerAgent22) person;
            stateCB.setText("Hungry?");
          //Should checkmark be there? 
            stateCB.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
            stateCB.setEnabled(!customer.getGui().isHungry());
          // Hack. Should ask customerGui
            infoLabel.setText(
               "<html><pre> Name: " + customer.getName() + " </pre></html>");
        }
        infoPanel.validate();
    }*/
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    /*public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stateCB) {
            if (currentPerson instanceof CustomerAgent22) {
                CustomerAgent22 c = (CustomerAgent22) currentPerson;
                c.getGui().setHungry();
                stateCB.setEnabled(false);
            }
        }
    }*/
    
    public void setCustomerHungry(CustomerRole c){
    	//System.out.println("Setting GUI to hungry");
    	c.getGui().setHungry();
    	
    }
    
    //TODO There'll be a bug here if two customers have the same name
    public void updateList(CustomerRole c){
    	String name = c.getName();
    	int i = 0;
    	for(JButton curr_name:restPanel.customerPanel.list){
    		if(name.equals(curr_name.getText())){
    			//System.out.println("At this place");
    			restPanel.customerPanel.hungryCBList.get(i).setSelected(false);
    			restPanel.customerPanel.hungryCBList.get(i).setEnabled(true);
    		}
    		i+=1;
    	}
    }
    
    public void actionPerformed(ActionEvent e){
    	/*if(e.getSource() == pauseButton){
    		pauseSystem();
    		
    	}*/
    }
    
    public void pauseSystem(){
    	List<Agent> people = restPanel.host.peopleList();
		for(Agent a:people){
			a.toggleActive();
		}
    }
    
    
    
    
    
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
    /*public void setCustomerEnabled(CustomerAgent22 c) {
        if (currentPerson instanceof CustomerAgent22) {
            CustomerAgent22 cust = (CustomerAgent222) currentPerson;
            if (c.equals(cust)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
        }
    }*/
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
