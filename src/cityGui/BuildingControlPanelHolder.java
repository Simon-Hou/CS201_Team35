package cityGui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;



public class BuildingControlPanelHolder extends JPanel{
	final private int PANELX = 180;
	final private int PANELY = 500;
	
	JPanel cards;
	CardLayout layout;
	
	//private BuildingControlPanel panel = new BuildingControlPanel();
	
	
	public BuildingControlPanelHolder(){
		
		Dimension dim = new Dimension(PANELX, PANELY); //x value can't be over 180
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		
		layout = new CardLayout();
		cards = new JPanel(layout);
		add(cards);
		
	
	}
	
	public void addPanelCard(BuildingControlPanel p){
		cards.add(p, "hi");
		layout.show(cards , "hi");
		
	}
	
	
}



