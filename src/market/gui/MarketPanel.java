package market.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class MarketPanel extends JPanel implements ActionListener{

	private JLabel title = new JLabel("Market");
	private JPanel inventoryPanel = new JPanel ();
	private List<JButton> inventoryList = new ArrayList<JButton>();
	public JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    
    private JButton button1 = new JButton("Steak");
	
	
	public MarketPanel(){
		pane.setViewportView(view);
		add(title);
		add(inventoryPanel);
		
		 Dimension paneSize = pane.getSize();
         Dimension buttonSize = new Dimension(paneSize.width - 20,
                 (int) (paneSize.height / 3));
         button1.setPreferredSize(buttonSize);
         button1.setMinimumSize(buttonSize);
         button1.setMaximumSize(buttonSize);
         button1.addActionListener(this);
         inventoryList.add(button1);
         view.add(button1);
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
