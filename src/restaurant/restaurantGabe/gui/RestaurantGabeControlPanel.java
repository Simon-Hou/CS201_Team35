package restaurant.restaurantGabe.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import public_Object.Food;
import restaurant.restaurantGabe.RestaurantGabe;
import cityGui.BuildingControlPanel;
import cityGui.CityRestaurantCardGabe;

public class RestaurantGabeControlPanel  extends BuildingControlPanel  implements ActionListener{
	//data
		RestaurantGabe restaurant;
		CityRestaurantCardGabe animation;
		private JLabel title=new JLabel("Gabe's Restaurant");
		private List<InventoryItem> inventoryList = new ArrayList<InventoryItem>();
		public JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		private JPanel view = new JPanel();
		
		public RestaurantGabeControlPanel(CityRestaurantCardGabe anim, RestaurantGabe r){
			restaurant=r;
			animation=anim;
			int WINDOWX = 180;//300; there are the fixed size of control panel
			int WINDOWY = 500; //500;
			setVisible(true);
			setLayout(new FlowLayout());
			add(title);
			view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
			pane.setViewportView(view);
			add(pane);
			Dimension paneSize = new Dimension (WINDOWX,300);
			pane.setPreferredSize(paneSize);
			pane.setMinimumSize(paneSize);
			pane.setMaximumSize(paneSize);
			Dimension buttonSize = new Dimension(paneSize.width-20, (int) (paneSize.height / 10));

			validate();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
//			for (InventoryItem item : inventoryList){
//				if (e.getSource() == item.minus && item.inventory >0){
//					item.inventory--;
//					for(Food food: house.room.inventory){
//						if(item.choice.equals(food.type)){
//							food.quantity--;
//						}
//					}
//					updateInventory();
//				}
//				else if (e.getSource() == item.plus){
//					item.inventory++;
//					for(Food food: house.room.inventory){
//						if(item.choice.equals(food.type)){
//							food.quantity++;
//						}
//					}
//					updateInventory();
//				}
//			}
		}
		public void updateInventory(){
//			for (InventoryItem item : inventoryList){
//				for(Food food: house.room.inventory){
//					if(item.choice.equals(food.type)){
//						item.inventory=food.quantity;
//						item.inventoryLabel.setText(item.inventory.toString());
//					}
//				}
//			}
		}
		private class InventoryItem extends JPanel{

			RestaurantGabeControlPanel rcp;
			RestaurantGabe restaurant;

			String choice;
			Integer inventory;

			JButton minus = new JButton("-");
			JLabel choiceLabel = new JLabel();
			JLabel inventoryLabel = new JLabel();
			JButton plus = new JButton("+");

			InventoryItem(String name, int invent, RestaurantGabe r, RestaurantGabeControlPanel hrcp){
				this.rcp = rcp;
				choice = name;
				inventory = invent;
				restaurant = r;

				setLayout(new GridLayout(1,5,5,5));
				setBorder(BorderFactory.createRaisedBevelBorder());


				choiceLabel.setText(choice);
				inventoryLabel.setText(inventory.toString());

				//choiceLabel.setHorizontalTextPosition(JLabel.RIGHT);
				//inventoryLabel.setVerticalTextPosition(SwingConstants.CENTER);

				// button1.addActionListener(this);
				minus.addActionListener(rcp);
				plus.addActionListener(rcp);


				add(minus);
				add(choiceLabel);
				add(inventoryLabel);
				add(plus);

			}
		}

}
