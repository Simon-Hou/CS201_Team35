package restaurantLinda.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class AnimationPanel extends JPanel implements ActionListener {

    public static final int WINDOWX = (int)(RestaurantGui.WINDOWX*.95);
    public static final int WINDOWY = (int)(RestaurantGui.WINDOWY*.45);
    public static final Rectangle REFRIGERATOR = new Rectangle(WINDOWX-80, 0, 80, 30);
    public static final Rectangle STOVE = new Rectangle(WINDOWX-80, WINDOWY-50, 80, 50);
    public static final int TABLESIZE=50;
    public static final int PERSONSIZE=25;
    
    private Timer timer = new Timer(15, this);

    private List<Gui> guis = new ArrayList<Gui>();
    private Collection<Point> tableMap = new ArrayList<Point>();
    public List<MyImage> platedFoods = Collections.synchronizedList(new ArrayList<MyImage>());
    
    public AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);  
    	
    	timer.start();
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX+50, WINDOWY+50);
        
        //Here is the cook's area       
        g2.setColor(Color.WHITE);
        g2.fillRect(REFRIGERATOR.x, REFRIGERATOR.y, REFRIGERATOR.width+20, REFRIGERATOR.height);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(STOVE.x, STOVE.y, STOVE.width+20, STOVE.height);
        g2.draw3DRect(REFRIGERATOR.x, REFRIGERATOR.y, REFRIGERATOR.width+20, REFRIGERATOR.height, true);
        g2.drawString("Refrigerator", WINDOWX-60, 20);
        g2.setColor(Color.GRAY);
        g2.fillRect(WINDOWX-150, 0, 50, WINDOWY);
        g2.drawOval(WINDOWX-72, WINDOWY-50, 24, 24);
        g2.drawOval(WINDOWX-41, WINDOWY-50, 24, 24);
        g2.drawOval(WINDOWX-10, WINDOWY-50, 24, 24);
        g2.drawOval(WINDOWX-72, WINDOWY-26, 24, 24);
        g2.drawOval(WINDOWX-41, WINDOWY-26, 24, 24);
        g2.drawOval(WINDOWX-10, WINDOWY-26, 24, 24);
        
        int cellSize = RestaurantPanel.cellSize;
        int gridX = WINDOWX/cellSize;
        int gridY = WINDOWY/cellSize;
        
        for (int i=0; i<gridX ; i++)
    	    for (int j = 0; j<gridY; j++){
    	    	g2.drawRect(i*cellSize, j*cellSize, cellSize, cellSize);
    	    	g2.drawString(i*cellSize + " " + j*cellSize, i*cellSize+5, j*cellSize+PERSONSIZE);
    	    }

        //Here are the tables
        for (Point table: tableMap)
		{
        	g2.setColor(Color.ORANGE);
        	g2.fillRect(table.x, table.y, TABLESIZE, TABLESIZE);
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
        
        //The plated foods
        synchronized(platedFoods){
        	for (MyImage plate: platedFoods){
        		plate.draw(g2);
        	}
        }
        
        //The cashier
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 100, PERSONSIZE, PERSONSIZE);
    }
    
    public void pause(){
    	timer.stop();
    }
    
    public void restart(){
    	timer.start();
    }

    public void addGui(Gui gui) {
        guis.add(gui);
    }
    
    public void addTable(Point p){
    	tableMap.add(p);
    }
}
