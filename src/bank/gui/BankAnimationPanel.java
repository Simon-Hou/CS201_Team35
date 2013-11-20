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
	
	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}
}
