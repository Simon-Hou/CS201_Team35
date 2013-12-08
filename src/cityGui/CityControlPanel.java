package cityGui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import cityGui.trace.AlertLevel;
import cityGui.trace.AlertLog;
import cityGui.trace.AlertTag;

public class CityControlPanel extends JPanel implements ActionListener{

	SimCityGui city;

	public static final int CP_WIDTH = 600, CP_HEIGHT = 100;
	JButton addRestaurant, addBank, addHouse, addMarket, addPerson, newScenario;


	//For managing traces
	JToggleButton InfoButton;		//You could (and probably should) substitute a JToggleButton to replace both of these, but I split it into enable and disable for clarity in the demo.
	JToggleButton RestaurantTagButton;			
	JToggleButton BankTagButton;
	JToggleButton MarketTagButton;
	
	String name = "Control Panel";

	public CityControlPanel(SimCityGui city) {
		this.city = city;
		this.setPreferredSize(new Dimension(CP_WIDTH, CP_HEIGHT));
		this.setVisible(true);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		addRestaurant = new JButton("Add Restaurant");
		addRestaurant.addActionListener(this);
		c.gridx = 0; c.gridy = 0;
		add(addRestaurant, c);
		addBank = new JButton("Add Bank");
		addBank.addActionListener(this);
		c.gridx = 0; c.gridy = 1;
		add(addBank, c);
		addHouse = new JButton("Add House");
		addHouse.addActionListener(this);
		c.gridx = 1; c.gridy = 0;
		add(addHouse, c);
		addMarket = new JButton("Add Market");
		addMarket.addActionListener(this);
		c.gridx = 1; c.gridy = 1;
		add(addMarket, c);
		addPerson = new JButton("Add Person");
		addPerson.addActionListener(this);
		c.gridx = 2; c.gridy = 0;
		add(addPerson, c);
		newScenario = new JButton("Select Scenario");
		newScenario.addActionListener(this);
		c.gridx = 2; c.gridy = 1;
		add(newScenario,c);
		

		//Trace panel buttons
		InfoButton = new JToggleButton("Hide Level: INFO");
		InfoButton.setSelected(true);
		InfoButton.addActionListener(this);

		RestaurantTagButton = new JToggleButton("Hide Tag: RESTAURANT");
		RestaurantTagButton.setSelected(true);
		RestaurantTagButton.addActionListener(this);

		BankTagButton = new JToggleButton("Hide Tag: BANK");
		BankTagButton.setSelected(true);
		BankTagButton.addActionListener(this);
		

		c.gridx = 3; c.gridy = 1;
		this.add(InfoButton, c);

		c.gridx = 4; c.gridy = 1;
		this.add(RestaurantTagButton, c);

		c.gridx = 6; c.gridy = 1;
		this.add(BankTagButton, c);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addRestaurant)) {
			city.city.addObject(CityComponents.RESTAURANT);
			AlertLog.getInstance().logInfo(AlertTag.RESTAURANT, this.name, "Adding New Restaurant");
		}
		else if (e.getSource().equals(addBank)) {
			AlertLog.getInstance().logInfo(AlertTag.BANK, this.name, "Adding New Bank");
			city.city.addObject(CityComponents.BANK);
		}
		else if (e.getSource().equals(addHouse)) {
			city.city.addObject(CityComponents.HOUSE);
			AlertLog.getInstance().logInfo(AlertTag.HOUSE, this.name, "Adding New House");
		}
		else if (e.getSource().equals(addMarket)) {
			AlertLog.getInstance().logInfo(AlertTag.MARKET, this.name, "Adding New Market");
			city.city.addObject(CityComponents.MARKET);
		}
		else if (e.getSource().equals(newScenario)){
			//AlertLog.getInstance().logInfo(AlertTag., this.name, "Adding New Restaurant");
			//System.out.println("HEREERERERRE");
			ScenarioPanel scenarioPanel = new ScenarioPanel(this.city);
		}
		else if(e.getSource().equals(addPerson)) {
			city.NewPersonCreationPanel();
		}
		else if(e.getSource().equals(InfoButton)) {
			if (InfoButton.isSelected()){
				InfoButton.setText("Hide Level: INFO");
				city.tracePanel.showAlertsWithLevel(AlertLevel.INFO);
			}
			else{
				InfoButton.setText("Show Level: INFO");
				city.tracePanel.hideAlertsWithLevel(AlertLevel.INFO);
			}
		}
		else if(e.getSource().equals(RestaurantTagButton)) {
			if (RestaurantTagButton.isSelected()){
				RestaurantTagButton.setText("Hide Tag: RESTAURANT");
				city.tracePanel.showAlertsWithTag(AlertTag.RESTAURANT);
			}
			else{
				RestaurantTagButton.setText("Show Tag: RESTAURANT");
				city.tracePanel.hideAlertsWithTag(AlertTag.RESTAURANT);
			}
		}
		else if(e.getSource().equals(BankTagButton)) {
			if (BankTagButton.isSelected()){
				BankTagButton.setText("Hide Tag: BANK");
				city.tracePanel.showAlertsWithTag(AlertTag.BANK);
			}
			else{
				BankTagButton.setText("Show Tag: BANK");
				city.tracePanel.hideAlertsWithTag(AlertTag.BANK);
			}
		}
		else if(e.getSource().equals(MarketTagButton)) {
			if (MarketTagButton.isSelected()){
				MarketTagButton.setText("Hide Tag: MARKET");
				city.tracePanel.showAlertsWithTag(AlertTag.MARKET);
			}
			else{
				MarketTagButton.setText("Show Tag: MARKET");
				city.tracePanel.hideAlertsWithTag(AlertTag.MARKET);
			}
		}
	}
}
