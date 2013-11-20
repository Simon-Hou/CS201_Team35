package house.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import public_Gui.Gui;

public class HouseAnimationPanel  extends JPanel implements ActionListener {
	private final int WINDOWX=300;
	private final int WINDOWY=300;
	public static final int frameInterval = 12;
	public static final int xSize = 50;
	public static final int ySize = 50;

	private Image bufferImage;
	private Dimension bufferSize;

	private List<Gui> guis = new ArrayList<Gui>();

	public HouseAnimationPanel() {
		setSize(WINDOWX, WINDOWY);
		setVisible(true);

		bufferSize = this.getSize();

		Timer timer = new Timer(frameInterval, this );
		timer.start();
	}


	public void actionPerformed(ActionEvent e) {
		repaint(); 

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(getBackground());
		g2.fillRect(0, 0, WINDOWX, WINDOWY );
		
		//Here is the table
		g2.setColor(Color.ORANGE);
		g2.fillRect((int)(WINDOWX*0.5), (int)(WINDOWY*0.5), xSize, ySize);//200 and 250 need to be table params

		//refrigerator
		g2.setColor(Color.WHITE);
		g2.fillRect((int)(WINDOWX*0.1),(int)(WINDOWY*0.5),xSize,ySize);
		
		for(Gui gui : guis) {
			if (gui.isPresent()) {
				gui.updatePosition();
			}
		}

		for(Gui gui : guis) {
			if (gui.isPresent()) {
				gui.draw(g2);
			}
		}
		
	}
	
	public void addGui(InhabitantGui gui) {
		guis.add(gui);
	}

}
