package util;

import interfaces.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle {

	public List<Person> passengers  = Collections.synchronizedList(new ArrayList<Person>());
	
}
