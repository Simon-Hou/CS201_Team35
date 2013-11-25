package cityGui;

import house.House;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import util.Bank;
import util.BankMapLoc;
import util.HouseMapLoc;
import util.Loc;

/**
 * Not to be confused with CitiBank
 */
public class CityHouse extends CityComponent implements ImageObserver {
	public House house;
	private int buildingSize = 35;
	public CityHouse(int x, int y) {
		super(x, y, Color.yellow, "House 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		initializeHouse();
	}

	public CityHouse(int x, int y, String I) {
		super(x, y, Color.yellow, I);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		house = new House();
		initializeHouse();
		//house.houseGui = this;
	}
	
	public void initializeHouse(){
		house = new House();
		house.houseGui = this;
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
		this.cityObject.cityMap.map.get("House").add(hMap);
	}

	public void updatePosition() {

	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, buildingSize, buildingSize);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
