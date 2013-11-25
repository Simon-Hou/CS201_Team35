package cityGui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import city.CityObject;


public abstract class CityComponent {

	//Consider creating a rectangle for every Component for better universal collision detection

	CityObject cityObject;
	
	protected Rectangle rectangle;
	int x, y;
	Color color;
	String ID;
	boolean isActive;

	public CityComponent() {
		x = 0;
		y = 0;
		color = Color.black;
		ID = "";
		isActive = true;
	}
	

	public CityComponent(int x, int y) {
		this.x = x;
		this.y = y;
		color = Color.black;
		ID = "";
		isActive = true;
	}

	public CityComponent(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		color = c;
		ID = "";
		isActive = true;
	}

	public CityComponent(int x, int y, Color c, String I) {
		this.x = x;
		this.y = y;
		color = c;
		ID = I;
		isActive = true;
	}

	public abstract void updatePosition();

	public void paint(Graphics g) {
		if (isActive) {
			g.setColor(color);
			g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
	}
	
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}
	
	public boolean contains(Point p) {
		return contains((int)p.getX(), (int)p.getY());
	}
	
	public void disable() {
		isActive = false;
	}
	
	public void enable() {
		isActive = true;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setX(int x) {
		this.x = x;
		rectangle.setLocation(x, y);
	}

	public void setY(int y) {
		this.y = y;
		rectangle.setLocation(x, y);
	}
	
	public void addAgentObjectToMap(){
		
	}

	public void setPosition(Point p) {
		this.x = p.x;
		this.y = p.y;
		rectangle.setLocation(p);
	}
	
	public int sidewalkX(int x, int y) {
		if (leftSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 200;
			}
			else return 40;
		}
		else if (rightSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 400;
			}
			else return 560;	
		}
		else return x+17;	
	}
	
	public int sidewalkY(int x, int y) {
		if (topSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 200;
			}
			else return 40;
		}
		else if (bottomSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 400;
			}
			else return 560;	
		}
		else return y+17;	
	}
	
	public boolean innerBuilding(int x, int y) {
		if (x >= 200 && x <= 400 && y >= 200 && y <= 400) {
			return true;
		}
		return false;
	}
	
	public boolean leftSide(int x, int y) {
		if (((x >= 200 && x <= 210) || (x >= 0 && x <= 10)) && (y >= 200 && y <= 365)) {
			return true;
		}
		return false;
	}
	
	public boolean rightSide(int x, int y) {
		if (((x >= 360 && x <= 370) || (x >= 560 && x <= 575)) && (y >= 200 && y <= 365)) {
			return true;
		}
		return false;
	}
	
	public boolean topSide(int x, int y) {
		if (((y >= 0 && y <= 10) || (y >= 200 && y <= 210)) && (x > 5 && x < 560)) {
			return true;
		}
		return false;
	}
	
	public boolean bottomSide(int x, int y) {
		if (((y >= 560 && y <= 575) || (y >= 360 && y <= 370)) && (x > 5 && x < 560)) {
			return true;
		}
		return false;
	}
}

