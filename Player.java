import java.util.*;


public class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);


        int shipX = 0;
        int shipY = 0;

        // en filas par
        int[][] even_oddr_directions = {{+1,  0}, { 0, -1}, {-1, -1}, {-1,  0}, {-1, +1}, { 0, +1}};

        // en filas impar
        int[][] odd_oddr_directions = {{+1,  0}, {+1, -1}, { 0, -1}, {-1,  0}, { 0, +1}, {+1, +1}};

        // Temperatura inicial
        double temp = 100000;

        //  Tasa de enfriamiento
        double coolingRate = 0.003;

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

            // dirigimos el barco a un vecino que este mas cerca al primer barrel del schedule

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
