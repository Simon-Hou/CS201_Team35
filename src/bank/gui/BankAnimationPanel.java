package bank.gui;

import javax.swing.*;
import javax.swing.Timer;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;

public class BankAnimationPanel extends JPanel implements ActionListener{

	private final int WINDOWX = 750;
	private final int WINDOWY = 550;
	static final int TableX = 200;//a global for the x position of the table.
	static final int TableY = 250;//a global for the y position of the table.
	static final int TableWidth = 50;//a global for the x position of the table.
	static final int TableHeight = 50;//a global for the y position of the table.

	private Image bufferImage;
	private Dimension bufferSize;
	//insert a list of tables in here from HostAgent that is updated, so that it can get them all.
	private List<Gui> guis = new ArrayList<Gui>();
	//public Collection<tellerWindow> tellerWindows;? easily take out.

	public BankAnimationPanel() {
		setSize(WINDOWX, WINDOWY);
		setVisible(true);

		bufferSize = this.getSize();

		Timer timer = new Timer(50, this );
		timer.start();
	}


	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}


	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(getBackground());
		g2.fillRect(0, 0, WINDOWX, WINDOWY );

		//        //Here is the table//replace with what I want to draw.
		//        g2.setColor(Color.ORANGE);
		//        for (Table table : collTables)
		//        {
		//        	//System.out.println(table.getTableNumber());
		//        	g2.fillRect(table.getXPos(), table.getYPos(), TableWidth, TableHeight);//200 and 250 need to be table params
		//        }
		//
		//        for(Gui gui : guis) {
		//            if (gui.isPresent()) {
		//            	for (Table table : collTables)
		//            	{
		//            		gui.updatePosition(table.getXPos(), table.getYPos());
		//            	}
		//            }
		//        }

		for(Gui gui : guis) {
			if (gui.isPresent()) {
				gui.draw(g2);
			}
		}
	}

	public void addGui(BankCustomerGui gui) {
		guis.add(gui);
	}

	public void addGui(BankTellerGui gui) {
		guis.add(gui);
	}



}
