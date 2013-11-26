package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import person.PersonAgent;
import restaurant.restaurantLinda.gui.AnimationPanel;
import util.CityMap;
import cityGui.test.PersonGui;

public abstract class SimCityPanel extends JPanel implements ActionListener, MouseListener {

	protected SimCityGui city;
	public ArrayList<CityComponent> statics, movings;
	protected Color background;
	protected Timer timer;
		
	public SimCityPanel(SimCityGui city) {
		this.city = city;
		statics = new ArrayList<CityComponent>();
		movings = new ArrayList<CityComponent>();
		timer = new Timer(10, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		moveComponents();
		drawComponents(g);
		
	}
	
	public void drawComponents(Graphics g) {
		for (CityComponent c:statics) {
			c.paint(g);
		}
		
		for (CityComponent c:movings) {
			c.paint(g);
		}
	}
	
	public void moveComponents() {
		for (CityComponent c:movings) {
			c.updatePosition();
		}
	}
	
	public void addStatic(CityComponent c) {
		statics.add(c);
	}
	
	public void addMoving(CityComponent c) {
		movings.add(c);
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	

}
