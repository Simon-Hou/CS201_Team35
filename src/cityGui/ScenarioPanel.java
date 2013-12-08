package cityGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScenarioPanel  extends JFrame implements ActionListener{

	SimCityGui city;
	
	JButton run = new JButton("Run");
	
	public ScenarioPanel(SimCityGui c){
		this.city  = c;
		
		setSize(400,700);
		setResizable(false);
		setTitle("Scenario Selection Panel");
		
		JLabel scenarioLabel = new JLabel("Please Select a Scenario:");
		Dimension d = new Dimension(200,30);
		scenarioLabel.setMinimumSize(d);
		scenarioLabel.setPreferredSize(d);
		scenarioLabel.setMaximumSize(d);
		
		JPanel scenarioNames = new JPanel();
		
		String[] scenarioLabels = {
				"A. Tests all behaviors",
		};
		
		JList test = new JList(scenarioLabels);
		JScrollPane scenarioS = new JScrollPane(test);
		
		Dimension d2 = new Dimension(300,500);
		scenarioNames.setMinimumSize(d2);
		scenarioNames.setPreferredSize(d2);
		scenarioNames.setMaximumSize(d2);
		scenarioS.setMinimumSize(d2);
		scenarioS.setPreferredSize(d2);
		scenarioS.setMaximumSize(d2);
		
		
		scenarioNames.add(scenarioS);
		
		setLayout(new FlowLayout());
		
		
		Dimension d3 = new Dimension(100,30);
		run.setMinimumSize(d3);
		run.setPreferredSize(d3);
		run.setMaximumSize(d3);
		
		
//		add(scenarioLabel,BorderLayout.NORTH);
//		add(scenarioNames,BorderLayout.CENTER);
//		add(run,BorderLayout.SOUTH);
		
		add(scenarioLabel);
		add(scenarioNames);
		add(run);
		
		setVisible(true);
		//validate();
		
		
		run.addActionListener(this);
		
		
		
		
		
		
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getSource().equals(run)){
			
//			*****Put your scenarios in here***** 
			
			
			
			
		}
		
		
		
	}
	
}
