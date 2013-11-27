package house.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import cityGui.CityHouseCard;
import public_Gui.Gui;
import house.InhabitantRole;





public class InhabitantGui implements Gui {


	private InhabitantRole role = null;
	
	private boolean pause=false;

	private int xInitial=-20;
	private int yInitial=0;
	private  int xPos = xInitial;//default inhabitant position
	private  int yPos = yInitial;
	private int xResting=100;
	private int yResting=100;		
	private int xDestination = xInitial, 		yDestination = yInitial;//default start position
	public static final int xSize=20;
	public static final int ySize=20;

	Timer timer;


	private Semaphore atBed = new Semaphore(0,true);
	private Semaphore atFridge = new Semaphore(0,true);
	private Semaphore atGrill = new Semaphore(0,true);
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atExit = new Semaphore(0,true);



	public InhabitantGui(InhabitantRole role){
		this.role = role;
	}

	/*public InhabitantGui(InhabitantRole agent,HouseGui gui) {
		this.role = agent;
		this.gui=gui;
	}*/

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

			if(xPos==CityHouseCard.bedX && yPos==CityHouseCard.bedY)
			{

				if(atBed.availablePermits()==0){
					atBed.release();
				}
			}
			if(xPos==CityHouseCard.grillX && yPos==CityHouseCard.grillY)
			{

				if(atGrill.availablePermits()==0){
					atGrill.release();
				}
			}
			if(xPos==CityHouseCard.refrigeratorX && yPos==CityHouseCard.refrigeratorY)
			{

				if(atFridge.availablePermits()==0){
					atFridge.release();
				}
			}
			if(xPos==CityHouseCard.tableX && yPos==CityHouseCard.tableY)
			{

				if(atTable.availablePermits()==0){
					atTable.release();
				}
			}
			if(xPos==xInitial && yPos==yInitial)
			{

				if(atExit.availablePermits()==0){
					atExit.release();
				}
			}
		}

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(xPos, yPos, xSize, ySize);

	}

	@Override
	public boolean isPresent() {
		return true;
	}

	public void DoSleep(){

		if(atBed.availablePermits()!=0){
			try {
				atBed.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		xDestination = CityHouseCard.bedX-xSize;
		yDestination = CityHouseCard.bedY;
		try {
			atBed.acquire();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void DoGoToFridge(){
		if(atFridge.availablePermits()!=0){
			try {
				atFridge.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		xDestination = CityHouseCard.refrigeratorX-xSize;
		yDestination = CityHouseCard.refrigeratorY;
		try {
			atFridge.acquire();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void DoIdle(){
		xDestination = xResting;
		yDestination = yResting;
	}

	public void DoGoToGrill(){
		if(atGrill.availablePermits()!=0){
			try {
				atGrill.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		xDestination = CityHouseCard.grillX-xSize;
		yDestination = CityHouseCard.grillY;
		try {
			atGrill.acquire();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void DoGoToTable(){
		if(atTable.availablePermits()!=0){
			try {
				atTable.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		xDestination = CityHouseCard.tableX-xSize;
		yDestination = CityHouseCard.tableY;
		try {
			atTable.acquire();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	public void DoPlateAndEat() {
		DoGoToGrill();
		DoGoToTable();
		timer.schedule(new TimerTask() {	
			public void run() {
				System.out.println("Eating");
			}
		},
		500);

	}

	public void DoExit() {
		if(atExit.availablePermits()!=0){
			try {
				atBed.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		xDestination = xInitial;
		yDestination = yInitial;
		try {
			atExit.acquire();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}










}
