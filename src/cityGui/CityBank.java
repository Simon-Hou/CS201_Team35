package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import util.Bank;
import util.BankMapLoc;
import util.Loc;

/**
 * Not to be confused with CitiBank
 */
public class CityBank extends CityComponent implements ImageObserver {
	java.net.URL imgURL1 = getClass().getResource("bank1.png");
	ImageIcon img1 = new ImageIcon(imgURL1);
	java.net.URL imgURL2 = getClass().getResource("bank2.png");
	ImageIcon img2 = new ImageIcon(imgURL2);
	java.net.URL imgURL3 = getClass().getResource("bank3.png");
	ImageIcon img3 = new ImageIcon(imgURL3);
	java.net.URL imgURL4 = getClass().getResource("bank4.png");
	ImageIcon img4 = new ImageIcon(imgURL4);
	java.net.URL imgURL5 = getClass().getResource("bank5.png");
	ImageIcon img5 = new ImageIcon(imgURL5);
	BankMapLoc bMap;
	boolean readyToPaint = false;
	//g.drawImage(img.getImage(),xPos,yPos,35,35,null);
	public Bank bank;
	private int buildingSize = 35;
	public CityBank(int x, int y) {
		super(x, y, Color.green, "Bank 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}

	public CityBank(int x, int y, String I) {
		super(x, y, Color.green, I);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		bank = new Bank();
		bank.bankGui = this;
	}
	
	@Override
	public void addAgentObjectToMap(){
		bMap = new BankMapLoc(bank);
		bMap.bankTopLeftX = x;
		bMap.bankTopLeftY = y;
//		int tempX = bMap.loc.x;
//		int tempY = bMap.loc.y;
//		System.out.println("Old Building X Value: " + bMap.loc.x);
//		System.out.println("Old Building Y Value: " + bMap.loc.y);
////		bMap.loc.x = sidewalkX(tempX,tempY);
////		bMap.loc.y = sidewalkY(tempX,tempY);
//		System.out.println("New Building X Value: " + bMap.loc.x);
//		System.out.println("New Building Y Value: " + bMap.loc.y);
		bMap.loc = new Loc(sidewalkX(x,y),sidewalkY(x,y));
		this.cityObject.cityMap.map.get("Bank").add(bMap);
		readyToPaint = true;
	}

	public void updatePosition() {

	}

	public void paint(Graphics g) {
		g.setColor(color);
		//g.fillRect(x, y, buildingSize, buildingSize);
		//if (readyToPaint) {
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
		//}
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
