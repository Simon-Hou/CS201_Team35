package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import market.Market;
import util.Bank;
import util.BankMapLoc;
import util.Loc;
import util.MarketMapLoc;

/**
 * Not to be confused with CitiBank
 */
public class CityMarket extends CityComponent implements ImageObserver{
	public Market market;
	private int buildingSize = 35;
	public CityMarket(int x, int y) {
		super(x, y, Color.magenta, "Bank 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		initializeMarket();
	}

	public CityMarket(int x, int y, String I) {
		super(x, y, Color.magenta, I);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		initializeMarket();
	}
	
	public void initializeMarket(){
		market = new Market();
		market.gui = this;
	}
	
	@Override
	public JPanel addAgentObjectToMap(){
		MarketMapLoc mMap = new MarketMapLoc(market);
		mMap.loc = new Loc(x,y);
		int tempX = mMap.loc.x;
		int tempY = mMap.loc.y;
		System.out.println("Old Building X Value: " + mMap.loc.x);
		System.out.println("Old Building Y Value: " + mMap.loc.y);
		mMap.loc.x = sidewalkX(tempX,tempY);
		mMap.loc.y = sidewalkY(tempX,tempY);
		System.out.println("New Building X Value: " + mMap.loc.x);
		System.out.println("New Building Y Value: " + mMap.loc.y);
		this.cityObject.cityMap.map.get("Market").add(mMap);
		return null;
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
