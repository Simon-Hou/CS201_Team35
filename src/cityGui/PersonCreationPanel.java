package cityGui;

import interfaces.Person;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import person.PersonAgent;
import util.BankMapLoc;
import util.Place;

public class PersonCreationPanel extends JFrame implements ActionListener, ListSelectionListener{

	JButton add;
	JPanel namePanel,creationPanel;
	/*JScrollPane scrollPlacesToWork =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane scrollTypesOfJobs =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);*/
	JList placesToWork;
	JList typesOfJobs;
	JList<String> placeOptions;
	JList<String> jobsOptions;
	DefaultListModel<String> listModel2;
	JTextField nameField, shiftStartField,shiftEndField,errorField;
	SimCityGui c;
	
	public PersonCreationPanel(SimCityGui c){
		this.c = c;
		
		setSize(600,400);
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
		
		
		
		//Place Of Work Panel
		JPanel placeOfWorkPanel = new JPanel();
		JLabel placeOfWorkLabel = new JLabel("Employer");
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		placeOptions = new JList<String>(listModel);
		listModel.add(listModel.size(), "None");
		int i = 0;
		for(Place bankPlace:c.cityObject.cityMap.map.get("Bank")){
			listModel.add(listModel.size(), "Bank "+(i+1));
			i+=1;
		}
		i = 0;
		for(Place marketPlace:c.cityObject.cityMap.map.get("Market")){
			listModel.add(listModel.size(), "Market "+(i+1));
			i+=1;
		}
		i = 0;
		for(Place restaurantPlace:c.cityObject.cityMap.map.get("Restaurant")){
			listModel.add(listModel.size(), "Restaurant "+(i+1));
			i+=1;
		}
		
		//String [] placeOptions = {"Bank 1","Bank 2","Bank 3","Bank 4","Bank 5"};
		//placesToWork = new JList(placeOptions);
		placeOptions.addListSelectionListener(this);
		JScrollPane placesS = new JScrollPane(placeOptions);
		Dimension ld = new Dimension(150,60);
		//placesS.setMinimumSize(ld);
		placesS.setPreferredSize(ld);
		//placesS.setMaximumSize(ld);
		/*scrollPlacesToWork.setMinimumSize(ld);
		scrollPlacesToWork.setPreferredSize(ld);
		scrollPlacesToWork.add(placesToWork);*/
		placeOfWorkPanel.setLayout(new FlowLayout());
		placeOfWorkPanel.add(placeOfWorkLabel);
		placeOfWorkPanel.add(placesS);
		
		//JobType Panel
		JPanel jobTypePanel = new JPanel();
		JLabel jobTypeLabel = new JLabel("Job");
		listModel2 = new DefaultListModel<String>();
		jobsOptions = new JList<String>(listModel2);
		//String [] jobOptions = {"Market Employee","Bank 2","Bank 3","Bank 4","Bank 5"};
		//typesOfJobs = new JList(jobOptions);
		//jobsOptions.addListSelectionListener(this);
		JScrollPane jobsS = new JScrollPane(jobsOptions);
		
		Dimension d2 = new Dimension(150,60);
		jobsS.setMinimumSize(d2);
		jobsS.setPreferredSize(d2);
		jobsS.setMaximumSize(d2);
		//JScrollPane jobsS = new JScrollPane(typesOfJobs);
		/*jobTypePanel.setLayout(new FlowLayout());
		jobTypePanel.add(jobTypeLabel);
		jobTypePanel.add(jobsS);*/
		placeOfWorkPanel.setLayout(new FlowLayout());
		placeOfWorkPanel.add(jobTypeLabel);
		placeOfWorkPanel.add(jobsS);
		
		//ShiftTimes Panel
		JPanel shiftTimes = new JPanel();
		JLabel shiftStartLabel = new JLabel("Shift start");
		JLabel shiftEndLabel = new JLabel("Shift end");
		shiftStartField = new JTextField();
		shiftEndField = new JTextField();
		Dimension shiftDim = new Dimension(75,20);
		
		shiftStartField.setPreferredSize(shiftDim);
		shiftEndField.setPreferredSize(shiftDim);
		shiftTimes.setLayout(new FlowLayout());
		shiftTimes.add(shiftStartLabel);
		shiftTimes.add(shiftStartField);
		shiftTimes.add(shiftEndLabel);
		shiftTimes.add(shiftEndField);
		
		
		//Creation Action
		creationPanel = new JPanel();
		creationPanel.setSize(200,30);
		add = new JButton("Add");
		creationPanel.add(add);
		
		//ErrorField
		JPanel errorPanel = new JPanel();
		errorField = new JTextField();
		errorField.setEditable(false);
		Dimension errorDim = new Dimension(400,20);
		errorField.setPreferredSize(errorDim);
		errorPanel.add(errorField);
		
		
		getContentPane().setLayout(new BoxLayout((Container) getContentPane(), BoxLayout.Y_AXIS));
		add(namePanel);
		//add(scrollPlacesToWork);
		//add(placesS);
		add(placeOfWorkPanel);
		//add(jobsS);
		//add(jobTypePanel);
		add(shiftTimes);
		add(creationPanel);
		add(errorPanel);
		
		//pack();
		setVisible(true);
		//validate();
		
		
		add.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(add)){
			if(addNewPerson()){
				this.dispose();
			}
			
		}
		
	}
	
	private boolean addNewPerson(){
		
		String name = nameField.getText();
		if(name.length()==0){
			errorField.setText("Please enter a name");
			return false;
		}
		if(placeOptions.getSelectedValue()==null){
			errorField.setText("Please select an employer");
			return false;
		}
		if(!placeOptions.getSelectedValue().toString().equals("None") &&
				jobsOptions.getSelectedValue() == null){
			errorField.setText("Please select a job");
			return false;
		}
		if(shiftStartField.getText().length()==0 || shiftEndField.getText().length()==0){
			errorField.setText("Please select shift start and end times");
			return false;
		}
		PersonAgent p  = new PersonAgent(name,c.cityObject.cityMap);
		c.addNewPerson(p);
		return true;
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(placeOptions.getSelectedValue().toString().equals("None")){
			listModel2.clear();
			return;
		}
		if(placeOptions.getSelectedValue().toString().contains("Bank")){
			listModel2.clear();
			listModel2.add(0,"Bank Teller");
			return;
		}
		if(placeOptions.getSelectedValue().toString().contains("Market")){
			listModel2.clear();
			listModel2.add(listModel2.size(),"Market Host");
			listModel2.add(listModel2.size(),"Market Cashier");
			listModel2.add(listModel2.size(),"Market Employee");
			listModel2.add(listModel2.size(),"Market Delivery Man");
			return;
		}
		if(placeOptions.getSelectedValue().toString().contains("Restaurant")){
			listModel2.clear();
			listModel2.add(listModel2.size(),"Restaurant Host");
			listModel2.add(listModel2.size(),"Restaurant Cashier");
			listModel2.add(listModel2.size(),"Restaurant Waiter");
			listModel2.add(listModel2.size(),"Restaurant Cook");
			return;
		}
		
	}

	
	
	
}
