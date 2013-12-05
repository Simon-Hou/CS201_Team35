package restaurant.restaurantGabe.gui;

//import restaurant.OldCustomerAgent;
//import restaurant.OldHostAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class ListPanel extends JPanel implements ActionListener {

    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    
    
    private JPanel view = new JPanel();
    public List<JButton> list = new ArrayList<JButton>();
    public List<JCheckBox> hungryCBList = new ArrayList<JCheckBox>();
    
    private JButton addPersonB = new JButton("Add");
    private JLabel hungryLabel = new JLabel("Hungry?:");
    private JCheckBox addHungryCB = new JCheckBox();
    
    private JTextField nameField = new JTextField();

    private RestaurantPanel restPanel;
    private String type;

    /**
     * Constructor for ListPanel.  Sets up all the gui
     *
     * @param rp   reference to the restaurant panel
     * @param type indicates if this is for customers or waiters
     */
    public ListPanel(RestaurantPanel rp, String type) {
        restPanel = rp;
        this.type = type;

        setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
        add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));

        JPanel creationDock = new JPanel();
        Dimension creationDim = new Dimension(500,20);
        creationDock.setMaximumSize(creationDim);
        creationDock.setLayout(new BoxLayout ((Container) creationDock, BoxLayout.X_AXIS));
        creationDock.add(addPersonB);
        creationDock.add(hungryLabel);
        creationDock.add(addHungryCB);
        
        
        addPersonB.addActionListener(this);
        add(creationDock);
        nameField.setMaximumSize(new Dimension(500,30));
        add(nameField);
        

        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        pane.setViewportView(view);
        add(pane);
    }

    /**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPersonB) {
        	// Chapter 2.19 describes showInputDialog()
        	//inputPanel q = new inputPanel();
        	
            //addPerson(JOptionPane.showInputDialog("Please enter a name:"));
            
        	String name = nameField.getText();
        	if(name.length()==0){
        		return;
        	}
        	else{
        		for(int i = 1;i<10;++i){
                	if(nameField.getText().equals(""+ i)){
                		for(int cust = 0;cust<i;cust++){
                			addPerson("c"+cust);
                		}
                		return;
                	}
                }
        		addPerson(name);
        	}
        	
        	
        }
        else {
        	// Isn't the second for loop more beautiful?
            /*for (int i = 0; i < list.size(); i++) {
                JButton temp = list.get(i);*/
        	/*for (JButton temp:list){
                if (e.getSource() == temp)
                    restPanel.showInfo(type, temp.getText());
            }*/
        	int i = 0;
        	for (JCheckBox cb:hungryCBList){
        		/*if(cb.isSelected()){
        			//System.out.print("HELLO");
        			cb.setEnabled(false);
        			cb.setSelected(true);
        			restPanel.setCustHungry(list.get(i).getText());
        			i+=1;
        			continue;
        		}*/
        		if(cb==e.getSource()){
        			cb.setEnabled(false);
        			cb.setSelected(true);
        			//System.out.println("New Person" + list.get(i).getText());
        			restPanel.setCustHungry(list.get(i).getText());
        			break;
        		}
        		i+=1;
        	}
        	
        	
        	//cb.setSelected(false);
        }
    }

    /**
     * If the add button is pressed, this function creates
     * a spot for it in the scroll pane, and tells the restaurant panel
     * to add a new person.
     *
     * @param name name of new person
     */
    public void addPerson(String name) {
        if (name != null) {
            JButton button = new JButton(name);
            button.setBackground(Color.white);

            Dimension paneSize = pane.getSize();
            Dimension buttonSize = new Dimension(paneSize.width - 20,
                    (int) (paneSize.height / 7));
            //button.setPreferredSize(buttonSize);
            //button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.addActionListener(this);
            list.add(button);
            
            JCheckBox cb = new JCheckBox();
            cb.setEnabled(true);
            cb.addActionListener(this);
            hungryCBList.add(cb);
            
            //System.out.println("Added a new person");
            
            
            JPanel p  = new JPanel();
            p.setLayout(new FlowLayout());
            p.add(button);
            p.add(cb);
            view.add(p);
            //view.add(button);
            restPanel.addPerson(type, name);//puts customer on list
            
            if(addHungryCB.isSelected()){
            	addHungryCB.setSelected(false);
            	restPanel.setCustHungry(name);
            	cb.setSelected(true);
            	cb.setEnabled(false);
            }
            //restPanel.showInfo(type, name);//puts hungry button on panel
            validate();
        }
    }
}
