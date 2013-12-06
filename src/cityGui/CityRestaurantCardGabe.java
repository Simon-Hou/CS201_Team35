package cityGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import restaurant.restaurantGabe.gui.CookGui;
import restaurant.restaurantGabe.gui.CustomerGui;
//import restaurant.restaurantGabe.gui.Gui;
import restaurant.restaurantGabe.gui.WaiterGui;

public class CityRestaurantCardGabe extends CityRestaurantCard{

	public CityRestaurantCardGabe(SimCityGui city) {
		super(city);
		
//		setSize(WINDOWX, WINDOWY);
//        setVisible(true);
//        
//        bufferSize = this.getSize();
//    	Timer timer = new Timer(TIMER_DELAY, this );
//    	timer.start();
	}
	

	
	private int WINDOWX = 650;
	private int WINDOWY = 550;
    private Image bufferImage;
    private Dimension bufferSize;
    
    private static int TIMER_DELAY = 10;
    
    private static int N_TABLES = 3;

    private List<Gui> guis = new ArrayList<Gui>();

    

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}
	
	int[] x_table; //= {200,300,400};
    int[] y_table;// = {250,250,250};
    int width = 50;
    int height = 50;
    
    int xGrill = 375;
    int yGrill = 125;
    
    int xPlate = 375;
    int yPlate = 55;
    
    int xFridge = 450;
    int yFridge = 85;
    
    int wFridge = 30;
    int hFridge = 30;

    @Override
    public void paint(Graphics g) {
    	//System.out.println("IN HERE");
        Graphics2D g2 = (Graphics2D)g;
        
        
        
        //g2.setClip(0,0,WINDOWX,WINDOWY);
        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );

        //Grills
        g2.setColor(Color.PINK);
        g2.fillRect(xPlate,yPlate, 250, 20);
        g2.fillRect(xGrill,yGrill, 250, 20);
        
        //Fridge
        g2.setColor(Color.blue);
        g2.fillRect(xFridge, yFridge, wFridge, hFridge);
        
        
        //Here is the table
        g2.setColor(Color.ORANGE);
       
        for(int i = 0;i<N_TABLES;++i){
        	g2.fillRect(x_table[i],y_table[i],width, height);//200 and 250 need to be table params
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }
    }

    public void addGui(CustomerGui gui) {
    	System.out.println("Adding a new customer");
        guis.add(gui);
    }

    public void addGui(WaiterGui gui) {
        guis.add(gui);
    }
    
    public void addGui(CookGui gui){
    	guis.add(gui);
    }



}
