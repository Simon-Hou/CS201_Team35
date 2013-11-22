package bank.gui;

import java.awt.Color;
import java.awt.Graphics2D;


public class BankTellerGui implements Gui{
	public void updatePosition(int x, int y) {
		
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		//g.fillRect(xPos, yPos, BankCustomerWidth, BankCustomerHeight);
//		if (hasFood) {
//			g.setColor(Color.WHITE);//plate
//			g.fillRect(xPos+BankCustomerWidth/2-5, yPos+BankCustomerHeight-5, BankCustomerWidth/2+10, BankCustomerHeight/2+10);
//			if (!agent.doneEating){
//				g.setColor(food);//TODO change color of stuff
//				g.fillRect(xPos+BankCustomerWidth/2, yPos+BankCustomerHeight, BankCustomerWidth/2, BankCustomerHeight/2);
//			}
//        }
		g.setColor(Color.BLACK);
		//g.drawString(dialogue, xPos, yPos);
	}
	
	public boolean isPresent() {
		return true;
	}
}
