package cityGui;

import interfaces.PlaceOfWork;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import market.Market;
import market.gui.MarketMain;
import market.gui.MarketPanel;
import person.PersonAgent;
import util.Bank;
import util.BankMapLoc;
import util.Bus;
import util.BusStop;
import util.CityMap;
import util.HouseMapLoc;
import util.Job;
import util.JobType;
import util.Loc;
import util.MarketMapLoc;
import util.RestaurantMapLoc;
import city.CityObject;
import cityGui.test.AStarTraversalPerson;
import cityGui.test.BusGui;
import cityGui.test.PersonGui;
import cityGui.trace.AlertLog;
import cityGui.trace.TracePanel;

public class SimCityGui extends JFrame implements ActionListener {

	//Creates a list of the different lists of sprites
	public List<ArrayList<ImageIcon>> upAll = new ArrayList<ArrayList<ImageIcon>>();
	public List<ArrayList<ImageIcon>> downAll = new ArrayList<ArrayList<ImageIcon>>();
	public List<ArrayList<ImageIcon>> leftAll = new ArrayList<ArrayList<ImageIcon>>();
	public List<ArrayList<ImageIcon>> rightAll = new ArrayList<ArrayList<ImageIcon>>();

	public List<ImageIcon> up = new ArrayList<ImageIcon>();
	java.net.URL up1 = getClass().getResource("personImages/up1.png");
	ImageIcon u1 = new ImageIcon(up1);
	java.net.URL up2 = getClass().getResource("personImages/up2.png");
	ImageIcon u2 = new ImageIcon(up2);
	java.net.URL up3 = getClass().getResource("personImages/up3.png");
	ImageIcon u3 = new ImageIcon(up3);
	public List<ImageIcon> down = new ArrayList<ImageIcon>();
	java.net.URL down1 = getClass().getResource("personImages/down1.png");
	ImageIcon d1 = new ImageIcon(down1);
	java.net.URL down2 = getClass().getResource("personImages/down2.png");
	ImageIcon d2 = new ImageIcon(down2);
	java.net.URL down3 = getClass().getResource("personImages/down3.png");
	ImageIcon d3 = new ImageIcon(down3);
	public List<ImageIcon> left = new ArrayList<ImageIcon>();
	java.net.URL left1 = getClass().getResource("personImages/left1.png");
	ImageIcon l1 = new ImageIcon(left1);
	java.net.URL left2 = getClass().getResource("personImages/left2.png");
	ImageIcon l2 = new ImageIcon(left2);
	java.net.URL left3 = getClass().getResource("personImages/left3.png");
	ImageIcon l3 = new ImageIcon(left3);
	public List<ImageIcon> right = new ArrayList<ImageIcon>();
	java.net.URL right1 = getClass().getResource("personImages/right1.png");
	ImageIcon r1 = new ImageIcon(right1);
	java.net.URL right2 = getClass().getResource("personImages/right2.png");
	ImageIcon r2 = new ImageIcon(right2);
	java.net.URL right3 = getClass().getResource("personImages/right3.png");
	ImageIcon r3 = new ImageIcon(right3);

	//Goku Sprites
	public List<ImageIcon> gup = new ArrayList<ImageIcon>();
	java.net.URL gup1 = getClass().getResource("personImages/gup1.png");
	ImageIcon gu1 = new ImageIcon(gup1);
	java.net.URL gup2 = getClass().getResource("personImages/gup2.png");
	ImageIcon gu2 = new ImageIcon(gup2);
	java.net.URL gup3 = getClass().getResource("personImages/gup3.png");
	ImageIcon gu3 = new ImageIcon(gup3);
	java.net.URL gup4 = getClass().getResource("personImages/gup4.png");
	ImageIcon gu4 = new ImageIcon(gup4);
	public List<ImageIcon> gdown = new ArrayList<ImageIcon>();
	java.net.URL gdown1 = getClass().getResource("personImages/gdown1.png");
	ImageIcon gd1 = new ImageIcon(gdown1);
	java.net.URL gdown2 = getClass().getResource("personImages/gdown2.png");
	ImageIcon gd2 = new ImageIcon(gdown2);
	java.net.URL gdown3 = getClass().getResource("personImages/gdown3.png");
	ImageIcon gd3 = new ImageIcon(gdown3);
	java.net.URL gdown4 = getClass().getResource("personImages/gdown4.png");
	ImageIcon gd4 = new ImageIcon(gdown4);
	public List<ImageIcon> gleft = new ArrayList<ImageIcon>();
	java.net.URL gleft1 = getClass().getResource("personImages/gleft1.png");
	ImageIcon gl1 = new ImageIcon(gleft1);
	java.net.URL gleft2 = getClass().getResource("personImages/gleft2.png");
	ImageIcon gl2 = new ImageIcon(gleft2);
	java.net.URL gleft3 = getClass().getResource("personImages/gleft3.png");
	ImageIcon gl3 = new ImageIcon(gleft3);
	java.net.URL gleft4 = getClass().getResource("personImages/gleft4.png");
	ImageIcon gl4 = new ImageIcon(gleft4);
	public List<ImageIcon> gright = new ArrayList<ImageIcon>();
	java.net.URL gright1 = getClass().getResource("personImages/gright1.png");
	ImageIcon gr1 = new ImageIcon(gright1);
	java.net.URL gright2 = getClass().getResource("personImages/gright2.png");
	ImageIcon gr2 = new ImageIcon(gright2);
	java.net.URL gright3 = getClass().getResource("personImages/gright3.png");
	ImageIcon gr3 = new ImageIcon(gright3);
	java.net.URL gright4 = getClass().getResource("personImages/gright4.png");
	ImageIcon gr4 = new ImageIcon(gright4);

