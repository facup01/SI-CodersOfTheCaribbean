# SI-CodersOfTheCaribbean

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);


        int shipX = 0;
        int shipY = 0;

        // en filas par
        int[][] even_oddr_directions = {{+1,  0}, { 0, -1}, {-1, -1}, {-1,  0}, {-1, +1}, { 0, +1}};

        // en filas impar
        int[][] odd_oddr_directions = {{+1,  0}, {+1, -1}, { 0, -1}, {-1,  0}, { 0, +1}, {+1, +1}};


        //SIMULATED-ANNEALING

        // Temperatura inicial
        double temp = 1;

        //  Tasa de enfriamiento
        //double coolingRate = 0.003;
        double coolingRate = 0.03;

        int movX = 0;
        int movY = 0;

        int xNeighbour;
        int yNeighbour;
        double minDistancia;

        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)



            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();


                if(entityType.equals("SHIP")){
                    shipX = x;
                    shipY = y;
                }

                if( entityType.equals("BARREL") ) {

                    Manager.addBarrel(new Barrel(x, y, arg1));

                }
            }



            //create schedule with position current of ship, origin point schedule, emulated how Barrel
            Schedule possibleWay = new Schedule(new Barrel(shipX, shipY, 0));

            //Se genera un schedule de barriles a recorrer aleatoriamente
            possibleWay.generateIndividual();

            // We would like to keep track if the best solution
            // Assume best solution is the current solution
            Schedule best = new Schedule(possibleWay.getSchedule());

            // Loop until system has cooled
            while (temp > 0) {
                // Create new neighbour schedule
                Schedule newSolution = new Schedule(possibleWay.getSchedule());

                // Get random positions in the schedule
                // First element of schedule always is the barrel position
                int barrelPos1 = Utility.randomInt(1 , newSolution.scheduleSize());
                int barrelPos2 = Utility.randomInt(1 , newSolution.scheduleSize());

                //to make sure that stepPos1 and stepPos2 are different
                while(barrelPos1 == barrelPos2) {barrelPos2 = Utility.randomInt(1 , newSolution.scheduleSize());}

                // Get the barrels at selected positions in the schedule
                Barrel barrelSwap1 = newSolution.getBarrel(barrelPos1);
                Barrel barrelSwap2 = newSolution.getBarrel(barrelPos2);

                // Swap them
                newSolution.setBarrel(barrelPos2, barrelSwap1);
                newSolution.setBarrel(barrelPos1, barrelSwap2);

                // Get energy of solutions
                int currentDistance   = possibleWay.getTotalDistance();
                int neighbourDistance = newSolution.getTotalDistance();



                // Decide if we should accept the neighbour
                double rand = Utility.randomDouble();
                if (Utility.acceptanceProbability(currentDistance, neighbourDistance, temp) > rand) {
                    possibleWay = new Schedule(newSolution.getSchedule());
                }

                // Keep track of the best solution found
                if (possibleWay.getTotalDistance() < best.getTotalDistance()) {
                    best = new Schedule(possibleWay.getSchedule());
                }

                // Cool system
                temp *= 1 - coolingRate;
            }



            minDistancia = Utility.distance(shipX, shipY, best.getBarrel(1));

            if(shipX % 2 == 0 ) {
                for ( int w = 0 ; w < 6 ; w++ ) {
                    xNeighbour = shipX + even_oddr_directions[0][0];
                    yNeighbour = shipY + even_oddr_directions[0][1];
                    if( minDistancia > Utility.distance(xNeighbour, yNeighbour, best.getBarrel(1))) {
                        movX = xNeighbour;
                        movY = yNeighbour;
                    }
                }
            } else {
                for ( int w = 0 ; w < 6 ; w++ ) {
                    xNeighbour = odd_oddr_directions[0][0];
                    yNeighbour = odd_oddr_directions[0][1];
                     if( minDistancia > Utility.distance(xNeighbour, yNeighbour, best.getBarrel(1))) {
                        movX = xNeighbour;
                        movY = yNeighbour;
                     }
            }



            System.out.println("MOVE " + movX + " " + movY);


            Manager.clearManager();

        }


        }
    }


}


//  ################### SCHEDULE ###################
//  ################### SCHEDULE ###################
//  ################### SCHEDULE ###################

class Schedule{

    //to hold a schedule of posible way
    private ArrayList<Barrel> schedule = new ArrayList<Barrel>();
    
    //we assume initial value of distance is 0 
    private int distance = 0;

    
    //Constructor
    //starts an empty schedule
    public Schedule(Barrel ship){
        for (int i = 0; i < Manager.numberOfBarrels() + 1; i++) {
            schedule.add(null);
        }
        setBarrel(0, ship);
    }
    
