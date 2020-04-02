import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/

public static class Barco {
    int entityId;
    String entityType;
    int x;
    int y;
    int arg1;// Orientacion de rotacion
    int arg2;// Velocidad barco
    int arg3;// Stock de ron barco
    int arg4;// 1 si controlo barco, 0 si no

    public Barco(final int id, final String tipo, final int x, final int y, final int arg1, final int arg2,
            final int arg3, final int arg4) {
        this.entityId = id;
        this.entityType = tipo;
        this.x = x;
        this.y = y;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.arg4 = arg4;
    }

}

public static class Barril {
    int entityId;
    String entityType;
    int x;
    int y;
    int arg1;// Cantidad de ron del barril

    public Barril(final int id, String tipo;int x, int y, int arg1) {
        this.entityId = id;
        this.entityType=tipo;
        this.x = x; //r
        this.y = y; //q
        this.arg1 = arg1;
    }
}

    class Player {
        ArrayList<Barril> barriles = new ArrayList<Barril>();
        ArrayList<Barco> barcos = new ArrayList<Barco>();

    public Player(barcoss,barriless){
        this.barriles.addAll(barriless);
        this.barcos.addAll(barcoss);
    }

        public double obtenerDistancia(final Barco barco, final Barril barril) {
            // La distancia entre un vector A(x1,y1) y B(x2,y2) es
            // AB=raiz((x2-x1)^2+(y2-y1)^2)
            final double dist = Math
                    .sqrt((otroBarril.getPosX() - this.getPosX()) ^ 2 + (otroBarril.getPosY() - this.getPosY()) ^ 2);
            return dist;

        }

    public double obtenerElBarrilCercano(Barco barco){ 
        double minDistancia= 10000;
        double dist;
        double mejorBarril;
        final int longBarriles=this.barriles.size();
        for (int barril = 0; barril < longBarriles; barril++) { // recorremos el array de barriles
            dist= this.obtenerDistancia(barco,barriles.get(barril));
            if(dist<minDistancia){
                minDistancia=dist;
                mejorBarril=barril;
            }
        }
        return mejorBarril;
    }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        // System.out.println("MOVE 11 10"); // Any valid action, such as "WAIT" or
        // "MOVE x y"

        barcos[i].moverBarco(barriles[0].getPosX(),barriles[0].getPosY());

    }

    // La distancia entre un vector A(x1,y1) y B(x2,y2) es
    // AB=raiz((x2-x1)^2+(y2-y1)^2)
    double dist= Math.sqrt(( otroBarril.getPosX()- this.getPosX()) ^ 2 + (otroBarril.getPosY() - this.getPosY()) ^ 2);return dist;

    }

    public static void main(final String args[]) {
        final Scanner in = new Scanner(System.in);
        final ArrayList <Barril> barriles;
        final ArrayList <Barco> barcos;

        // game loop
        while (true) {
            final int myShipCount = in.nextInt(); // cantidad de barcos que tengo controlados
           System.err.println("myShipCount"+myShipCount);
            final int entityCount = in.nextInt(); // cantidad de objetos en total 22
           System.err.println("entityCount"+entityCount);
          
            for (int i = 0; i < entityCount; i++) {
                final int entityId = in.nextInt();
                System.err.println("entityId"+entityId);
         
                final String entityType = in.next();
                  System.err.println("entityType"+entityType); // (e.g. ships, mines or cannonballs)
         
                final int x = in.nextInt();
                final int y = in.nextInt();
                final int arg1 = in.nextInt();
                final int arg2 = in.nextInt();
                final int arg3 = in.nextInt();
                final int arg4 = in.nextInt();
                if (entityType.equals("SHIP")) {
                    Barco b= new Barco(entityId,entityType x, y, arg1, arg2, arg3, arg4);
                    barcos.add();
                   
                }
            for (int i = 0; i < myShipCount; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                System.out.println("MOVE 11 10"); // Any valid action, such as "WAIT" or "MOVE x y"
            }
        }
    }
}

/*   public Barril(int id, int x, int y) {
            this.entityId = id;
            this.x = x;
            this.y = y;
        }

        public int getPosX() {
            return this.x;
        }

        public int getPosY() {
            return this.y;
        }

        public int getEntityID() {
            return this.entityId;
        }

       
    }

    public static class Mar {
    private ArrayList <Barril> barriles = new ArrayList<Barril>();
    public Mar (ArrayList <Barril> barriles){
        this.barriles.addAll(barriles);
        Collections.shuffle(this.barriles); //Desordena los barriles
    }
    public ArrayList<Barril> getBarriles() {
        return this.barriles;
    }
    public double getTotalDistance(){
        int longBarriles=this.barriles.size();
   //sumo las distancias de todos los barriles
        return this.barriles.stream().mapToDouble(x->
        {
            int indiceBarril = this.barriles.indexOf(x);
            double resultado=0;
            if(indiceBarril < longBarriles -1)
            resultado=  x.distancia(this.barriles.get(indiceBarril+1));
            return resultado;
          }).sum() + this.barriles.get(longBarriles-1).distancia(this.barriles.get(0));
    }
    }

    public static class HillClimbing{
        public Mar distanciaMasCorta(Mar mar){
           Mar m;
           int indice =0;
           while( indice <100){
               m=distanciasAbyacentes(new Mar(mActual));
               if(m.getTotalDistance()<=mActual.getTotalDistance()){
                   indice=0;
                   mActual= new Mar(m);
               }else
               indice++;
               }
           }
        }
        private Mar distanciasAbyacentes(Mar mar){
            int x1=0;
            int x2=0;
            while (x1==x2){
                x1=(int) (mar.getBarriles().size()*Math.random());
                x2=(int) (mar.getBarriles().size()*Math.random());
            }
            Barril b1= mar.getBarriles().get(x1);
            Barril b2= mar.getBarriles().get(x2);
           return mar;
        }
    }
}