	//Gohan Sprites
	public List<ImageIcon> ghup = new ArrayList<ImageIcon>();
	java.net.URL ghup1 = getClass().getResource("personImages/ghup1.png");
	ImageIcon ghu1 = new ImageIcon(ghup1);
	java.net.URL ghup2 = getClass().getResource("personImages/ghup2.png");
	ImageIcon ghu2 = new ImageIcon(ghup2);
	java.net.URL ghup3 = getClass().getResource("personImages/ghup3.png");
	ImageIcon ghu3 = new ImageIcon(ghup3);
	java.net.URL ghup4 = getClass().getResource("personImages/ghup4.png");
	ImageIcon ghu4 = new ImageIcon(ghup4);
	public List<ImageIcon> ghdown = new ArrayList<ImageIcon>();
	java.net.URL ghdown1 = getClass().getResource("personImages/ghdown1.png");
	ImageIcon ghd1 = new ImageIcon(ghdown1);
	java.net.URL ghdown2 = getClass().getResource("personImages/ghdown2.png");
	ImageIcon ghd2 = new ImageIcon(ghdown2);
	java.net.URL ghdown3 = getClass().getResource("personImages/ghdown3.png");
	ImageIcon ghd3 = new ImageIcon(ghdown3);
	java.net.URL ghdown4 = getClass().getResource("personImages/ghdown4.png");
	ImageIcon ghd4 = new ImageIcon(ghdown4);
	public List<ImageIcon> ghleft = new ArrayList<ImageIcon>();
	java.net.URL ghleft1 = getClass().getResource("personImages/ghleft1.png");
	ImageIcon ghl1 = new ImageIcon(ghleft1);
	java.net.URL ghleft2 = getClass().getResource("personImages/ghleft2.png");
	ImageIcon ghl2 = new ImageIcon(ghleft2);
	java.net.URL ghleft3 = getClass().getResource("personImages/ghleft3.png");
	ImageIcon ghl3 = new ImageIcon(ghleft3);
	java.net.URL ghleft4 = getClass().getResource("personImages/ghleft4.png");
	ImageIcon ghl4 = new ImageIcon(ghleft4);
	public List<ImageIcon> ghright = new ArrayList<ImageIcon>();
	java.net.URL ghright1 = getClass().getResource("personImages/ghright1.png");
	ImageIcon ghr1 = new ImageIcon(ghright1);
	java.net.URL ghright2 = getClass().getResource("personImages/ghright2.png");
	ImageIcon ghr2 = new ImageIcon(ghright2);
	java.net.URL ghright3 = getClass().getResource("personImages/ghright3.png");
	ImageIcon ghr3 = new ImageIcon(ghright3);
	java.net.URL ghright4 = getClass().getResource("personImages/ghright4.png");
	ImageIcon ghr4 = new ImageIcon(ghright4);

	//Hercule Sprites
	public List<ImageIcon> hup = new ArrayList<ImageIcon>();
	java.net.URL hup1 = getClass().getResource("personImages/hup1.png");
	ImageIcon hu1 = new ImageIcon(hup1);
	java.net.URL hup2 = getClass().getResource("personImages/hup2.png");
	ImageIcon hu2 = new ImageIcon(hup2);
	java.net.URL hup3 = getClass().getResource("personImages/hup3.png");
	ImageIcon hu3 = new ImageIcon(hup3);
	java.net.URL hup4 = getClass().getResource("personImages/hup4.png");
	ImageIcon hu4 = new ImageIcon(hup4);
	public List<ImageIcon> hdown = new ArrayList<ImageIcon>();
	java.net.URL hdown1 = getClass().getResource("personImages/hdown1.png");
	ImageIcon hd1 = new ImageIcon(hdown1);
	java.net.URL hdown2 = getClass().getResource("personImages/hdown2.png");
	ImageIcon hd2 = new ImageIcon(hdown2);
	java.net.URL hdown3 = getClass().getResource("personImages/hdown3.png");
	ImageIcon hd3 = new ImageIcon(hdown3);
	java.net.URL hdown4 = getClass().getResource("personImages/hdown4.png");
	ImageIcon hd4 = new ImageIcon(hdown4);
	public List<ImageIcon> hleft = new ArrayList<ImageIcon>();
	java.net.URL hleft1 = getClass().getResource("personImages/hleft1.png");
	ImageIcon hl1 = new ImageIcon(hleft1);
	java.net.URL hleft2 = getClass().getResource("personImages/hleft2.png");
	ImageIcon hl2 = new ImageIcon(hleft2);
	java.net.URL hleft3 = getClass().getResource("personImages/hleft3.png");
	ImageIcon hl3 = new ImageIcon(hleft3);
	java.net.URL hleft4 = getClass().getResource("personImages/hleft4.png");
	ImageIcon hl4 = new ImageIcon(hleft4);
	public List<ImageIcon> hright = new ArrayList<ImageIcon>();
	java.net.URL hright1 = getClass().getResource("personImages/hright1.png");
	ImageIcon hr1 = new ImageIcon(hright1);
	java.net.URL hright2 = getClass().getResource("personImages/hright2.png");
	ImageIcon hr2 = new ImageIcon(hright2);
	java.net.URL hright3 = getClass().getResource("personImages/hright3.png");
	ImageIcon hr3 = new ImageIcon(hright3);
	java.net.URL hright4 = getClass().getResource("personImages/hright4.png");
	ImageIcon hr4 = new ImageIcon(hright4);

