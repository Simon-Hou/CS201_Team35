package restaurantLinda.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TablePanel extends JPanel implements ActionListener
{
	private RestaurantPanel restPanel;
	private Map<Integer,Point> tableMap;
	private JComboBox tableSizeList = new JComboBox(new Object[] {1,2,3,4,5});
    private JButton addTableB = new JButton("Add Table");
    private JTextField Tablex = new JTextField(8);
    private JTextField Tabley = new JTextField(8);
    private JLabel messageLabel = new JLabel("                     "); //Spaces force this to go on its own line
	
    public TablePanel(RestaurantPanel rp)
    {
    	restPanel=rp;
    	JLabel tempLabel;
    	
    	setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
    	tempLabel = new JLabel("<html><pre> <u>Tables</u><br></pre></html>", SwingConstants.CENTER);
    	tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(tempLabel);
        
        JPanel p = new JPanel(new FlowLayout());
        p.add(new JLabel("Please select the table size"));
        tableSizeList.setSize(200, 50);
        p.add(tableSizeList);  
     
        add(p);        
        addTableB.addActionListener(this);
        addTableB.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(addTableB);
        
        tempLabel = new JLabel("Table Location: ");
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(new JPanel()); 	//Just a filler
        add(tempLabel);
        
        JPanel xlocations = new JPanel(new FlowLayout());
        xlocations.add(new JLabel("X"));
        xlocations.add(Tablex);
        
        JPanel ylocations = new JPanel(new FlowLayout());
        ylocations.add(new JLabel("Y"));
        ylocations.add(Tabley);
        
        add(xlocations);
        add(ylocations);
        
        add(messageLabel);
        
        
    }
    
    public void setTables(Map<Integer,Point> map)
    {
    	tableMap=map;
    }
    
    @Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==addTableB)
		{
			int xLoc, yLoc;
			
			try
			{
				xLoc = Integer.parseInt(Tablex.getText().trim());
				yLoc = Integer.parseInt(Tabley.getText().trim());
			}
			catch(Exception ex)
			{
				messageLabel.setText("Not a number");
				return;
			}
			
			if (validateTableLoc(xLoc,yLoc))
			{
				if (restPanel.addTable(tableSizeList.getSelectedIndex()+1,xLoc,yLoc)){
					messageLabel.setText("                     ");
					Tablex.setText(null);
					Tabley.setText(null);
				}
				else
					messageLabel.setText("Could not add table  ");
			}
			else
				messageLabel.setText("Invalid location");
		}
		
	}
	
	private boolean validateTableLoc(int xLoc, int yLoc)
	{		
		if (xLoc<0 || xLoc>AnimationPanel.WINDOWX-AnimationPanel.TABLESIZE-150 || yLoc<0 || yLoc>AnimationPanel.WINDOWY-AnimationPanel.TABLESIZE)
			return false;
		
		for (int table: tableMap.keySet())
		{
			if (xLoc > tableMap.get(table).x-2*AnimationPanel.TABLESIZE && xLoc < tableMap.get(table).x+2*AnimationPanel.TABLESIZE && yLoc > tableMap.get(table).y-2*AnimationPanel.TABLESIZE && xLoc < tableMap.get(table).y+2*AnimationPanel.TABLESIZE)
				return false;
		}
		
		return true;
	}
}
