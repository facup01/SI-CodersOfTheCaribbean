/*
* Class Schedule.java
*/

import java.util.ArrayList;
import java.util.Collections;


public class Schedule{

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
    
