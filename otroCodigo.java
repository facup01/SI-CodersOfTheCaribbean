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
        List<Integer> barrelX = new ArrayList<Integer>();
        List<Integer> barrelY = new ArrayList<Integer>();
        double posP = 0;
        double minT = 0;
        int movX = 0;
        int movY = 0;



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
                            
                
                    barrelX.add(x);
                    barrelY.add(y);
                            
                }
                
                
                
            }
            for (int i = 0; i < myShipCount; i++) {

                // Write an action using System.out.println()
                minT = Math.sqrt((shipX - barrelX.get(0)) ^ 2 + (shipY - barrelY.get(0)) ^ 2);
                
                 
                for (int k = 1 ; k< barrelX.size(); k++) {
                    
                    
                    posP = Math.sqrt((shipX - barrelX.get(k)) ^ 2 + (shipY - barrelY.get(k)) ^ 2);
                    
                    if ( posP < minT ) {
                        
                        movX = barrelX.get(k);
                        movY = barrelY.get(k);
                        
                    }
                
                }
                
                
                System.out.println("MOVE " + movX + " " + movY);
                    

                
            }
                
           // To debug: System.err.println("Debug messages...");

           // Any valid action, such as "WAIT" or "MOVE x y"
            
            
            barrelX.clear();
            barrelY.clear();
        }
    }
}