	//	//Olibu Sprites
	//	public List<ImageIcon> oup = new ArrayList<ImageIcon>();
	//    java.net.URL oup1 = getClass().getResource("personImages/oup1.png");
	//	ImageIcon ou1 = new ImageIcon(oup1);
	//	java.net.URL oup2 = getClass().getResource("personImages/oup2.png");
	//	ImageIcon ou2 = new ImageIcon(oup2);
	//	java.net.URL oup3 = getClass().getResource("personImages/oup3.png");
	//	ImageIcon ou3 = new ImageIcon(oup3);
	//	public List<ImageIcon> odown = new ArrayList<ImageIcon>();
	//	java.net.URL odown1 = getClass().getResource("personImages/odown1.png");
	//	ImageIcon od1 = new ImageIcon(odown1);
	//	java.net.URL odown2 = getClass().getResource("personImages/odown2.png");
	//	ImageIcon od2 = new ImageIcon(odown2);
	//	java.net.URL odown3 = getClass().getResource("personImages/odown3.png");
	//	ImageIcon od3 = new ImageIcon(odown3);
	//	public List<ImageIcon> oleft = new ArrayList<ImageIcon>();
	//	java.net.URL oleft1 = getClass().getResource("personImages/oleft1.png");
	//	ImageIcon ol1 = new ImageIcon(oleft1);
	//	java.net.URL oleft2 = getClass().getResource("personImages/oleft2.png");
	//	ImageIcon ol2 = new ImageIcon(oleft2);
	//	java.net.URL oleft3 = getClass().getResource("personImages/oleft3.png");
	//	ImageIcon ol3 = new ImageIcon(oleft3);
	//	public List<ImageIcon> oright = new ArrayList<ImageIcon>();
	//	java.net.URL oright1 = getClass().getResource("personImages/oright1.png");
	//	ImageIcon or1 = new ImageIcon(oright1);
	//	java.net.URL oright2 = getClass().getResource("personImages/oright2.png");
	//	ImageIcon or2 = new ImageIcon(oright2);
	//	java.net.URL oright3 = getClass().getResource("personImages/oright3.png");
	//	ImageIcon or3 = new ImageIcon(oright3);

	//Piccolo Sprites
	//	public List<ImageIcon> pup = new ArrayList<ImageIcon>();
	//    java.net.URL pup1 = getClass().getResource("personImages/pup1.png");
	//	ImageIcon pu1 = new ImageIcon(pup1);
	//	java.net.URL pup2 = getClass().getResource("personImages/pup2.png");
	//	ImageIcon pu2 = new ImageIcon(pup2);
	//	java.net.URL pup3 = getClass().getResource("personImages/pup3.png");
	//	ImageIcon pu3 = new ImageIcon(pup3);
	//	public List<ImageIcon> pdown = new ArrayList<ImageIcon>();
	//	java.net.URL pdown1 = getClass().getResource("personImages/pdown1.png");
	//	ImageIcon pd1 = new ImageIcon(pdown1);
	//	java.net.URL pdown2 = getClass().getResource("personImages/pdown2.png");
	//	ImageIcon pd2 = new ImageIcon(pdown2);
	//	java.net.URL pdown3 = getClass().getResource("personImages/pdown3.png");
	//	ImageIcon pd3 = new ImageIcon(pdown3);
	//	public List<ImageIcon> pleft = new ArrayList<ImageIcon>();
	//	java.net.URL pleft1 = getClass().getResource("personImages/pleft1.png");
	//	ImageIcon pl1 = new ImageIcon(pleft1);
	//	java.net.URL pleft2 = getClass().getResource("personImages/pleft2.png");
	//	ImageIcon pl2 = new ImageIcon(pleft2);
	//	java.net.URL pleft3 = getClass().getResource("personImages/pleft3.png");
	//	ImageIcon pl3 = new ImageIcon(pleft3);
	//	public List<ImageIcon> pright = new ArrayList<ImageIcon>();
	//	java.net.URL pright1 = getClass().getResource("personImages/pright1.png");
	//	ImageIcon pr1 = new ImageIcon(pright1);
	//	java.net.URL pright2 = getClass().getResource("personImages/pright2.png");
	//	ImageIcon pr2 = new ImageIcon(pright2);
	//	java.net.URL pright3 = getClass().getResource("personImages/pright3.png");
	//	ImageIcon pr3 = new ImageIcon(pright3);

	
	//(-Parker's layout)
	public BuildingControlPanelHolder buildingCP;
	
	
	public CityPanel city;
	public CityObject cityObject;
	InfoPanel info;
	CityView view;
	CityControlPanel CP;
	TracePanel tracePanel;
	GridBagConstraints c = new GridBagConstraints();
	int SHIFTS = 2;
	int MAXTIME = 100;
	protected Timer timer;
	public long time=0;
	boolean hasBuses = false;

	
	JFrame traceFrame = new JFrame();


