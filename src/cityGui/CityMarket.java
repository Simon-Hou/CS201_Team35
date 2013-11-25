package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import market.Market;
import util.Bank;
import util.BankMapLoc;
import util.Loc;
import util.MarketMapLoc;

/**
 * Not to be confused with CitiBank
 */
public class CityMarket extends CityComponent implements ImageObserver{
	java.net.URL imgURL1 = getClass().getResource("market1.png");
	ImageIcon img1 = new ImageIcon(imgURL1);
	java.net.URL imgURL2 = getClass().getResource("market2.png");
	ImageIcon img2 = new ImageIcon(imgURL2);
	java.net.URL imgURL3 = getClass().getResource("market3.png");
	ImageIcon img3 = new ImageIcon(imgURL3);
	java.net.URL imgURL4 = getClass().getResource("market4.png");
	ImageIcon img4 = new ImageIcon(imgURL4);
	java.net.URL imgURL5 = getClass().getResource("market5.png");
	ImageIcon img5 = new ImageIcon(imgURL5);
	public Market market;
	private int buildingSize = 35;
	public CityMarket(int x, int y) {
		super(x, y, Color.magenta, "Bank 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}

	public CityMarket(int x, int y, String I) {
		super(x, y, Color.magenta, I);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}
	
	@Override
	public void addAgentObjectToMap(){
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
		this.cityObject.cityMap.map.get("Bank").add(mMap);
	}

	public void updatePosition() {

	}

	public void paint(Graphics g) {
		g.setColor(color);
		//g.fillRect(x, y, buildingSize, buildingSize);
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
