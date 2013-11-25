package cityGui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PersonCreationPanel extends JFrame implements ActionListener{

	JButton add;
	JPanel namePanel,creationPanel;
	JTextField nameField;
	SimCityGui c;
	
	public PersonCreationPanel(SimCityGui c){
		this.c = c;
		
		setSize(300,500);
		setTitle("Person Creation Panel");
		
		//TITLE
		//JLabel titleLabel = new JLabel("Person Creation Panel");
		
		//Name Panel
		namePanel = new JPanel();
		//namePanel.setSize(200,100);
		namePanel.setLayout(new FlowLayout());
		
		JLabel nameLabel = new JLabel("Name");
		nameField = new JTextField();
		
		//Dimension d = new Dimension(100,10);
		Dimension b = new Dimension(100,20);
		nameField.setMinimumSize(b);
		nameField.setPreferredSize(b);
		nameField.setMaximumSize(b);
		//namePanel.setMinimumSize(d);
		//namePanel.setPreferredSize(d);
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		//Creation Action
		creationPanel = new JPanel();
		creationPanel.setSize(200,30);
		add = new JButton("Add");
		creationPanel.add(add);
		
		
		getContentPane().setLayout(new BoxLayout((Container) getContentPane(), BoxLayout.Y_AXIS));
		add(namePanel);
		add(creationPanel);
		
		
		setVisible(true);
		//validate();
		
		
		add.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(add)){
			c.addNewPerson();
			this.dispose();
		}
		
	}

	
	
	
}