	int gridX = 600;
	int gridY = 600;
	double cityScale = 30;

	public Semaphore[][] grid = new Semaphore[(int) ((int) gridX/cityScale)][(int) ((int) gridY/cityScale)];


	public SimCityGui() throws HeadlessException {
		//Adds person images to its sprite array
		up.add(u1); up.add(u2); up.add(u1); up.add(u3);
		down.add(d1); down.add(d2); down.add(d1); down.add(d3);
		left.add(l1); left.add(l2); left.add(l1); left.add(l3);
		right.add(r1); right.add(r2); right.add(r1); right.add(r3);

		upAll.add((ArrayList<ImageIcon>) up);
		downAll.add((ArrayList<ImageIcon>) down);
		leftAll.add((ArrayList<ImageIcon>) left);
		rightAll.add((ArrayList<ImageIcon>) right);

		//Adds piccolo images to sprite arrays
		//		pup.add(pu1); pup.add(pu2); pup.add(pu1); pup.add(pu3);
		//		pdown.add(pd1); pdown.add(pd2); pdown.add(pd1); pdown.add(pd3);
		//		pleft.add(pl1); pleft.add(pl2); pleft.add(pl1); pleft.add(pl3);
		//		pright.add(pr1); pright.add(pr2); pright.add(pr1); pright.add(pr3);

		//Adds goku images to sprite arrays
		gup.add(gu1); gup.add(gu2); gup.add(gu3); gup.add(gu4);
		gdown.add(gd1); gdown.add(gd2); gdown.add(gd3); gdown.add(gd4);
		gleft.add(gl1); gleft.add(gl2); gleft.add(gl3); gleft.add(gl4);
		gright.add(gr1); gright.add(gr2); gright.add(gr3); gright.add(gr4);

		upAll.add((ArrayList<ImageIcon>) gup);
		downAll.add((ArrayList<ImageIcon>) gdown);
		leftAll.add((ArrayList<ImageIcon>) gleft);
		rightAll.add((ArrayList<ImageIcon>) gright);

		//		up.add(u1); up.add(u2); up.add(u1); up.add(u3); up.add(u4);
		//		down.add(d1); down.add(d2); down.add(d1); down.add(d3); up.add(d4);
		//		left.add(l1); left.add(l2); left.add(l1); left.add(l3); up.add(l4);
		//		right.add(r1); right.add(r2); right.add(r1); right.add(r3); up.add(r4);

		//Adds gohan images to sprite arrays
		ghup.add(ghu1); ghup.add(ghu2); ghup.add(ghu3); ghup.add(ghu4);
		ghdown.add(ghd1); ghdown.add(ghd2); ghdown.add(ghd3); ghdown.add(ghd4);
		ghleft.add(ghl1); ghleft.add(ghl2); ghleft.add(ghl3); ghleft.add(ghl4);
		ghright.add(ghr1); ghright.add(ghr2); ghright.add(ghr3); ghright.add(ghr4);

		upAll.add((ArrayList<ImageIcon>) ghup);
		downAll.add((ArrayList<ImageIcon>) ghdown);
		leftAll.add((ArrayList<ImageIcon>) ghleft);
		rightAll.add((ArrayList<ImageIcon>) ghright);

		//Adds hercule images to sprite arrays
		hup.add(hu1); hup.add(hu2); hup.add(hu3); hup.add(hu4);
		hdown.add(hd1); hdown.add(hd2); hdown.add(hd3); hdown.add(hd4);
		hleft.add(hl1); hleft.add(hl2); hleft.add(hl3); hleft.add(hl4);
		hright.add(hr1); hright.add(hr2); hright.add(hr3); hright.add(hr4);

		upAll.add((ArrayList<ImageIcon>) hup);
		downAll.add((ArrayList<ImageIcon>) hdown);
		leftAll.add((ArrayList<ImageIcon>) hleft);
		rightAll.add((ArrayList<ImageIcon>) hright);

		CP = new CityControlPanel(this);

		tracePanel = new TracePanel();
		tracePanel.setPreferredSize(new Dimension(CP.getPreferredSize().width, (int)(1.4*CP.getPreferredSize().height)));
		tracePanel.showAlertsForAllLevels();
		tracePanel.showAlertsForAllTags();


		//Makes the A* grid for the city
		initializeGrid();

		//THIS IS THE AGENT CITY
		cityObject = new CityObject(this);
		cityObject.MAXTIME = this.MAXTIME;

		city = new CityPanel(this);
		city.cityObject = cityObject;

		view = new CityView(this);

		info = new InfoPanel(this);

		///////-----v----v--v-----v-------PREVIOUS LAYOUT------v----v--v-----v-----v---v----////
//		this.setLayout(new GridBagLayout());

//		
//		//city animation
//		c.gridx = 0; c.gridy = 0;
//		c.gridwidth = 6; c.gridheight = 6;
//		this.add(city, c);
//
//		//building info (name)
//		c.gridx = 6; c.gridy = 0;
//		c.gridwidth = 5; c.gridheight = 1;
//		this.add(info, c);
//
//		//building animation view
//		c.gridx = 6; c.gridy = 1;
//		c.gridwidth = 5; c.gridheight = 5;
//		this.add(view, c);
//
//		//city control panel
//		c.gridx = 0; c.gridy = 6;
//		c.gridwidth = 11; c.gridheight = 1;
//		this.add(CP, c);
		
	/////////--^-----^------^------^----PREVIOUS LAYOUT-----^-----^-----^-----^-----^----////
	//			|	|		|		|						|		|	|		|	|
	///////-----v----v------v------v-NEW (PARKER) LAYOUT----v------v-----v-----v-----v----//// 
		this.setLayout(new GridBagLayout());
		//setBounds(0,0,1300,700);
		
		Dimension dim = new Dimension(180, 500); //x value can't be over 180
	
		buildingCP = new BuildingControlPanelHolder();
		
	
	
		
		JPanel trace = new JPanel();
		dim = new Dimension(500, 100);
		trace.setMaximumSize(dim);
		trace.setMinimumSize(dim);
		trace.setPreferredSize(dim);
		//trace.setBackground(Color.BLUE);
		
		JPanel cityCP = new JPanel();
		dim = new Dimension(600, 100); //y value can't be over 178
		cityCP.setMaximumSize(dim);
		cityCP.setMinimumSize(dim);
		cityCP.setPreferredSize(dim);
		//buildingCP.setBackground(Color.WHITE);
		
		
	
		//city animation
		c.gridx = 0; c.gridy = 0;
		c.gridwidth = 6; c.gridheight = 6;
		c.fill = GridBagConstraints.BOTH;
		this.add(city, c);

		//building animation view
		c.gridx = 6; c.gridy = 0;
		c.gridwidth = 5; c.gridheight = 5;
		c.fill = GridBagConstraints.BOTH;
		this.add(view, c);

		//building control panel
		c.gridx = 11; c.gridy = 0;
		c.gridwidth = 2; c.gridheight = 5;
		c.fill = GridBagConstraints.BOTH;
		this.add(buildingCP, c);
		//buildingCP.setBackground(Color.BLACK);
		
		//city control panel
		c.gridx = 0; c.gridy = 6;
		c.gridwidth = 6; c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(CP, c);

		//cityCP.setBackground(Color.WHITE);
		
		//trace log
		c.gridx = 6; c.gridy = 5;
		c.gridwidth =7; c.gridheight = 2; 
		c.fill = GridBagConstraints.BOTH;
		this.add(trace, c);
		trace.setBackground(Color.BLUE);
		


/////////--^-----^------^------^----NEW (PARKER) LAYOUT-----^-----^-----^-----^-----^----////
		
		timer = new Timer(10,  this);
		timer.start();
		/*
		c.gridx = 0; c.gridy = 7;
		c.gridwidth = 11; c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		this.add(tracePanel, c);*/
		//this.add(tracePanel, c)
		
		//traceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//traceFrame.setBounds(1000, 50 , 400, 300);
		//traceFrame.setVisible(true);
		//traceFrame.add(tracePanel);
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
		p.setAStar(new AStarTraversalPerson(grid));
		PersonGui personGui = new PersonGui(p,this,0,0,0,0);
		p.setGui(personGui);
		cityObject.people.add(p);
		city.addMoving(personGui);
		p.startThread();

	}

