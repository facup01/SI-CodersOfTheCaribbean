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
        int movX = 0;
        int movY = 0;
        
        int xNeighbour;
        int yNeighbour;  
        
        // en filas par
        int[][] even_oddr_directions = {{+1,  0}, { 0, -1}, {-1, -1}, {-1,  0}, {-1, +1}, { 0, +1}};
        
        // en filas impar
        int[][] odd_oddr_directions = {{+1,  0}, {+1, -1}, { 0, -1}, {-1,  0}, { 0, +1}, {+1, +1}};
        
        
        //SIMULATED-ANNEALING
        
        // Temperatura inicial
        double temp = 100000;

        //  Tasa de enfriamiento
        double coolingRate = 0.003;



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
            
            
            for (int i = 0; i < myShipCount; i++) {
                
                
                //distancia entre el barco y un barril random
                double tempDistCurrent = Utility.distance(shipX,shipY, Manager.getBarrel(Utility.randomInt(0, Manager.numberOfBarrels())));
            
                // creo una solucion entre la posicion actual a un barril random, esto despues se va mejorando a medida que se enfria la temperatura
                Solution currentSolution = new Solution(shipX, shipY, tempDistCurrent);
                
                
                // %%%%%%%%%%%%%%%%%%%% START SIMULATED ANNEALING %%%%%%%%%%%%%%%%%%%% 
                
                
                // Loop until system has cooled
                while (temp > 1) {
                    
                    
                    //arreglar aca!! ver si estoy en una fila par o impar para elegir el posVecino adecuado
                    //selecciono un vecino
                    //int posVecino = Utility.randomInt(0 , even_oddr_directions.length());
                    int posVecino = Utility.randomInt(0 , 6);
                    
                    
                    
                    if(shipX % 2 == 0 ) {
                        xNeighbour = even_oddr_directions[posVecino][0];
                        yNeighbour = even_oddr_directions[posVecino][1];   
                    } else {
                        xNeighbour = odd_oddr_directions[posVecino][0];
                        yNeighbour = odd_oddr_directions[posVecino][1];
                    }
                    
                    
                    double tempDistNeighbour = Utility.distance(shipX+xNeighbour,shipY+yNeighbour, Manager.getBarrel(Utility.randomInt(0, Manager.numberOfBarrels())));
                    Solution newSolution = new Solution(xNeighbour, yNeighbour, tempDistNeighbour);
                    
                    
                    
                    double currentDistance   = currentSolution.getDistancia();
                    double neighbourDistance = newSolution.getDistancia();
        
                    // Decide if we should accept the neighbour
                    double rand = Utility.randomDouble();
                    if (Utility.acceptanceProbability(currentDistance, neighbourDistance, temp) > rand) {
                        currentSolution = newSolution;
                    }

                    
                    // Cool system
                    temp *= 1 - coolingRate;
                }
                
                
                    
                    movX = currentSolution.getX();
                    movY = currentSolution.getY();
                    
                    System.out.println("MOVE " + movX + " " + movY);
                    
                    
                    Manager.clearManager();
                    
            }
                    

        }
    }
}

// %%%%%%%%%%%%%%%%%%%  BARREL  %%%%%%%%%%%%%%%%%%%

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
		return x;
	}
	
	public int getY() {
		return y;
	}
    
}



// %%%%%%%%%%%%%%%%%%%  MANAGER %%%%%%%%%%%%%%%%%%%

class Manager {

    // Holds our barrels
    private static ArrayList<Barrel> destinationBarrels = new ArrayList<Barrel>();

    /**
	 * Adds a destination barrel
	 * @param city
	 */
	public static void addBarrel(Barrel barrel) {
		destinationBarrels.add(barrel);
	}

	/**
	 * returns a barrel given its index
	 * @param index
	 * @return barrel the barrel at index 
	 */
	public static Barrel getBarrel(int index){
		return (Barrel)destinationBarrels.get(index);
	}

	/**
	 * Returns the number of destination barrels 
	 * @return size the number of destination barrels
	 */
	public static int numberOfBarrels(){
		return destinationBarrels.size();
	}
	
	public static void clearManager(){
	    destinationBarrels.clear();
	}
    
}


// %%%%%%%%%%%%%%%%%%%  Solutions %%%%%%%%%%%%%%%%%%%



class Solution {
    private int x;
    private int y;
    private double distancia;
    
    public Solution(int x, int y, double distancia){
        this.x = x;
        this.y = y;
        this.distancia = distancia;
    }
    
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    
    public double getDistancia(){
        return this.distancia;
    }
    
    
}



// %%%%%%%%%%%%%%%%%%%  Utility %%%%%%%%%%%%%%%%%%%


class Utility {


	/**
	 * Computes and returns the Euclidean distance between position and barrel
	 * @param coordenada x
	 * @param coordenada y
	 * @param barrel
	 * @return distance the dist between position and barrel
	 */
	public static double distance(int x, int y, Barrel barrel){
		int xDistance = Math.abs(x - barrel.getX());
		int yDistance = Math.abs(y - barrel.getY());
		double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

		return distance;
	}
		
	/**
	 * Calculates the acceptance probability
	 * @param currentDistance the total distance of the current tour
	 * @param newDistance the total distance of the new tour
	 * @param temperature the current temperature
	 * @return value the probability of whether to accept the new tour
	 */
	public static double acceptanceProbability(double currentDistance, double newDistance, double temperature) {
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






