import java.util.Random;

public class Utility {

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
