package cityGui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import cityGui.trace.AlertLevel;
import cityGui.trace.AlertLog;
import cityGui.trace.AlertTag;

public class CityControlPanel extends JPanel implements ActionListener{

	SimCityGui city;

	public static final int CP_WIDTH = 600, CP_HEIGHT = 100;
	JButton addParkerRestaurant, addLindaRestaurant, addGabeRestaurant, addYoccaRestaurant, addBobbyRestaurant, addSimonRestaurant, addBank, addHouse, addMarket, addPerson, newScenario;
	JButton dummyScenarioA;

	//For managing traces
	JToggleButton InfoButton;		//You could (and probably should) substitute a JToggleButton to replace both of these, but I split it into enable and disable for clarity in the demo.
	JToggleButton RestaurantTagButton;			
	JToggleButton BankTagButton;
	JToggleButton MarketTagButton;
	
	String name = "Control Panel";
	
	CityListPanel panel1 = new CityListPanel();
	CityListPanel panel2 = new CityListPanel();
	CityListPanel panel3 = new CityListPanel("trace");

	public CityControlPanel(SimCityGui city) {
		this.city = city;
		this.setPreferredSize(new Dimension(CP_WIDTH, CP_HEIGHT));
		this.setVisible(true);

		this.setLayout(new GridLayout(1,3,5,0));
	
		
		add(panel1);
		add(panel2);
		//add(panel3);
		//city.trace.add(panel3);
		
		//AddBuilding/People Buttons
		
		
		
		addBank = new JButton("Add Bank");
		addBank.addActionListener(this);
		panel1.addButton(addBank);
		
		
		addHouse = new JButton("Add House");
		addHouse.addActionListener(this);
		panel1.addButton(addHouse);
		
		addMarket = new JButton("Add Market");
		addMarket.addActionListener(this);
		panel1.addButton(addMarket);
		
		addParkerRestaurant = new JButton("Add Parker's Restaurant");
		addParkerRestaurant.addActionListener(this);
		panel1.addButton(addParkerRestaurant);
		
		addLindaRestaurant = new JButton("Add Linda's Restaurant");
		addLindaRestaurant.addActionListener(this);
		panel1.addButton(addLindaRestaurant);
		
		addGabeRestaurant = new JButton("Add Gabe's Restaurant");
		addGabeRestaurant.addActionListener(this);
		panel1.addButton(addGabeRestaurant);
		
		addYoccaRestaurant = new JButton("Add Andrew's Restaurant");
		addYoccaRestaurant.addActionListener(this);
		panel1.addButton(addYoccaRestaurant);
		
		addBobbyRestaurant = new JButton("Add Bobby's Restaurant");
		addBobbyRestaurant.addActionListener(this);
		panel1.addButton(addBobbyRestaurant);
		
		addSimonRestaurant = new JButton("Add Simon's Restaurant");
		addSimonRestaurant.addActionListener(this);
		panel1.addButton(addSimonRestaurant);
		
		addPerson = new JButton("Add Person");
		addPerson.addActionListener(this);
		panel1.addButton(addPerson);
		
//		newScenario = new JButton("Select Scenario");
//		newScenario.addActionListener(this);
//		panel1.addButton(newScenario);
		

		//Scenario Buttons
		dummyScenarioA = new JButton("DummyScenarioA");
		dummyScenarioA.addActionListener(this);
		panel2.addButton(dummyScenarioA);
		
		
		//Trace panel buttons
		InfoButton = new JToggleButton("Hide Level: INFO");
		InfoButton.setSelected(true);
		InfoButton.addActionListener(this);
		panel3.addToggleButton(InfoButton);

		RestaurantTagButton = new JToggleButton("Hide Tag: RESTAURANT");
		RestaurantTagButton.setSelected(true);
		RestaurantTagButton.addActionListener(this);
		panel3.addToggleButton(RestaurantTagButton);

		BankTagButton = new JToggleButton("Hide Tag: BANK");
		BankTagButton.setSelected(true);
		BankTagButton.addActionListener(this);
		panel3.addToggleButton(BankTagButton);
		
	}

	public void actionPerformed(ActionEvent e) {
//		if (e.getSource().equals(addRestaurant)) {
//			city.city.addObject(CityComponents.RESTAURANT);
//			AlertLog.getInstance().logInfo(AlertTag.RESTAURANT, this.name, "Adding New Restaurant");
//		}
		
		//^^add new checks for each person's restaurant!
		
		
		 if (e.getSource().equals(addBank)) {
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
//		else if (e.getSource().equals(newScenario)){
//			//AlertLog.getInstance().logInfo(AlertTag., this.name, "Adding New Restaurant");
//			//System.out.println("HEREERERERRE");
//			ScenarioPanel scenarioPanel = new ScenarioPanel(this.city);
//		}
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
	
	public void addTraceControls(){
		city.trace.add(panel3);
	}
	
	private class CityListPanel extends JPanel{
		 private JPanel view = new JPanel();
		 private List<JButton> inventoryList = new ArrayList<JButton>();
			public JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
										JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			 Dimension paneSize = new Dimension (150, 90);	
		CityListPanel(){
			Dimension size = new Dimension (150, 90);
	        setPreferredSize(size);
	        setMinimumSize(size);
	        setMaximumSize(size);
	        
			setLayout(new FlowLayout());
			view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
			pane.setViewportView(view);
	        add(pane);
	        
	        
	        //new
	        
	       
	        pane.setPreferredSize(paneSize);
	        pane.setMinimumSize(paneSize);
	        pane.setMaximumSize(paneSize);




		}
		
		
		CityListPanel(String trace){
			Dimension size = new Dimension (150, 185);
	        setPreferredSize(size);
	        setMinimumSize(size);
	        setMaximumSize(size);
	        
			setLayout(new FlowLayout());
			view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
			pane.setViewportView(view);
	        add(pane);

	        pane.setPreferredSize(size);
	        pane.setMinimumSize(size);
	        pane.setMaximumSize(size);

		}
		
		
		public void addButton(JButton item){

	        Dimension buttonSize = new Dimension(paneSize.width-20, (int) (paneSize.height / 5));
	        item.setPreferredSize(buttonSize);
	        item.setMinimumSize(buttonSize);
	        item.setMaximumSize(buttonSize);
	        view.add(item);
		}
		
		public void addToggleButton(JToggleButton item){

	        Dimension buttonSize = new Dimension(paneSize.width-20, (int) (paneSize.height / 5));
	        item.setPreferredSize(buttonSize);
	        item.setMinimumSize(buttonSize);
	        item.setMaximumSize(buttonSize);
	        view.add(item);
		}
	}
	
}
