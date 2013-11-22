package market.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;


public class MarketMain extends JFrame{
	

	
	MarketMain(){
	    int WINDOWX = 500;
	    int WINDOWY = 500;	
		setBounds(50, 50, WINDOWX,WINDOWY);
		setLayout(new FlowLayout());
		
		MarketPanel mp = new MarketPanel();
        Dimension mpSize = new Dimension (WINDOWX, WINDOWY);
        mp.setPreferredSize(mpSize);
        mp.setMinimumSize(mpSize);
        mp.setMaximumSize(mpSize);	
		add(mp);
		
		
	}

	public static void main(String[] args) {

		MarketMain gui = new MarketMain();
        gui.setTitle("Market Panel");
        gui.setVisible(true);
        gui.setResizable(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
