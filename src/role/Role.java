package role;

import agent.Agent;

public abstract class Role extends Agent{

	
	public abstract boolean pickAndExecuteAnAction();
	
	public synchronized void startThread() {
        
		//Don't do anything! This is a role.
		System.err.println("Warning: Role thread is being started.");
		super.startThread();
		
    }
	
	
}