    //another Constructor
    //starts a schedule from another schedule
    @SuppressWarnings("unchecked")
    // schedule is a array of steps in the way
	public Schedule(ArrayList<Barrel> schedule){
        this.schedule = (ArrayList<Barrel>) schedule.clone();
    }
    
    /**
      Returns schedule information
      @return current Schedule
     */
    public ArrayList<Barrel> getSchedule(){
        return schedule;
    }
     
    /**
     * Creates a random schedule (i.e. individual or candidate solution)
     */
    public void generateIndividual() {
        // Loop through all our destination barrels and add them to our schedule
        for (int barrelIndex = 0; barrelIndex < Manager.numberOfBarrels(); barrelIndex++) {
            setBarrel(barrelIndex + 1, Manager.getBarrel(barrelIndex));
        }

        // Randomly reorder the tour
        Collections.shuffle(schedule);
    }
    /**
     * Returns a barrel from the schedule given the barrel's index
     * @param index
     * @return Barrel at that index
     */
    public Barrel getBarrel(int index) {
        return schedule.get(index);
    }

    /**
     * Sets a barrel in a certain position within a schedule
     * @param index
     * @param barrel
     */
    public void setBarrel(int index, Barrel barrel) {
        schedule.set(index, barrel);
        // If the tour has been altered we need to reset the fitness and distance
        distance = 0;
    }
    
    /**
     * Computes and returns total distance of the schedule
     * @return distance totals of the schedule
     */
    public int getTotalDistance(){
    	if (distance == 0) {
            int scheduleDistance = 0;
            // Loop through our tour's cities
            for (int barrelIndex=0; barrelIndex < scheduleSize(); barrelIndex++) {
                // Get city we're traveling from
                Barrel fromBarrel = getBarrel(barrelIndex);
                // City we're traveling to
                Barrel destinationBarrel;
                // Check we're not on our tour's last city, if we are set our
                // tour's final destination city to our starting city
                if(barrelIndex+1 < scheduleSize()){
                    destinationBarrel = getBarrel(barrelIndex+1);
                }
                else{
                    destinationBarrel = getBarrel(0);
                }                
                // Get the distance between the two cities
                scheduleDistance += Utility.distance(fromBarrel.getX(), fromBarrel.getY(), destinationBarrel); 
            }
            distance = scheduleDistance;
        }
        return distance;
    }

    /**
     * Get number of barrels on our tour
     * @return number how many barrels there are in the schedule!
     */
    public int scheduleSize() {
        return schedule.size();
    }
    

}



//  ################### MANAGER ###################
//  ################### MANAGER ###################
//  ################### MANAGER ###################


class Manager {

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



//  ################### BARREL ###################
//  ################### BARREL ###################
//  ################### BARREL ###################

class Barrel {
	private int x;
	private int y;
	private int rum;

	//Constructor
	//creates a barrel given its (x,y) location
	public Barrel(int x, int y, int rum){
		this.x = x;
		this.y = y;
		this.rum = rum;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getRum(){
		return this.rum;
	}

}



//  ################### UTILITY ###################
//  ################### UTILITY ###################
//  ################### UTILITY ###################

class Utility {


	/**
	 * Computes and returns the Euclidean distance between ship and barrel
	 * @param x of ship
	 * @param y of ship
	 * @return distance the dist between ship and barrel
	 */
	public static double distance(int x, int y, Barrel barrel){
		int xDistance = Math.abs(x - barrel.getX());
		int yDistance = Math.abs(y - barrel.getY());
		double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

		return distance;
	}
	
	/**
	 * Calculates the acceptance probability
	 * @param currentDistance the total distance of the current schedule
	 * @param newDistance the total distance of the new schedule
	 * @param temperature the current temperature
	 * @return value the probability of whether to accept the new schedule
	 */
	public static double acceptanceProbability(int currentDistance, int newDistance, double temperature) {
		// If the new solution is better, accept it
		if (newDistance < currentDistance) {
			return 1.0;
		}
		// If the new solution is worse, calculate an acceptance probability
		return Math.exp((currentDistance - newDistance) / temperature);
	}

	/**
	 * this method returns a random number n such that
	 * 0.0 <= n <= 1.0
	 * @return random such that 0.0 <= random <= 1.0
	 */
	static double randomDouble()
	{
		Random r = new Random();
		return r.nextInt(1000) / 1000.0;
	}
	
	/**
	 * returns a random int value within a given range
	 * min inclusive .. max not inclusive
	 * @param min the minimum value of the required range (int)
	 * @param max the maximum value of the required range (int)
	 * @return rand a random int value between min and max [min,max)
	 */ 
	public static int randomInt(int min , int max) {
		Random r = new Random();
		double d = min + r.nextDouble() * (max - min);
		return (int)d;
	}
}

