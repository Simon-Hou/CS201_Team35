package bank.gui;

import javax.swing.JPanel;



public class BankPanel extends JPanel{

	/*TODO: ask the following:
	 * should there be one main gui that sets everything else up, and only the panels? this would make the most sense 
	 * */
	
	private BankGui gui; //reference to main gui may not need a bank gui.
	
	public BankPanel(BankGui gui) {
        this.gui = gui;
	}
}
