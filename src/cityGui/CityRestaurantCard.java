package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import restaurant.restaurantLinda.gui.Gui;
import restaurant.restaurantLinda.gui.MyImage;
import restaurant.restaurantLinda.gui.RestaurantPanel;




public class CityRestaurantCard extends CityCard{
	//initial values
	public static final int CARD_WIDTH = 500, CARD_HEIGHT = 500;
	public static final Rectangle REFRIGERATOR = new Rectangle(CARD_WIDTH-100, 0, 79, 30);
    public static final Rectangle STOVE = new Rectangle(CARD_WIDTH-100, CARD_HEIGHT-50, 80, 50);
    public static final int TABLESIZE=50;
    public static final int PERSONSIZE=25;
    
    private List<Gui> guis = new ArrayList<Gui>();
    private Collection<Point> tableMap = new ArrayList<Point>();
    public List<MyImage> platedFoods = Collections.synchronizedList(new ArrayList<MyImage>());


	public CityRestaurantCard(SimCityGui city) {
		super(city);
		// TODO Auto-generated constructor stub
	}


	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(getBackground());
		g2.fillRect(0, 0, CARD_WIDTH, CARD_HEIGHT );

		

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }


        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g);
            }
        }
        
        //The plated foods
        synchronized(platedFoods){
        	for (MyImage plate: platedFoods){
        		plate.draw(g);
        	}
        }
        
        //The cashier
        g.setColor(Color.BLUE);
        g.fillRect(0, 100, PERSONSIZE, PERSONSIZE);
    }
        
}
	

