package house.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import public_Gui.Gui;
import house.InhabitantRole;





public class InhabitantGui implements Gui {


	private InhabitantRole agent = null;
	private HouseGui gui=null;
	private boolean pause=false;
	
	private int xInitial=-20;
	private int yInitial=-20;
	private  int xPos = xInitial;//default inhabitant position
	private  int yPos = yInitial;
	private int xResting=40;
	private int yResting=100;		
	private int xDestination = xResting, 		yDestination = yResting;//default start position


	public InhabitantGui(InhabitantRole agent,HouseGui gui) {
		this.agent = agent;
		this.gui=gui;
	}

	@Override
	public void updatePosition() {
		if(!pause){
			if (xPos < xDestination)
				xPos++;
			else if (xPos > xDestination)
				xPos--;

			if (yPos < yDestination)
				yPos++;
			else if (yPos > yDestination)
				yPos--;
		}

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(xPos, yPos, 20, 20);

	}

	@Override
	public boolean isPresent() {
		return true;
	}
}
