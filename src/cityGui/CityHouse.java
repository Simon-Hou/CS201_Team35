package cityGui;

import house.House;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import util.Bank;
import util.BankMapLoc;
import util.HouseMapLoc;
import util.Loc;

/**
 * Not to be confused with CitiBank
 */
public class CityHouse extends CityComponent implements ImageObserver {
	java.net.URL imgURL1 = getClass().getResource("house1.png");
	ImageIcon img1 = new ImageIcon(imgURL1);
	java.net.URL imgURL2 = getClass().getResource("house2.png");
	ImageIcon img2 = new ImageIcon(imgURL2);
	java.net.URL imgURL3 = getClass().getResource("house3.png");
	ImageIcon img3 = new ImageIcon(imgURL3);
	java.net.URL imgURL4 = getClass().getResource("house4.png");
	ImageIcon img4 = new ImageIcon(imgURL4);
	java.net.URL imgURL5 = getClass().getResource("house.png");
	ImageIcon img5 = new ImageIcon(imgURL5);
	public House house;
	private int buildingSize = 35;
	public CityHouse(int x, int y) {
		super(x, y, Color.yellow, "House 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}

	public CityHouse(int x, int y, String I) {
		super(x, y, Color.yellow, I);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		house = new House();
		//house.houseGui = this;
	}
	
	@Override
	public void addAgentObjectToMap(){
		HouseMapLoc hMap = new HouseMapLoc(house);
		hMap.loc = new Loc(x,y);
		int tempX = hMap.loc.x;
		int tempY = hMap.loc.y;
		System.out.println("Old Building X Value: " + hMap.loc.x);
		System.out.println("Old Building Y Value: " + hMap.loc.y);
		hMap.loc.x = sidewalkX(tempX,tempY);
		hMap.loc.y = sidewalkY(tempX,tempY);
		System.out.println("New Building X Value: " + hMap.loc.x);
		System.out.println("New Building Y Value: " + hMap.loc.y);
		this.cityObject.cityMap.map.get("Bank").add(hMap);
	}

	public void updatePosition() {

	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, buildingSize, buildingSize);
		if (this.outerTopSide((int)rectangle.getX(), (int)rectangle.getY()) || this.innerBottomSide((int)rectangle.getX(), (int)rectangle.getY())) {
			g.drawImage(img1.getImage(),x,y,35,35,null);
		}
		else if (this.outerRightSide((int)rectangle.getX(), (int)rectangle.getY()) || this.innerLeftSide((int)rectangle.getX(), (int)rectangle.getY())) {
			g.drawImage(img2.getImage(),x,y,35,35,null);
		}
		else if (this.outerBottomSide((int)rectangle.getX(), (int)rectangle.getY())|| this.innerTopSide((int)rectangle.getX(), (int)rectangle.getY())) {
			g.drawImage(img3.getImage(),x,y,35,35,null);
		}
		else if (this.outerLeftSide((int)rectangle.getX(), (int)rectangle.getY()) || this.innerRightSide((int)rectangle.getX(), (int)rectangle.getY())) {
			g.drawImage(img4.getImage(),x,y,35,35,null);
		}
		else g.drawImage(img5.getImage(),x,y,35,35,null);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
