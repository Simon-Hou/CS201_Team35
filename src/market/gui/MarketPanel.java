package market.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;




public class MarketPanel extends JPanel implements ActionListener{

	private MarketAnimation animation;
	
	private JLabel title = new JLabel("Market");
	private List<InventoryItem> inventoryList = new ArrayList<InventoryItem>();
	public JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    

    private InventoryItem button1 = new InventoryItem("Steak", this);
    private InventoryItem button2 = new InventoryItem("Chicken", this);
    
    private JButton startButton = new JButton("Enter");
	
	public MarketPanel(){
		
        setLayout(new FlowLayout());
		add(title);
		
		view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
		pane.setViewportView(view);
        add(pane);
		
        //^^^^^^^^^^  good. now to add the button. make new class that has (-, name, invent, +)
       
      
       // button1.setBackground(Color.white);
   //button1.setForeground(Color.white);
        
        
       
        Dimension paneSize = new Dimension (400,200);
        pane.setPreferredSize(paneSize);
        pane.setMinimumSize(paneSize);
        pane.setMaximumSize(paneSize);
        
      

         Dimension buttonSize = new Dimension(paneSize.width-20, (int) (paneSize.height / 10));
         
         button1.setPreferredSize(buttonSize);
         button1.setMinimumSize(buttonSize);
         button1.setMaximumSize(buttonSize);
         inventoryList.add(button1);
         view.add(button1);
         
         
         button2.setPreferredSize(buttonSize);
         button2.setMinimumSize(buttonSize);
         button2.setMaximumSize(buttonSize);
         inventoryList.add(button2);
         view.add(button2);
		
         validate();
         
         startButton.addActionListener(this);
         add(startButton);
         
		
	}
	
	
	public void conformSize(){
		
	}
	
	public void actionPerformed(ActionEvent e) {
		System.err.println("Something was pressed.");
		
		if (e.getSource() == startButton){
			System.out.println("A customer has entered the market");
			animation.enterCustomer();
		}
		
		for (InventoryItem item : inventoryList){
			if (e.getSource() == item.minus && item.inventory >0){
				//System.err.println("Minus button pressed.");
				item.inventory--;
				item.inventoryLabel.setText(item.inventory.toString());
			}
			else if (e.getSource() == item.plus){
				//System.err.println("Plus button pressed.");
				item.inventory++;
				item.inventoryLabel.setText(item.inventory.toString());
			}
		}
		
	}

	//Utilities
	
	private class InventoryItem extends JPanel{
		
		MarketPanel mp;
		
		String choice;
		Integer inventory;
		
		JButton minus = new JButton("-");
		JLabel choiceLabel = new JLabel();
		JLabel inventoryLabel = new JLabel();
		JButton plus = new JButton("+");
		
		InventoryItem(String name, MarketPanel mp){
			this.mp = mp;
			choice = name;
			inventory = 0;
			
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
