package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import util.Bank;
import util.BankMapLoc;
import util.Loc;

/**
 * Not to be confused with CitiBank
 */
public class CityBank extends CityComponent {
	public Bank bank;
	private int buildingSize = 50;
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
		BankMapLoc bMap = new BankMapLoc(bank);
		bMap.loc = new Loc(x,y);
		this.cityObject.cityMap.map.get("Bank").add(bMap);
	}

	public void updatePosition() {

	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, buildingSize, buildingSize);
	}
}
