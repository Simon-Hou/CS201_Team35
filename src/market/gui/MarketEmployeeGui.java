package market.gui;

import java.awt.Graphics2D;

import public_Gui.Gui;
import market.MarketEmployeeRole;

public class MarketEmployeeGui implements Gui{

	MarketEmployeeRole role;
	
	public MarketEmployeeGui(MarketEmployeeRole e){
		role = e;
	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
}
