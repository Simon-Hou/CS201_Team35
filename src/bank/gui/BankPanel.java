package bank.gui;

import interfaces.BankCustomer;
import interfaces.BankTeller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bank.BankCustomerRole;
import bank.BankTellerRole;
import util.Bank;
import util.Bank.BankAccount;


public class BankPanel extends JPanel{


	//public static final int groupRows = 1;
	//public static final int groupCols = 3;
	//public static final int groupXGap = 20;
	//public static final int groupYGap = 20;
	//public static final int restPanelRows = 1;
	//public static final int restPanelCols = 2;
	//public static final int restPanelXGap = 20;
	//public static final int restPanelYGap = 20;
	private ListPanel accountPanel = new ListPanel(this, "Accounts");
	private ListPanel tellerPanel = new ListPanel(this, "Tellers");
	private ListPanel customerPanel = new ListPanel(this, "Customers");
	
	private JPanel groupOfListPanels = new JPanel();
	
	
	private BankGui gui; //reference to main gui may not need a bank gui.
	
	private Bank bank = new Bank();
	
	public BankPanel(BankGui gui) {
        this.gui = gui;
        //setLayout(new GridLayout(restPanelRows, restPanelCols, restPanelXGap, restPanelYGap));
        //initRestLabel();
	}
	
	public void enterBank(BankCustomer bcr, BankCustomerGui g) {
		bank.addMeToQueue(bcr);
		gui.bankAnimationPanel.addGui(g);
		if (bcr instanceof BankCustomerRole) {
			((BankCustomerRole) bcr).startThread();
		}
	}
	
	public void enterBank(BankTeller bt, BankTellerGui g) {
		bank.startTellerShift(bt);
		gui.bankAnimationPanel.addGui(g);
		if (bt instanceof BankTellerRole) {
			((BankTellerRole) bt).startThread();
		}
	}	

//			c.setHost(host);
//			c.setCashier(cashier);
//			c.setGui(g);
//			customers.add(c);
//			c.startThread();
//		}
//		if (type.equals("Waiters")) {
//			WaiterAgent w = new WaiterAgent(name);	
//			WaiterGui g = new WaiterGui(w, gui, host.waiters.size());
//			System.err.println("Hello");
//			gui.animationPanel.addGui(g);// dw
//			w.setHost(host);//TODO add setHost to waiter
//			w.setCook(cook);
//			w.setCashier(cashier);
//			w.setGui(g);
//			w.addCounters(counters);
//			waiters.add(w);
//			w.startThread();
//			host.waiters.add(w);
//			if (w instanceof WaiterAgent) {
//				host.addWaiter(w);
//			}
//			
//		}
	//}
}



//private JPanel restLabel = new JPanel();
//private ListPanel customerPanel = new ListPanel(this, "Customers");
////private JPanel group = new JPanel();
//
//private ListPanel waiterPanel = new ListPanel(this, "Waiters");
//private JPanel waiterCust = new JPanel();
//
//

//    /*waiterCust.setLayout(new FlowLayout());
//    waiterCust.add(waiterPanel);
//    waiterCust.add(customerPanel);
//    group.add(waiterCust);//Both on one panel*///was using multiple panels
//
//    initRestLabel();
//    /*Dimension infoDimen1 = new Dimension(1000, (int) (1000 * .6));
//    restLabel.setPreferredSize(infoDimen1);
//    restLabel.setMinimumSize(infoDimen1);
//    restLabel.setMaximumSize(infoDimen1);//TODO just can take out at will*/
//    add(restLabel);
//    add(waiterPanel);
//    add(customerPanel);
//    //add(restLabel, BorderLayout.WEST);
//    //add(group, BorderLayout.EAST);
//}
//
///**
// * Sets up the restaurant label that includes the menu,
// * and host and cook information
// */
//private void initRestLabel() {
//    JLabel label = new JLabel();
//    //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
//    restLabel.setLayout(new BorderLayout());
//    label.setText(
//            "<html><h4><u>Tonight's Staff</u></h4><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");
//    //Working on changing the size of restLabel
//    restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
//    restLabel.add(label, BorderLayout.CENTER);
//    restLabel.add(new JLabel("        "), BorderLayout.EAST);
//    restLabel.add(new JLabel("        "), BorderLayout.WEST);
//}
//
///**
// * When a customer or waiter is clicked, this function calls
// * updatedInfoPanel() from the main gui so that person's information
// * will be shown
// *
// * @param type indicates whether the person is a customer or waiter
// * @param name name of person
// */
public void showInfo(String type, String name) {

    if (type.equalsIgnoreCase("acounts")) {
        for (int i = 0; i < bank.accounts.size(); i++) {
            BankAccount temp = customers.get(i);
            if (temp.getName() == name)
                gui.updateInfoPanel(temp);
        }
    }
}
//
///**
// * Adds a customer or waiter to the appropriate list
// *
// * @param type indicates whether the person is a customer or waiter (later)
// * @param name name of person
// */
//public void addPerson(String type, String name) {
//
//	if (type.equals("Customers")) {
//		CustomerAgent c = new CustomerAgent(name);	
//		CustomerGui g = new CustomerGui(c, gui);
//
//		gui.animationPanel.addGui(g);// dw
//		c.setHost(host);
//		c.setCashier(cashier);
//		c.setGui(g);
//		customers.add(c);
//		c.startThread();
//	}
//	if (type.equals("Waiters")) {
//		WaiterAgent w = new WaiterAgent(name);	
//		WaiterGui g = new WaiterGui(w, gui, host.waiters.size());
//		System.err.println("Hello");
//		gui.animationPanel.addGui(g);// dw
//		w.setHost(host);//TODO add setHost to waiter
//		w.setCook(cook);
//		w.setCashier(cashier);
//		w.setGui(g);
//		w.addCounters(counters);
//		waiters.add(w);
//		w.startThread();
//		host.waiters.add(w);
//		if (w instanceof WaiterAgent) {
//			host.addWaiter(w);
//		}
//		
//	}
//}
//public void addPerson(String type, String name, boolean hunger) {
//
//	if (type.equals("Customers")) {
//		CustomerAgent c = new CustomerAgent(name);	
//		CustomerGui g = new CustomerGui(c, gui);
//
//		gui.animationPanel.addGui(g);// dw
//		c.setHost(host);
//		c.setGui(g);
//		if (hunger==true)
//			c.getGui().setHungry();
//		customers.add(c);
//		c.startThread();
//	}
//}