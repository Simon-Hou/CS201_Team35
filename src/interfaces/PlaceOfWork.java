package interfaces;

import role.Role;
import util.JobType;

public interface PlaceOfWork {

	public abstract Role canIStartWorking(Person p,JobType type,Role r);
	
}
