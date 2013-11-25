package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import util.BankMapLoc;
import util.Loc;
import util.RestaurantMapLoc;

public class CityRestaurant extends CityComponent implements ImageObserver {
	java.net.URL imgURL1 = getClass().getResource("restaurant1.png");
	ImageIcon img1 = new ImageIcon(imgURL1);
	java.net.URL imgURL2 = getClass().getResource("restaurant2.png");
	ImageIcon img2 = new ImageIcon(imgURL2);
	java.net.URL imgURL3 = getClass().getResource("restaurant3.png");
	ImageIcon img3 = new ImageIcon(imgURL3);
	java.net.URL imgURL4 = getClass().getResource("restaurant4.png");
	ImageIcon img4 = new ImageIcon(imgURL4);
	java.net.URL imgURL5 = getClass().getResource("restaurant5.png");
	ImageIcon img5 = new ImageIcon(imgURL5);
	//public Restaurant restaurant;
	private int buildingSize = 35;
	public CityRestaurant(int x, int y) {
		super(x, y, Color.red, "Restaurant 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		//System.out.println("First");
	}
	
	public CityRestaurant(int x, int y, String ID) {
		super(x, y, Color.red, ID);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		//System.out.println("Second");

	}

	public void updatePosition() {
		
	}
	
	@Override
	public void addAgentObjectToMap(){
//		RestaurantMapLoc rMap = new RestaurantMapLoc(restaurant);
//		rMap.loc = new Loc(x,y);
//		int tempX = rMap.loc.x;
//		int tempY = rMap.loc.y;
//		System.out.println("Old Building X Value: " + rMap.loc.x);
//		System.out.println("Old Building Y Value: " + rMap.loc.y);
//		rMap.loc.x = sidewalkX(tempX,tempY);
//		rMap.loc.y = sidewalkY(tempX,tempY);
//		System.out.println("New Building X Value: " + rMap.loc.x);
//		System.out.println("New Building Y Value: " + rMap.loc.y);
//		this.cityObject.cityMap.map.get("Bank").add(rMap);
	}

	public void paint(Graphics g) {
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

	
//	public boolean contains(int x, int y) {
//		if (x >= this.x && x <= this.x+20)
//			if (y >= this.y && y <= this.y+20)
//				return true;
//		return false;
//	}

}
