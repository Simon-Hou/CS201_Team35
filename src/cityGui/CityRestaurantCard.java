package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;




public class CityRestaurantCard extends CityCard{
	//initial values
	public static final int CARD_WIDTH = 500, CARD_HEIGHT = 500;
	public static final int xTableUnit = 50;
	public static final int yTableUnit = 250;
	public static final int xSize = 50;
	public static final int ySize = 50;
	public static final int deskXPos=300;
	public static final int deskYPos=40;
	public static final int deskX=30;
	public static final int deskY=100;
	public static final int grillY=30;
	public static final int fridgeY=20;
	
	private List<Gui> guis = new ArrayList<Gui>();


	public CityRestaurantCard(SimCityGui city) {
		super(city);
		// TODO Auto-generated constructor stub
	}


	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, CARD_WIDTH, CARD_HEIGHT );

		//Here is the table
		g.setColor(Color.ORANGE);
		g.fillRect(xTableUnit, yTableUnit, xSize, ySize);//200 and 250 need to be table params

		//more tables
		g.fillRect(3*xTableUnit, yTableUnit, xSize, ySize);//200 and 250 need to be table params
		g.fillRect(5*xTableUnit, yTableUnit, xSize, ySize);//200 and 250 need to be table params

		//desks!
		g.setColor(Color.BLUE);
		g.fillRect(deskXPos, deskYPos, deskX,deskY);

		//grills
		g.setColor(Color.GRAY);
		g.fillRect(deskXPos+80, deskYPos, deskX, grillY);
		g.fillRect(deskXPos+70, deskYPos+33, deskX, grillY);
		g.fillRect(deskXPos+60, deskYPos+66, deskX, grillY);

		//refrigerator
		g.setColor(Color.WHITE);
		g.fillRect(deskXPos+30,deskYPos-20,deskX,fridgeY);

	}
	
}
