package restaurant.restaurantLinda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class ListPanel extends JPanel implements ActionListener, KeyListener {

    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    private List<JButton> list = new ArrayList<JButton>();
    private JButton addPersonB = new JButton("Add");
    private JTextField nameField = new JTextField(15);
    private JCheckBox hungryBox = new JCheckBox("Hungry?");
    
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
        JLabel temp;

        setPreferredSize(new Dimension((int)(RestaurantGui.WINDOWX*.2),(int)(RestaurantGui.WINDOWY*.35)));
        setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
        temp = new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>", SwingConstants.CENTER);      
        temp.setAlignmentX(Component.CENTER_ALIGNMENT);  
        add(temp);
        
    	temp = new JLabel("Please enter a name");
    	temp.setAlignmentX(Component.CENTER_ALIGNMENT);
    	add(temp);
    	
    	nameField.setMaximumSize(new Dimension((int)(RestaurantGui.WINDOWX*.25),(int)(RestaurantGui.WINDOWY*.05)));
    	nameField.addKeyListener(this);
    	add(nameField);
    	
    	JPanel p = new JPanel(new FlowLayout());
    	p.setPreferredSize(new Dimension((int)(RestaurantGui.WINDOWX*.2),(int)(RestaurantGui.WINDOWY*.1)));
    	if (type=="Customers"){	 
    		hungryBox.setEnabled(false);
    		p.add(hungryBox);     	
    	}
        addPersonB.addActionListener(this);
        p.add(addPersonB);        
        add(p);
        
        pane.setPreferredSize(new Dimension((int)(RestaurantGui.WINDOWX*.2),(int)(RestaurantGui.WINDOWY*.25))); //To fix the size
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
            //addPerson(JOptionPane.showInputDialog("Please enter a name:"));
        	
        	addPerson(nameField.getText(), hungryBox.isSelected());
        	nameField.setText(null);
        	hungryBox.setSelected(false);
        	hungryBox.setEnabled(false);
        }
        else {
        	// Isn't the second for loop more beautiful?
            /*for (int i = 0; i < list.size(); i++) {
                JButton temp = list.get(i);*/
        	for (JButton temp:list){
                if (e.getSource() == temp)
                    restPanel.showInfo(type, temp.getText());
            }
        }
    }
    
    public void keyPressed(KeyEvent e){
    	if (nameField.getText().trim().isEmpty())
    		hungryBox.setEnabled(false);
    	else
    		hungryBox.setEnabled(true);	
    }
    
	public void keyReleased(KeyEvent e) {
		if (nameField.getText().trim().isEmpty())
    		hungryBox.setEnabled(false);
    	else
    		hungryBox.setEnabled(true);			
	}

	public void keyTyped(KeyEvent e) {
		if (nameField.getText().trim().isEmpty())
    		hungryBox.setEnabled(false);
    	else
    		hungryBox.setEnabled(true);		
	}

    /**
     * If the add button is pressed, this function creates
     * a spot for it in the scroll pane, and tells the restaurant panel
     * to add a new person.
     *
     * @param name name of new person
     */
    public void addPerson(String name, boolean hungry) {
        if (!name.trim().isEmpty()) {
        	JButton button = new JButton(name);
            button.setBackground(Color.white);

            Dimension paneSize = pane.getSize();
            Dimension buttonSize = new Dimension(paneSize.width - 20,
                    (int) (paneSize.height / 5));
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.addActionListener(this);
            list.add(button);
            view.add(button);
            restPanel.addPerson(type, name, hungry);//puts customer on list
            //restPanel.showInfo(type, name);//puts hungry button on panel
            validate();
        }
    }

	
}
