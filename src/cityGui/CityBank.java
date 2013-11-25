package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import util.Bank;
import util.BankMapLoc;
import util.Loc;

/**
 * Not to be confused with CitiBank
 */
public class CityBank extends CityComponent implements ImageObserver {
	public Bank bank;
	private int buildingSize = 35;
	public CityBank(int x, int y) {
		super(x, y, Color.green, "Bank 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		initializeBank();
	}

	public CityBank(int x, int y, String I) {
		super(x, y, Color.green, I);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		initializeBank();
	}
	
	public void initializeBank(){
		bank = new Bank();
		bank.bankGui = this;
	}
	
	@Override
	public void addAgentObjectToMap(){
		BankMapLoc bMap = new BankMapLoc(bank);
		bMap.loc = new Loc(x,y);
		int tempX = bMap.loc.x;
		int tempY = bMap.loc.y;
		System.out.println("Old Building X Value: " + bMap.loc.x);
		System.out.println("Old Building Y Value: " + bMap.loc.y);
		bMap.loc.x = sidewalkX(tempX,tempY);
		bMap.loc.y = sidewalkY(tempX,tempY);
		System.out.println("New Building X Value: " + bMap.loc.x);
		System.out.println("New Building Y Value: " + bMap.loc.y);
		bMap.loc = new Loc(sidewalkX(x,y),sidewalkY(x,y));
		this.cityObject.cityMap.map.get("Bank").add(bMap);
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
