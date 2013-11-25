package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CitySidewalk extends CityComponent {
	
	private RoadDirection direction;
	boolean inner;
	
	public CitySidewalk(int x, RoadDirection direction, boolean inner, boolean water) {
		super(x, 0, Color.LIGHT_GRAY, "Side Walk");
		this.direction = direction;
		if (direction == RoadDirection.HORIZONTAL && !inner)
			rectangle = new Rectangle(40, x, 520, 40);
		else if (!inner)
			rectangle = new Rectangle(x, 40, 40, 520);
		if (direction == RoadDirection.HORIZONTAL && inner)
			rectangle = new Rectangle(160, x, 280, 40);
		else if (inner)
			rectangle = new Rectangle(x, 160, 40, 280);
		if (water) {
			this.color = Color.blue;
			rectangle = new Rectangle(240, 240, 120, 120);
		}
	}

	public CitySidewalk(int x, RoadDirection direction, String I) {
		super(x, 0, Color.gray, I);
		this.direction = direction;
	}

	public void updatePosition() {
	}

//	public void paint(Graphics g) {
//		g.setColor(color);
//		if (direction == RoadDirection.HORIZONTAL)
//			g.fillRect(0, x, 1000, 20);
//		else
//			g.fillRect(x, 0, 20, 1000);
//	}

//	public boolean contains(int x, int y) {
//		if (direction == RoadDirection.HORIZONTAL)
//			if (x >= this.x && x <= this.x+20)
//				return true;
//		if (direction == RoadDirection.VERTICAL)
//			if (y >= this.x && y <= this.x+20)
//				return true;
//		return false;
//	}

}
