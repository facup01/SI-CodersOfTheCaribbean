import java.util.*;

/*
* Manager.java
* Holds and keeps track of the barrels of a grid
*/


public class Manager {

	// Holds our barrels
	private static ArrayList<Barrel> barrelsAvailable = new ArrayList<Barrel>();

	/**
	 * Adds a Barrel Available
	 * @param barrel
	 */
	public static void addBarrel(Barrel barrel) {
		barrelsAvailable.add(barrel);
	}

	/**
	 * returns a barrel given its index
	 * @param index
	 * @return Barrel the barrel at index
	 */
	public static Barrel getBarrel(int index){
		return (Barrel)barrelsAvailable.get(index);
	}

	/**
	 * Returns the number of barrels availables
	 * @return size the number of barrels availables
	 */
	public static int numberOfBarrels(){
		return barrelsAvailable.size();
	}

	/**
	 * Clear a Schedule
	 */
	public static void clearManager(){
		barrelsAvailable.clear();
	}
    
}
