package cityGui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import person.PersonAgent;
import util.Bank;
import util.CityMap;
import city.CityObject;
import cityGui.test.PersonGui;
import cityGui.trace.AlertLog;
import cityGui.trace.TracePanel;

public class SimCityGui extends JFrame {
	
	static public CityPanel city;
	public CityObject cityObject;
	InfoPanel info;
	CityView view;
	CityControlPanel CP;
	TracePanel tracePanel;
	GridBagConstraints c = new GridBagConstraints();

	public SimCityGui() throws HeadlessException {
		CP = new CityControlPanel(this);
		
		tracePanel = new TracePanel();
		tracePanel.setPreferredSize(new Dimension(CP.getPreferredSize().width, (int)(1.4*CP.getPreferredSize().height)));
		tracePanel.showAlertsForAllLevels();
		tracePanel.showAlertsForAllTags();

		//THIS IS THE AGENT CITY
		cityObject = new CityObject(this);
		
		city = new CityPanel(this);
		city.cityObject = cityObject;
		
		view = new CityView(this);
		
		info = new InfoPanel(this);
		
		this.setLayout(new GridBagLayout());
		
		c.gridx = 0; c.gridy = 0;
		c.gridwidth = 6; c.gridheight = 6;
		this.add(city, c);
		
		c.gridx = 6; c.gridy = 0;
		c.gridwidth = 5; c.gridheight = 1;
		this.add(info, c);

		c.gridx = 6; c.gridy = 1;
		c.gridwidth = 5; c.gridheight = 5;
		this.add(view, c);

		c.gridx = 0; c.gridy = 6;
		c.gridwidth = 11; c.gridheight = 1;
		this.add(CP, c);
		
		/*c.gridx = 0; c.gridy = 7;
		c.gridwidth = 11; c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		this.add(tracePanel, c);*/
	}
	
	public void NewPersonCreationPanel(){
		PersonCreationPanel pCreate = new PersonCreationPanel(this);
	}
	
	public void addNewPerson(PersonAgent p){
		
		
		/*String name = "p0";
		PersonAgent p = new PersonAgent(name,cityObject.cityMap);
		PersonGui personGui = new PersonGui(p,this,0,0,0,0);
		p.gui = personGui;
		cityObject.people.add(p);
		city.addMoving(personGui);
		p.startThread();*/
		PersonGui personGui = new PersonGui(p,this,0,0,0,0);
		p.gui = personGui;
		cityObject.people.add(p);
		city.addMoving(personGui);
		p.startThread();
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		SimCityGui test = new SimCityGui();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setResizable(false);
		test.pack();
		test.setVisible(true);
		
		int xStartTest = 190;
		int yStartTest = 50;
		
		
		
		
		//Bank b = test.cityObject.cityMap.map.get("Bank").get(0).bank;
		//test.addNewPerson("p0");
		//test.addNewPerson("p1");
		//test.cityObject.people.get(0).setJob(placeOfWork, jobType, start, end);
		/*cityObject.people.add(new PersonAgent("p0",cityObject.cityMap));
		cityObject.people.get(0).startThread();*/
		
		/*int xStartTest = 300;
		int yStartTest = 520;

		PersonGui pg1 = new PersonGui(new PersonAgent("A",new CityMap()),test, xStartTest, yStartTest, 300, 520);
        PersonGui pg2 = new PersonGui(new PersonAgent("B",new CityMap()),test, xStartTest, yStartTest, 300, 70);
		PersonGui pg3 = new PersonGui(new PersonAgent("C",new CityMap()),test, xStartTest, yStartTest, 300, 400);
		PersonGui pg4 = new PersonGui(new PersonAgent("D",new CityMap()),test, xStartTest, yStartTest, 300, 190);
		PersonGui pg5 = new PersonGui(new PersonAgent("E",new CityMap()),test, xStartTest, yStartTest, 190, 300);
		PersonGui pg6 = new PersonGui(new PersonAgent("F",new CityMap()),test, xStartTest, yStartTest, 520, 300);
		PersonGui pg7 = new PersonGui(new PersonAgent("G",new CityMap()),test, xStartTest, yStartTest, 400, 300);
		PersonGui pg8 = new PersonGui(new PersonAgent("G",new CityMap()),test, xStartTest, yStartTest, 70, 300);
		test.city.addMoving(pg1);
		test.city.addMoving(pg2);
		test.city.addMoving(pg3);
		test.city.addMoving(pg4);
		test.city.addMoving(pg5);
		test.city.addMoving(pg6);
		test.city.addMoving(pg7);
		test.city.addMoving(pg8);*/


	}

}
