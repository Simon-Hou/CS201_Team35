package restaurant.restaurantParker;

/*
* Figure 2-27
*/
 
import java.util.Vector;
 
public class RevolvingStand<T> extends Object {
	private int count = 0;
	private Vector<T> theData;
	
	
	public RevolvingStand(){
		theData = new Vector<T>();
	}
	
	synchronized public void insert(T data) {
		//Don't think we need this, since it's assumed the order stand should never fill up?
		//while (count == N) {
		//	try{
		//		System.out.println("\tFull, waiting");
		//		wait(5000); // Full, wait to add
		//	} catch (InterruptedException ex) {};
		//}
		insert_item(data);
		count++;
		if(count == 1) {
			//System.out.println("\tNot Empty, notify");
			//notify(); // Not empty, notify a
			// waiting consumer
		}
	}
	synchronized public T remove() {
		T data;
		//while(count == 0)
			//try{
			//	System.out.println("\tEmpty, waiting");
			//	wait(5000); // Empty, wait to consume
			//} catch (InterruptedException ex) {};
		
		if (count==0){
			System.out.println("nothing for consumer to remove");
			return null;
		}

		data = remove_item();
		count--;
			
			//Probably also unneccessary, because we don't need the stand to be full either
			//if(count == N-1){
			//	System.out.println("\tNot full, notify");
			//	notify(); // Not full, notify a waiting producer
			//}
		return data;
	}
	private void insert_item(T data){
		theData.addElement(data);
	}
	private T remove_item(){
		T data = (T) theData.firstElement();
		theData.removeElementAt(0);
		return data;
	}

	public boolean isEmpty(){
		return count==0;
	}
}