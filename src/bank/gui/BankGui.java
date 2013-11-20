package bank.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import bank.BankCustomerRole;
import bank.BankTellerRole;;

public class BankGui  extends JFrame implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
    }
	
	/**
     * Main routine to get gui started
     */
    public static void main(String[] args) {
        BankGui gui = new BankGui();
        gui.setTitle("Bank");
        gui.setVisible(true);
        gui.setResizable(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //cityFrame.addTab("Bank", NULL/*unless you want an image*/,  Component component) 
    }
	
}
