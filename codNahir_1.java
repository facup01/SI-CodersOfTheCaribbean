import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {
    public static double obtenerDistancia(Barco barco, Barril barril) {
        // La distancia entre un vector A(x1,y1) y B(x2,y2) es
        // AB=raiz((x2-x1)^2+(y2-y1)^2)
        final double dist = Math
                .sqrt((barril.getPosX() - barco.getPosX()) ^ 2 + (barril.getPosY() - barco.getPosY()) ^ 2);
        return dist;

    }

    public static Barril obtenerElBarrilCercano(Barco barco, ArrayList<Barril> barriles) {
        double minDistancia = 10000;
        double dist;
        Barril mejorBarril=new Barril();
        int longBarriles = barriles.size();
        for (int barril = 0; barril < longBarriles; barril++) { // recorremos el array de barriles
            dist = obtenerDistancia(barco, barriles.get(barril));
            if (dist < minDistancia) {
                minDistancia = dist;
                mejorBarril = barriles.get(barril);
            }
        }
        return mejorBarril;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        ArrayList<Barril> barriles = new ArrayList<Barril>();
        ArrayList<Barco> barcos = new ArrayList<Barco>();

        while (true) {
            int myShipCount = in.nextInt();// cantidad de barcos que tengo controlados
            int entityCount = in.nextInt(); // cantidad de objetos en total 22

            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next(); // (e.g. ships, mines or cannonballs)
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();
                if (entityType.equals("SHIP")) {
                    Barco b = new Barco(entityId, entityType, x, y, arg1, arg2, arg3, arg4);
                    barcos.add(b);
                }
                if (entityType.equals("BARREL")) {
                    Barril ba = new Barril(entityId, entityType, x, y, arg1);
                    barriles.add(ba);
                }

            }
        
            for (int i = 0; i < myShipCount; i++) {
                Barril mejorBarril = obtenerElBarrilCercano(barcos.get(i),barriles);
                int x= mejorBarril.getPosX();
                int y= mejorBarril.getPosY();
               
                System.out.println("MOVE " + x + " " + y);
            }
        }
    }

    public static class Barco {
        int entityId;
        String entityType;
        int x;
        int y;
        int arg1;// Orientacion de rotacion
        int arg2;// Velocidad barco
        int arg3;// Stock de ron barco
        int arg4;// 1 si controlo barco, 0 si no

        public Barco(int id, String tipo, int x, int y, int arg1, int arg2, int arg3, int arg4) {
            this.entityId = id;
            this.entityType = tipo;
            this.x = x;
            this.y = y;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
        }

        public int getPosX() {
            return this.x;
        }

        public int getPosY() {
            return this.y;
        }

    }

    public static class Barril {
        int entityId;
        String entityType;
        int x;
        int y;
        int arg1;// Cantidad de ron del barril

        
        public Barril() {
            this.entityId = 0;
            this.entityType = "";
            this.x =0;
            this.y =0;
            this.arg1 = 0;
        }
        public Barril(int id, String tipo, int x, int y, int arg1) {
            this.entityId = id;
            this.entityType = tipo;
            this.x = x; 
            this.y = y; 
            this.arg1 = arg1;
        }

        public int getPosX() {
            return this.x;
        }

        public int getPosY() {
            return this.y;
        }

        

    }
}