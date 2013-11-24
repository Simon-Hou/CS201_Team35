package cityGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import person.PersonAgent;
import util.CityMap;
import city.CityObject;
import cityGui.test.PersonGui;
import cityGui.trace.AlertLog;
import cityGui.trace.AlertTag;

public class CityPanel extends SimCityPanel implements MouseMotionListener {

	public static final int CITY_WIDTH = 600, CITY_HEIGHT = 600;
	boolean addingObject = false;
	CityComponent temp;
	public CityObject cityObject;
	
	String name = "City Panel";
	
	public CityPanel(SimCityGui city) {
		super(city);
		//setSize(CITY_WIDTH,CITY_HEIGHT);
		this.setPreferredSize(new Dimension(CITY_WIDTH, CITY_HEIGHT));
		this.setVisible(true);
		
		background = new Color(0, 183, 96);
		this.addStatic(new CityRestaurant(30, 30));
		this.addStatic(new CityRestaurant(60, 30, "Restaurant 2"));
		for (int i = 80; i < 700; i += 360) {
			this.addStatic(new CityRoad(i, RoadDirection.HORIZONTAL));
			this.addStatic(new CityRoad(i, RoadDirection.VERTICAL));
		}
		for (int i = 40; i < 600; i += 480) {
			this.addStatic(new CitySidewalk(i, RoadDirection.HORIZONTAL,false));
			this.addStatic(new CitySidewalk(i, RoadDirection.VERTICAL,false));
		}
		for (int i = 160; i < 500; i += 240) {
			this.addStatic(new CitySidewalk(i, RoadDirection.HORIZONTAL,true));
			this.addStatic(new CitySidewalk(i, RoadDirection.VERTICAL,true));
		}
		for (int i = 120; i < 480; i += 30) {
			if (i != 300) {
				this.addStatic(new CityRoadLines(i, RoadDirection.HORIZONTAL,true));
				this.addStatic(new CityRoadLines(i, RoadDirection.VERTICAL,true));
				this.addStatic(new CityRoadLines(i, RoadDirection.HORIZONTAL,false));
				this.addStatic(new CityRoadLines(i, RoadDirection.VERTICAL,false));
			}
		}
		for (int i = 85; i < 160; i += 20) {
			this.addStatic(new CityCrosswalk(i, RoadDirection.VERTICAL,true));
		}
		for (int i = 445; i < 520; i += 20 ) {
			this.addStatic(new CityCrosswalk(i, RoadDirection.VERTICAL,false));
		}
		for (int i = 85; i < 160; i += 20) {
			this.addStatic(new CityCrosswalk(i, RoadDirection.HORIZONTAL,true));
		}
		for (int i = 445; i < 520; i += 20) {
			this.addStatic(new CityCrosswalk(i, RoadDirection.HORIZONTAL,false));
		}

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
	}
	
	public void mouseEntered(MouseEvent arg0) {
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	public void mousePressed(MouseEvent arg0) {
		if (addingObject) {
			//make sure we aren't overlapping anything
			for (CityComponent c: statics) {
				if (c.equals(temp))
					continue;
				if (c.rectangle.intersects(temp.rectangle)) {
					AlertLog.getInstance().logError(AlertTag.GENERAL_CITY, this.name, "Can't add building, location obstructed!");
					return;
				}
			}
			AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, this.name, "Building successfully added");
			addingObject = false;
			city.view.addView(new CityCard(city, Color.pink), temp.ID);
			temp.cityObject = this.cityObject;
			temp.addAgentObjectToMap();
			temp = null;
		}
		for (CityComponent c: statics) {
			if (c.contains(arg0.getX(), arg0.getY())) {
				//city.info.setText(c.ID);
				city.view.setView(c.ID);
				AlertLog.getInstance().logMessage(AlertTag.GENERAL_CITY, this.name, "Building Selected: " + c.ID);
			}
		}
	}
	
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	public void addObject(CityComponents c) {
		if (addingObject)
			return;
		addingObject = true;
		switch (c) {
		case RESTAURANT: temp = new CityRestaurant(-100, -100, "Restaurant " + (statics.size()-19)); break;
		case ROAD: temp = new CityRoad(-100, RoadDirection.HORIZONTAL); break; //NOTE: DON'T MAKE NEW ROADS
		case BANK: temp = new CityBank(-100, -100, "Bank " + (statics.size()-19)); break;
		default: return;
		}
		addStatic(temp);
	}

	public void mouseDragged(MouseEvent arg0) {
		
	}

	public void mouseMoved(MouseEvent arg0) {
		if (addingObject) {
			temp.setPosition(arg0.getPoint());
		}
	}

}
