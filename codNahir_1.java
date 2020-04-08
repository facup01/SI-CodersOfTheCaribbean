import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {
   

    public static double obtenerDistancia(Barco barco, Barril barril) {
            double dist= (Math.abs(barco.getPosY() - barril.getPosY()) + Math.abs(barco.getPosY() + barco.getPosX() - barril.getPosY()- barril.getPosX()) + Math.abs(barco.getPosX() - barril.getPosX())) / 2;
            //double cantRon= barco.obtenerRon();
          //  double heuristica=dist-cantRon;
    
      //  System.err.println("Distancia: "+dist);
       
        return dist;

    }

    public static Barril obtenerElBarrilCercano(Barco barco, ArrayList<Barril> barriles) {
        /*Hill Climbing:
    Actual<- Barco, lista de barriles
    Hasta recorrer todos los barriles
    Eligo el que tenga menor distancia
    Fin
    */
        double minDistancia = 10000;
        double dist;
        Barril mejorBarril=new Barril();
        int longBarriles = barriles.size();
        System.err.println("LongBarriles: "+longBarriles);
        for (int barril = 0; barril < longBarriles; barril++) { // recorremos el array de barriles
            dist = obtenerDistancia(barco, barriles.get(barril));
       //     System.err.println("Distancia: "+dist + "MenorDistancia: "+ minDistancia);
       //Tengo que agarrar el barril que menor ron tenga para dejar los que mayor tengan para dsp
            if (dist < minDistancia) {
                minDistancia = dist;
                mejorBarril = barriles.get(barril);
               // System.err.println("Mejor Barril");
                
            }
            
        }
        mejorBarril.mostrarBarril();
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
                    
                    b.mostrarBarco();
                    barcos.add(b);
                }
                if (entityType.equals("BARREL")) {
                    Barril ba = new Barril(entityId, entityType, x, y, arg1);
                    ba.mostrarBarril();
                    barriles.add(ba);
                }

            }
        
            for (int i = 0; i < myShipCount; i++) {
                System.err.println("myShipCount"+myShipCount+" i:"+i); 
                Barril mejorBarril = obtenerElBarrilCercano(barcos.get(i),barriles);
                int x= mejorBarril.getPosX();
                int y= mejorBarril.getPosY();
                
                barriles.remove(mejorBarril);
                System.err.println("Se mueve "+ x + " " + y);
               
                System.out.println("MOVE " + x + " " + y);
            }
        }
    }

    public static class Mapa {
        int ejeX;
        int ejeY;
        int ejeZ;

        public Mapa(){
            this.ejeX=0;
            this.ejeY=0;
            this.ejeZ=0;
        }
        
        public void axial_to_cube(q,r){
            this.ejeX=q;
            this.ejeZ=r;
            this.ejeY=- this.ejeX - this.ejeZ;

        }
    }

    public static class Barco {
        int entityId;
        String entityType;
        int x;
        int y;
        int arg1;// Orientacion de rotacion entre 0 and 5
        int arg2;// Velocidad barco entre 0 y 2
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
        
        public void mostrarBarco (){
            System.err.println("Barco " + this.entityId +" X:"+this.x+" Y:"+this.y+" Arg1:"+
            this.arg1+" Arg2:"+this.arg2+" Arg3:"+this.arg3+" Arg4:"+this.arg4);
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
        public int obtenerRon() {
            return this.arg1;
        }
        
        
        public void mostrarBarril (){
            System.err.println("Barril " + this.entityId +" X:"+this.x+" Y:"+this.y+" Arg1:"+
            this.arg1);
            }

    }

    public static class BalaCañon {
  
        int entityId;
        String entityType; //CANNONBALL
        int x;
        int y;
        int arg1;// el ID de entidad de la nave que disparó esta bola de cañón
        int arg2;//el número de vueltas antes del impacto (1 significa que la bala de cañón aterrizará al final del turno actual)
      
        public BalaCañon(int id, String tipo, int x, int y, int arg1, int arg2) {
            this.entityId = id;
            this.entityType = tipo;
            this.x = x;
            this.y = y;
            this.arg1 = arg1;
            this.arg2 = arg2;
          
        }

        public int getPosX() {
            return this.x;
        }

        public int getPosY() {
            return this.y;
        }
        
        public void mostrarBalaCañon (){
            System.err.println("BalaCañon " + this.entityId +" X:"+this.x+" Y:"+this.y+" Arg1:"+
            this.arg1+" Arg2:"+this.arg2);
    }
            
    public static class Mina {
  
        String entityType; //MINE
       
        public Mina(String tipo) {
            this.entityType = tipo;
          
        }

    }