	public void addNewPersonHard(String name, PlaceOfWork placeOfWork,
			JobType jobType,int start,int end,int bankNum,int houseNum){

		PersonAgent person = new PersonAgent(name,cityObject.cityMap);
		person.setJob(placeOfWork, jobType, start, end);
		person.setBank(bankNum);
		//System.out.println(cityObject.cityMap.map.get("House").isEmpty());
		if(!cityObject.cityMap.map.get("House").isEmpty()){
			//System.out.println("Setting person's house to "+((HouseMapLoc) cityObject.cityMap.map.get("House").get(houseNum)).house.address.x + ", "+
			//		((HouseMapLoc) cityObject.cityMap.map.get("House").get(houseNum)).house.address.y);
			person.setHouse(((HouseMapLoc) cityObject.cityMap.map.get("House").get(houseNum)).house);
		}
		//person.setHouse(((HouseMapLoc) cityObject.cityMap.map.get("House").get(houseNum)).house);
		boolean wantsToRideBus = false;
		if(Math.random()>.75 && hasBuses){
			wantsToRideBus = true;
		}
		person.wantsToRideBus = wantsToRideBus;
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
			
			MarketPanel panel = new MarketPanel(tempAnimation, ((CityMarket)temp).market);
			buildingCP.addPanelCard(panel, temp.ID);
			
			((CityMarket)temp).market.setMarketPanel(panel);
			tempAnimation.setPanel(panel);
			city.markets.add(((CityMarket)temp).market);
			this.view.addView(tempAnimation, temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
			city.statics.add(temp);
			return;
		}

		if(type.contains("Restaurant")){
			CityRestaurant temp = null;

			if (type.contains("Linda"))
			{
				temp = new CityRestaurantLinda(x, y, "RestaurantLinda " + (city.statics.size()-19));

			}


			else if(type.contains("Gabe")){
				temp = new CityRestaurantGabe(x,y, "RestaurantGabe " + (city.statics.size()-19));}
				else if(type.contains("Simon")) {
					temp = new CityRestaurantSimon(x, y, "RestaurantLinda " + (city.statics.size()-19));
				}

				temp.createAnimationPanel(this);
				city.restaurants.add(temp.restaurant);
				this.view.addView(temp.animationPanel, temp.ID);
				temp.cityObject = this.cityObject;
				temp.addAgentObjectToMap();
				city.statics.add(temp);
				return;
			}
			/*
		else if(type.equals("RestaurantXXX")){
			CityComponent temp = new CityRestaurantXXX(x, y, "Restaurant " + (city.statics.size()-19));
			CityRestaurantLindaCard tempAnimation = new CityRestaurantLindaCard(this);
			((CityRestaurant)temp).setAnimationPanel(tempAnimation);
			city.restaurants.add(((CityRestaurant)temp).restaurant);
			this.view.addView(tempAnimation, temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
			city.statics.add(temp);
			return;
		}*/
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
				//int randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
				int randOffset = 0;
				//System.out.println("Rand offset: "+randOffset);
				for(int i = 0;i<SHIFTS;++i){
					int start = (i*(MAXTIME/SHIFTS) + randOffset+MAXTIME-2)%MAXTIME;
					int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset+2)%MAXTIME;
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
				randOffset = 0;
				for(int i = 0;i<SHIFTS;++i){
					int start = (i*(MAXTIME/SHIFTS) + randOffset+MAXTIME-2)%MAXTIME;
					int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset+2)%MAXTIME;
					System.out.println(j+ " Shift start, end: "+start+" " +end);
					int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
					int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
					addNewPersonHard("p"+j,
							((MarketMapLoc) cityObject.cityMap.map.get("Market").get(num)).market,
							JobType.MarketHost,start,end,bankNum,houseNum);
					j = j+1;
				}
				randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
				randOffset = 0;
				for(int i = 0;i<SHIFTS;++i){
					int start = (i*(MAXTIME/SHIFTS) + randOffset+MAXTIME-2)%MAXTIME;
					int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset+2)%MAXTIME;
					System.out.println(j+" Shift start, end: "+start+" " +end);
					int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
					int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
					addNewPersonHard("p"+j,
							((MarketMapLoc) cityObject.cityMap.map.get("Market").get(num)).market,
							JobType.MarketCashier,start,end,bankNum,houseNum);
					j = j+1;
				}

				//for(int numEmployees = 0;numEmployees<2;++numEmployees){
				randOffset = (int) Math.floor(MAXTIME/SHIFTS/2*Math.random());
				randOffset = 0;
				for(int i = 0;i<SHIFTS;++i){
					int start = (i*(MAXTIME/SHIFTS) + randOffset+MAXTIME-2)%MAXTIME;
					int end = ((i+1)*(MAXTIME/SHIFTS) + randOffset+2)%MAXTIME;
					System.out.println(j+" Shift start, end: "+start+" " +end);
					int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
					int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());
					addNewPersonHard("p"+j,
							((MarketMapLoc) cityObject.cityMap.map.get("Market").get(num)).market,
							JobType.MarketEmployee,start,end,bankNum,houseNum);
					j = j+1;

				}
				//}
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
					addNewPersonHard("pboringwaiter"+j,
							((RestaurantMapLoc) cityObject.cityMap.map.get("Restaurant").get(num)).restaurant,
							JobType.RestaurantWaiter1,start,end,bankNum,houseNum);
					addNewPersonHard("pnewwaiter"+j,
							((RestaurantMapLoc) cityObject.cityMap.map.get("Restaurant").get(num)).restaurant,
							JobType.RestaurantWaiter2,start,end,bankNum,houseNum);
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

			cityObject.cityMap.fStops.add(new BusStop(new Loc(170,130)));
			cityObject.cityMap.fStops.add(new BusStop(new Loc(380,450)));

			cityObject.cityMap.fStops.get(0).sidewalkLoc = new Loc(180,160);
			cityObject.cityMap.fStops.get(1).sidewalkLoc = new Loc(410,430);

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

			//System.out.println(""+ (-2)%100);

			SimCityGui test = new SimCityGui();
			test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			test.setResizable(false);
			test.pack();
			test.setVisible(true);

			int xStartTest = 0;
			int yStartTest = 0;

			//test.gabeRestaurant();



			//THIS SHOWS THE MARKET TESTS I'VE (GABE) BEEN WORKING ON
			//test.marketScenario();

			/*test.addNewBuilding("Market", 250, 200);
		test.addNewPersonHard("p"+0,
				((MarketMapLoc) test.cityObject.cityMap.map.get("Market").get(0)).market,
				JobType.MarketHost,0,33,0,0);
		test.addNewPersonHard("p"+1,
				((MarketMapLoc) test.cityObject.cityMap.map.get("Market").get(0)).market,
				JobType.MarketEmployee,0,33,0,0);*/
			//Bank b = test.cityObject.cityMap.map.get("Bank").get(0).bank;
			//test.addNewPerson("p0");
			//HACK ADDS BUILDINGS TO THE MAP

			//test.addBuses(test);


			//		test.addBuses(test);

			//test.addNewBuilding("Bank", 5, 400);
			//test.addNewBuilding("Market",200,250);
			//test.addNewBuilding("RestaurantLinda", 5, 300);
			//		test.addNewBuilding("House", 200, 5);
			//test.fullyManBuilding("Bank",0);
			//test.fullyManBuilding("Market",0);
			//test.fullyManBuilding("Restaurant",0);

			//		test.fullyManBuilding("Bank",0);
			//		test.fullyManBuilding("Market",0);
			//		test.fullyManBuilding("Market",1);
			//test.addBuses(test);
			//test.addNewBuilding("Bank", 5, 400);
			//test.addNewBuilding("Market",200,250);
			//test.addNewBuilding("Restaurant", 5, 200);
			//test.addNewBuilding("House", 200, 5);

			//test.addBuses(test);
			//test.addNewBuilding("Bank", 5, 400);
			//test.addNewBuilding("Market",200,250);
			//test.addNewBuilding("Restaurant", 5, 200);

			//test.fullyManBuilding("Bank",0);
			//test.fullyManBuilding("Market",0);
			//test.fullyManBuilding("Market",1);
			//test.fullyManBuilding("Bank",0);
			//test.fullyManBuilding("Market",0);


			//PARKER TESTING
			//		test.addNewBuilding("Market", 200,250);
			//		test.fullyManBuilding("Market", 0);


			//Bank b = test.cityObject.cityMap.map.get("Bank").get(0).bank;
			//test.addNewPerson("p0");
			//test.addNewPerson("p1");
			//test.cityObject.people.get(0).setJob(placeOfWork, jobType, start, end);
			/*cityObject.people.add(new PersonAgent("p0",cityObject.cityMap));
		cityObject.people.get(0).startThread();*/

			//		int xStartTest = 300;
			//		int yStartTest = 520;


			//		PersonGui pg1 = new PersonGui(new PersonAgent("A",new CityMap()),test, xStartTest, yStartTest, 300, 520);
			//      PersonGui pg2 = new PersonGui(new PersonAgent("B",new CityMap()),test, xStartTest, yStartTest, 300, 70);
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


		public void busRideScenario(){

			hasBuses = true;

			addNewBuilding("House", 200, 5);
			addBuses(this);

			PersonAgent busRider = new PersonAgent("Rider",this.cityObject.cityMap);
			busRider.myJob = new Job();
			busRider.wantsToRideBus = true;
			busRider.setHouse(((HouseMapLoc) cityObject.cityMap.map.get("House").get(0)).house);

			addNewPerson(busRider);

		}

		public void bankScenario(){
			hasBuses = false;
			setMAXTIME(50);
			addNewBuilding("House", 200, 5);
			addNewBuilding("Bank",200,250);
			fullyManBuilding("Bank",0);

		}

		public void marketScenario(){
			hasBuses = false;
			setMAXTIME(50);
			//cityObject.MAXTIME = 50;
			addNewBuilding("House", 200, 560);
			addNewBuilding("Market",250,200);

			//fullyManBuilding("Market",0);


			addNewPersonHard("p"+0,
					((MarketMapLoc) cityObject.cityMap.map.get("Market").get(0)).market,
					JobType.MarketHost,0,5,0,0);

			addNewPersonHard("p"+1,
					((MarketMapLoc) cityObject.cityMap.map.get("Market").get(0)).market,
					JobType.MarketEmployee,0,5,0,0);

			addNewPersonHard("p"+2,
					((MarketMapLoc) cityObject.cityMap.map.get("Market").get(0)).market,
					JobType.MarketCashier,0,6,0,0);

			addNewPersonHard("replacementHOST",
					((MarketMapLoc) cityObject.cityMap.map.get("Market").get(0)).market,
					JobType.MarketHost,5,100,0,0);

			addNewPersonHard("replacementEMPLOYEE",
					((MarketMapLoc) cityObject.cityMap.map.get("Market").get(0)).market,
					JobType.MarketEmployee,4,100,0,0);


			addNewPersonHard("relacementCASHIER",
					((MarketMapLoc) cityObject.cityMap.map.get("Market").get(0)).market,
					JobType.MarketCashier,6,100,0,0);


			/*try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/



			addNewPersonHard("p"+3,null,null,0,0,0,0);
			addNewPersonHard("p"+4,null,null,0,0,0,0);


			/*addNewPersonHard("p"+5,null,null,0,0,0,0);
		addNewPersonHard("p"+6,null,null,0,0,0,0);
		addNewPersonHard("p"+7,null,null,0,0,0,0);
		addNewPersonHard("p"+8,null,null,0,0,0,0);
		addNewPersonHard("p"+9,null,null,0,0,0,0);
		addNewPersonHard("p"+10,null,null,0,0,0,0);*/

			//fullyManBuilding("Market",0);
		}

		public void busyScenario(){
			hasBuses = true;
			addBuses(this);
			addNewBuilding("Bank", 5, 400);
			addNewBuilding("Market",200,250);
			addNewBuilding("Market", 250, 200);
			addNewBuilding("House", 200, 5);
			addNewBuilding("House", 500, 5);
			fullyManBuilding("Bank",0);
			fullyManBuilding("Market",0);
			fullyManBuilding("Market",1);
		}

		public void gabeRestaurant(){
			addNewBuilding("RestaurantGabe",200,250);
			addNewBuilding("House", 500, 5);


			addNewPersonHard("p0",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantHost,0,15, 0,0);
			addNewPersonHard("p1",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantWaiter1,0,15, 0,0);
			addNewPersonHard("p2",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantWaiter1,0,15, 0,0);
			addNewPersonHard("p3",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantCook,0,15, 0,0);
			addNewPersonHard("p4",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantCashier,0,15, 0,0);

			addNewPersonHard("replacementHOST",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantHost,11,100, 0,0);
			addNewPersonHard("replacementCASHIER",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantCashier,11,100, 0,0);
			addNewPersonHard("replacementCOOK",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantCook,11,100, 0,0);

			addNewPersonHard("replacementWAITER",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantWaiter1,22,100, 0,0);
			//addNewPersonHard("replacementCASHIER",((RestaurantMapLoc)this.cityObject.cityMap.map.get("Restaurant").get(0)).restaurant, JobType.RestaurantCashier,10,100, 0,0);


			//System.out.println(cityObject.cityMap.map.get("House").get(0).loc.x +", "+cityObject.cityMap.map.get("House").get(0).loc.y);


		}

		public void restaurantScenario(String scenarioName){
			hasBuses = false;
			setMAXTIME(20);
			addNewBuilding("House", 200, 5);
			addNewBuilding(scenarioName,5, 300);
			fullyManBuilding("Restaurant",0);

			int bankNum = (int) Math.floor(cityObject.cityMap.map.get("Bank").size()*Math.random());
			int houseNum = (int) Math.floor(cityObject.cityMap.map.get("House").size()*Math.random());

			PersonAgent person = new PersonAgent("restaurant",cityObject.cityMap);
			person.setJob(null, JobType.NOTSELECTED, 0, 0);
			person.setBank(bankNum);
			if(!cityObject.cityMap.map.get("House").isEmpty()){
				person.setHouse(((HouseMapLoc) cityObject.cityMap.map.get("House").get(houseNum)).house);
			}
			//person.setHouse(((HouseMapLoc) cityObject.cityMap.map.get("House").get(houseNum)).house);
			boolean wantsToRideBus = false;
			if(Math.random()>.75 && hasBuses){
				wantsToRideBus = true;
			}
		}

		public void restaurantSimonScenario(){
			hasBuses = false;
			setMAXTIME(50);
			addNewBuilding("House", 200, 5);
			addNewBuilding("Resataurant",200,250);
			fullyManBuilding("Restaurant",1);

		}

		public void setMAXTIME(int m){
			MAXTIME = m;
			cityObject.MAXTIME = m;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			time++;

		}

		public void initializeGrid(){
			for (int i=0; i<gridX/cityScale ; i++)
				for (int j = 0; j<gridY/cityScale; j++)
					grid[i][j]=new Semaphore(1,true);

			try{
				for(int i = (int) Math.ceil(200/cityScale);i<(int) Math.floor(400/cityScale);++i){
					for(int j = (int) Math.ceil(200/cityScale);j<(int) Math.floor(400/cityScale);++j){
						//System.out.println("("+i+","+j+")");
						grid[i][j].acquire();
					}
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}


		}

	}
