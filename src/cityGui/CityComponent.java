package cityGui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import city.CityObject;


public abstract class CityComponent implements ImageObserver {

	//Consider creating a rectangle for every Component for better universal collision detection

	CityObject cityObject;
	String type=null;
	
	public Rectangle rectangle;
	public int x, y;
	Color color;
	String ID;
	boolean isActive;
	public boolean invalidPlacement = false;

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
	public CityComponent(int x, int y, Color c, String I, String t) {
		this.x = x;
		this.y = y;
		color = c;
		ID = I;
		type=t;
		isActive = true;
	}

	public abstract void updatePosition();

	public void paint(Graphics g) {
		if (isActive) {
			g.setColor(color);
			g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			//g.drawImage(image, rectangle.x, rectangle.y, this);
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
	
	public JPanel addAgentObjectToMap(){
		return null;
	}

	public void setPosition(Point p) {
		this.x = p.x;
		this.y = p.y;
		rectangle.setLocation(p);
	}
	
	public int sidewalkX(int x, int y) {
		if (outerLeftSide(x,y) || innerLeftSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 190;
			}
			else return 40;
		}
		else if (outerRightSide(x,y) || innerRightSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 400;
			}
			else return 550;	
		}
		else return x+(this.rectangle.width/2)-5;	
	}
	
	public int sidewalkY(int x, int y) {
		if (outerTopSide(x,y) || innerTopSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 190;
			}
			else return 40;
		}
		else if (outerBottomSide(x,y) || innerBottomSide(x,y)) {
			if (innerBuilding(x,y)) {
				return 400;
			}
			else return 550;	
		}
		else return y+(this.rectangle.height/2)-5;	
	}
	
	public boolean innerBuilding(int x, int y) {
		if (x >= 200 && x <= 400 && y >= 200 && y <= 400) {
			return true;
		}
		return false;
	}
	
	public boolean innerLeftSide(int x, int y) {
		if ((x >= 200 && x <= 205) && (y >= 200 && y <= 370)) {
			return true;
		}
		return false;
	}
	
	public boolean innerRightSide(int x, int y) {
		if ((x >= 360 && x <= 365) && (y >= 200 && y <= 370)) {
			return true;
		}
		return false;
	}
	
	public boolean innerTopSide(int x, int y) {
		if ((y >= 200 && y <= 205) && (x > 205 && x < 360)) {
			return true;
		}
		return false;
	}
	
	public boolean innerBottomSide(int x, int y) {
		if ((x > 205 && x < 360) && (y >= 360 && y <= 365)) {
			return true;
		}
		return false;
	}
	public boolean outerLeftSide(int x, int y) {
		if ((x >= 0 && x <= 40) && (y >= 40 && y <= 560)) {
			return true;
		}
		return false;
	}
	
	public boolean outerRightSide(int x, int y) {
		if ((x >= 560 && x <= 600) && (y >= 40 && y <= 560)) {
			return true;
		}
		return false;
	}
	
	public boolean outerTopSide(int x, int y) {
		if ((y >= 0 && y <= 40) && (x >= 40 && x <= 560)) {
			return true;
		}
		return false;
	}
	
	public boolean outerBottomSide(int x, int y) {
		if ((x >= 40 && x <= 560) && (y >= 560 && y <= 600)) {
			return true;
		}
		return false;
	}
}

