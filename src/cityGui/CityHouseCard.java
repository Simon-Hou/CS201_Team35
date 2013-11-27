package cityGui;

import house.gui.InhabitantGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import public_Gui.Gui;

public class CityHouseCard extends CityCard{

	
	public static final int xSize = 50;
	public static final int ySize = 50;
	
	private List<Gui> guis = new ArrayList<Gui>();
	
	public CityHouseCard(SimCityGui city) {
		super(city);
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(getBackground());
		g2.fillRect(0, 0, CARD_WIDTH, CARD_HEIGHT );
		
		//Here is the table
		g2.setColor(Color.ORANGE);
		g2.fillRect((int)(CARD_WIDTH*0.5), (int)(CARD_HEIGHT*0.5), xSize, ySize);//200 and 250 need to be table params

		//refrigerator
		g2.setColor(Color.WHITE);
		g2.fillRect((int)(CARD_WIDTH*0.1),(int)(CARD_HEIGHT*0.5),xSize,ySize);
		
		for(Gui gui : guis) {
			//if (gui.isPresent()) {
				gui.updatePosition();
			//}
		}

		for(Gui gui : guis) {
			//if (gui.isPresent()) {
				gui.draw(g2);
			//}
		}

	}
	public void run(long t){
		for(long i=0; i<t-time;i++){
			for(Gui gui : guis) {
				//if (gui.isPresent()) {
					gui.updatePosition();
				//}
			}
		}
	}
	public void addGui(InhabitantGui gui) {
		guis.add(gui);
	}
	



}
