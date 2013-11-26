package cityGui;

import interfaces.PlaceOfWork;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import market.gui.MarketPanel;
import person.PersonAgent;
import util.Bank;
import util.BankMapLoc;
import util.Bus;
import util.BusStop;
import util.CityMap;
import util.HouseMapLoc;
import util.JobType;
import util.Loc;
import util.MarketMapLoc;
import util.RestaurantMapLoc;
import city.CityObject;
import cityGui.test.BusGui;
import cityGui.test.PersonGui;
import cityGui.trace.AlertLog;
import cityGui.trace.TracePanel;

public class SimCityGui extends JFrame implements ActionListener {

	public CityPanel city;
	public CityObject cityObject;
	InfoPanel info;
	CityView view;
	CityControlPanel CP;
	TracePanel tracePanel;
	GridBagConstraints c = new GridBagConstraints();
	int SHIFTS = 3;
	int MAXTIME = 100;
	protected Timer timer;
	public long time=0;

	public SimCityGui() throws HeadlessException {
		CP = new CityControlPanel(this);

		tracePanel = new TracePanel();
		tracePanel.setPreferredSize(new Dimension(CP.getPreferredSize().width, (int)(1.4*CP.getPreferredSize().height)));
		tracePanel.showAlertsForAllLevels();
		tracePanel.showAlertsForAllTags();

		//THIS IS THE AGENT CITY
		cityObject = new CityObject(this);
		cityObject.MAXTIME = this.MAXTIME;

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


		timer = new Timer(10,  this);
		timer.start();
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

	public void addNewPersonHard(String name, PlaceOfWork placeOfWork,
			JobType jobType,int start,int end,int bankNum,int houseNum){

		PersonAgent person = new PersonAgent(name,cityObject.cityMap);
		person.setJob(placeOfWork, jobType, start, end);
		person.setBank(bankNum);
		person.setHouse(((HouseMapLoc) cityObject.cityMap.map.get("House").get(houseNum)).house);
		addNewPerson(person);

	}

	public void addNewBuilding(String type,int x, int y){
		if(type.equals("Bank")){
			/*city.addObject(CityComponents.BANK);
			city.moveBuildingTo(new Loc(x,y));
			city.setBuildingDown();*/
			CityComponent temp = new CityBank(x, y, "Bank " + (city.statics.size()-19));
			CityBankCard tempAnimation = new CityBankCard(this);
			((CityBank)temp).bank.setAnimationPanel(tempAnimation);
			city.banks.add(((CityBank)temp).bank);
			this.view.addView(tempAnimation, temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
			city.statics.add(temp);
			return;
		}
		if(type.equals("Market")){
			CityComponent temp = new CityMarket(x, y, "Market " + (city.statics.size()-19));
			CityMarketCard tempAnimation = new CityMarketCard(this);
			((CityMarket)temp).market.setMarketPanel(new MarketPanel(tempAnimation, ((CityMarket)temp).market));
			city.markets.add(((CityMarket)temp).market);
			this.view.addView(tempAnimation, temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
			city.statics.add(temp);
			return;
		}
		
		if(type.equals("Restaurant")){
			CityComponent temp = new CityRestaurant(x, y, "Restaurant " + (city.statics.size()-19));
			CityRestaurantCard tempAnimation = new CityRestaurantCard(this);
			((CityRestaurant)temp).setAnimationPanel(tempAnimation);
			city.restaurants.add(((CityRestaurant)temp).restaurant);
			this.view.addView(tempAnimation, temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
			city.statics.add(temp);
			return;
		}
		if(type.equals("House")){
			CityComponent temp = new CityHouse(x, y, "House " + (city.statics.size()-19));
			CityHouseCard tempAnimation = new CityHouseCard(this);
			((CityHouse)temp).house.setAnimationPanel(tempAnimation);
			city.houses.add(((CityHouse)temp).house);
			this.view.addView(tempAnimation, temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
			city.statics.add(temp);
			return;
		}
		
		/*
		 	CityHouseCard tempAnimation= new CityHouseCard(city);
			((CityHouse)temp).house.setAnimationPanel(tempAnimation);
			houses.add(((CityHouse)temp).house);//hack: this is not necessary because we have the cityObject already. Change it later
			city.view.addView(tempAnimation, temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
				*/

	}

	//HACK
	public void fullyManBuilding(String type,int num){


		if(type.equals("Bank")){
			int j = 0;
			int randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
			//System.out.println("Rand offset: "+randOffset);
			for(int i = 0;i<SHIFTS;++i){
				int start = (i*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				//System.out.println("Shift start, end: "+start+" " +end);
				int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
				int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
				addNewPersonHard("p"+j,
						((BankMapLoc) cityObject.cityMap.map.get("Bank").get(num)).bank,
						JobType.BankTeller,start,end,bankNum,houseNum);
				j = j+1;
			}
			return;
		}
		if(type.equals("Market")){
			int j = 0;
			int randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
			//System.out.println("Rand offset: "+randOffset);
			for(int i = 0;i<SHIFTS;++i){
				int start = (i*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				//System.out.println("Shift start, end: "+start+" " +end);
				int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
				int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
				addNewPersonHard("p"+j,
						((MarketMapLoc) cityObject.cityMap.map.get("Market").get(num)).market,
						JobType.MarketHost,start,end,bankNum,houseNum);
				j = j+1;
			}
			randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
			for(int i = 0;i<SHIFTS;++i){
				int start = (i*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				//System.out.println("Shift start, end: "+start+" " +end);
				int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
				int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
				addNewPersonHard("p"+j,
						((MarketMapLoc) cityObject.cityMap.map.get("Market").get(num)).market,
						JobType.MarketCashier,start,end,bankNum,houseNum);
				j = j+1;
			}

			for(int numEmployees = 0;numEmployees<2;++numEmployees){
				randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
				for(int i = 0;i<SHIFTS;++i){
					int start = (i*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
					int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
					//System.out.println("Shift start, end: "+start+" " +end);
					int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
					int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
					addNewPersonHard("p"+j,
							((MarketMapLoc) cityObject.cityMap.map.get("Market").get(num)).market,
							JobType.MarketEmployee,start,end,bankNum,houseNum);
					j = j+1;
				}
			}
		}
		if(type.equals("Restaurant")){
			int j = 0;
			int randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
			//System.out.println("Rand offset: "+randOffset);
			for(int i = 0;i<SHIFTS;++i){
				int start = (i*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset)%MAXTIME;
				//System.out.println("Shift start, end: "+start+" " +end);
				int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
				int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
				addNewPersonHard("prhost"+j,
						((RestaurantMapLoc) cityObject.cityMap.map.get("Restaurant").get(num)).restaurant,
						JobType.RestaurantHost,start,end,bankNum,houseNum);
				addNewPersonHard("pcook"+j,
						((RestaurantMapLoc) cityObject.cityMap.map.get("Restaurant").get(num)).restaurant,
						JobType.RestaurantCook,start,end,bankNum,houseNum);
				addNewPersonHard("pwaiter"+j,
						((RestaurantMapLoc) cityObject.cityMap.map.get("Restaurant").get(num)).restaurant,
						JobType.RestaurantWaiter,start,end,bankNum,houseNum);
				addNewPersonHard("prcash"+j,
						((RestaurantMapLoc) cityObject.cityMap.map.get("Restaurant").get(num)).restaurant,
						JobType.RestaurantCashier,start,end,bankNum,houseNum);
				
				j = j+1;
			}
			return;
		}
	}

	public void addBuses(SimCityGui simCityGui){
		Bus b = new Bus();
		Bus b2 = new Bus();
		//b.gui = new BusGui(b,test,110,110,30,50);
		b.gui = new BusGui(b,simCityGui,true);
		b2.gui = new BusGui(b2,simCityGui,false);
		
		cityObject.fBus = b;
		cityObject.bBus = b2;
		
		cityObject.cityMap.fStops.add(new BusStop(new Loc(380,450)));
		cityObject.cityMap.fStops.add(new BusStop(new Loc(170,130)));
		
		cityObject.cityMap.fStops.get(0).sidewalkLoc = new Loc(410,430);
		cityObject.cityMap.fStops.get(1).sidewalkLoc = new Loc(180,160);
		
		cityObject.cityMap.bStops.add(new BusStop(new Loc(460,90)));
		cityObject.cityMap.bStops.add(new BusStop(new Loc(100,490)));
		
		cityObject.cityMap.bStops.get(0).sidewalkLoc = new Loc(490,70);
		cityObject.cityMap.bStops.get(1).sidewalkLoc = new Loc(120,520);
		
		b.stops = cityObject.cityMap.fStops;
		b2.stops = cityObject.cityMap.bStops;
		
		city.addMoving(b.gui);
		city.addMoving(b2.gui);
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

		int xStartTest = 0;
		int yStartTest = 0;

		//Bank b = test.cityObject.cityMap.map.get("Bank").get(0).bank;
		//test.addNewPerson("p0");
		//HACK ADDS BUILDINGS TO THE MAP
		//test.addNewBuilding("Bank", 5, 400);
//		test.addNewBuilding("Market",200,250);
//		test.addNewBuilding("Market", 250, 200);
//		test.addNewBuilding("House", 200, 5);
//		test.addNewBuilding("House", 500, 5);
		
		//test.fullyManBuilding("Bank",0);
		//test.fullyManBuilding("Market",0);
		//test.fullyManBuilding("Market",1);
		test.addBuses(test);
		test.addNewBuilding("Bank", 5, 400);
		test.addNewBuilding("Market",200,250);
		//test.addNewBuilding("Restaurant", 5, 200);
		//test.addNewBuilding("Market", 250, 200);
		test.addNewBuilding("House", 200, 5);
		//test.addNewBuilding("House", 500, 5);
		test.addBuses(test);
		test.fullyManBuilding("Bank",0);
		test.fullyManBuilding("Market",0);
		//test.fullyManBuilding("Restaurant",0);
		//test.fullyManBuilding("Market",1);
		//test.fullyManBuilding("Bank",0);
		//test.fullyManBuilding("Market",0);



		//Bank b = test.cityObject.cityMap.map.get("Bank").get(0).bank;
		//test.addNewPerson("p0");
		//test.addNewPerson("p1");
		//test.cityObject.people.get(0).setJob(placeOfWork, jobType, start, end);
		/*cityObject.people.add(new PersonAgent("p0",cityObject.cityMap));
		cityObject.people.get(0).startThread();*/

		//		int xStartTest = 300;
		//		int yStartTest = 520;


//		PersonGui pg1 = new PersonGui(new PersonAgent("A",new CityMap()),test, xStartTest, yStartTest, 300, 520);
//        PersonGui pg2 = new PersonGui(new PersonAgent("B",new CityMap()),test, xStartTest, yStartTest, 300, 70);
//		PersonGui pg3 = new PersonGui(new PersonAgent("C",new CityMap()),test, xStartTest, yStartTest, 300, 400);
//		PersonGui pg4 = new PersonGui(new PersonAgent("D",new CityMap()),test, xStartTest, yStartTest, 300, 190);
//		PersonGui pg5 = new PersonGui(new PersonAgent("E",new CityMap()),test, xStartTest, yStartTest, 190, 300);
//		PersonGui pg6 = new PersonGui(new PersonAgent("F",new CityMap()),test, xStartTest, yStartTest, 520, 300);
//		PersonGui pg7 = new PersonGui(new PersonAgent("G",new CityMap()),test, xStartTest, yStartTest, 400, 300);
//		PersonGui pg8 = new PersonGui(new PersonAgent("G",new CityMap()),test, xStartTest, yStartTest, 70, 300);
//		test.city.addMoving(pg1);
//		test.city.addMoving(pg2);
//		test.city.addMoving(pg3);
//		test.city.addMoving(pg4);
//		test.city.addMoving(pg5);
//		test.city.addMoving(pg6);
//		test.city.addMoving(pg7);
//		test.city.addMoving(pg8);



	}

	@Override
	public void actionPerformed(ActionEvent e) {
		time++;

	}

}
