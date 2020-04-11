import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/

public static class Cubo {
    int x;
    int y;
    int z;

    public Cubo(){
        this.x=0;
        this.y=0;
        this.z=0;
    }

    public Cubo(int x,int y, int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public int getPosX() {
        return this.x;
    }

    public int getPosY() {
        return this.y;
    }
    public int getPosZ() {
        return this.z;
    }

    
}
public static class ODDR {
    int x;
    int y;

    public ODDR(){
        this.x=0;
        this.y=0;
    }

    public ODDR(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getPosX() {
        return this.x;
    }

    public int getPosY() {
        return this.y;
    }

    
}

class Player {
   
    /*Como la grilla es offset odd-r, la convertimos a cubo.
    function oddr_to_cube(hex):
        var x = hex.col - (hex.row - (hex.row&1)) / 2
        var z = hex.row
        var y = -x-z
        return Cube(x, y, z)

    function cube_to_oddr(cube):
        var col = cube.x + (cube.z - (cube.z&1)) / 2
        var row = cube.z
        return OffsetCoord(col, row)

    */
    public static Cubo oddr_to_cube(int fila, int col){
        int ejeX= col - (fila-(fila&1))/2;
        int ejeZ=fila;
        int ejeY=- this.ejeX - this.ejeZ;
        Cubo m= new Cubo(ejeX,ejeY,ejeZ);
        return m;
    }

    public static ODDR cube_to_oddr(int x, int y;int z){
        int col= x+(z-(z&1))/2;
        int fila=z;
        ODDR oddr= new ODDR(col,fila);
        return oddr;
    }

     /*convertiremos las coordenadas de desplazamiento en coordenadas de cubo, luego usaremos la distancia del cubo.
    function offset_distance(a, b):
       var ac = offset_to_cube(a)
        var bc = offset_to_cube(b)
        return cube_distance(ac, bc)
    */
    public static double offset_distance(int x, int y, int x1, int y1) {
        Cubo ac= oddr_to_cube(x,y);
        Cubo bc= oddr_to_cube(x1,y1);
        return this.obtenerDistancia(ac,bc);
    }

    /*function cube_distance(a, b):
        return (abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)) / 2   
    */
    public static double obtenerDistancia(Cubo a, Cubo b) {
        double dist= (Math.abs(a.getPosX() - b.getPosX()) + 
                      Math.abs(a.getPosY() - b.getPosY()) +
                      Math.abs(a.getPosZ() - b.getPosZ())) / 2;
            System.err.println("Distancia: "+dist);
        return dist;

    }


    public static Barril obtenerElBarrilCercano(Barco miBarco, ArrayList<Barril> barriles) {
        double minDistancia = 10000;
        double dist;
        Barril mejorBarril=new Barril();
        int longBarriles = barriles.size();
        for (int i = 0; i < longBarriles; i++) {
            Barril barril= barriles.get(i); // recorremos el array de barriles
            dist =  offset_distance(miBarco.getPosX(),miBarco.getPosY(), barril.getPosX(),barril.getPosY());
            System.err.println("Distancia: "+dist + "MenorDistancia: "+ minDistancia);

            if (dist < minDistancia) {
                minDistancia = dist;
                mejorBarril = barril;    
            }   
        }
        mejorBarril.mostrarBarril();
        return mejorBarril;
    }

    public static Barco obtenerElBarcoMasCercano(ArrayList<Barco> barcos, Barco miBarco) { //Método para disparar
        Barco mejorBarco=new Barco();
        int longBarcos = barcos.size();
        for (int i = 0; i < longBarcos; i++) { // recorremos el array de barcos
            Barco otroBarco = barcos.get(barco);
            if (otroBarco.getArg_4 == 0){ //Es el barco de mi oponente
                double dist= offset_distance(miBarco.getPosX(),miBarco.getPosY(), otroBarco.getPosX(),otroBarco.getPosY());
                if (dist<10){
                    mejorBarco=otroBarco;
                    mejorBarco.mostrarBarco();
                }

            }
        }
        return mejorBarco;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        ArrayList<Barril> barriles = new ArrayList<Barril>();
        ArrayList<Barco> barcos = new ArrayList<Barco>();
   //     ArrayList<Cubo> Cubos= new ArrayList<Cubo>();

        while (true) {
            int myShipCount = in.nextInt();// cantidad de barcos que tengo controlados
            int entityCount = in.nextInt(); // cantidad de objetos en total

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
                 //   Cubo m = oddr_to_cube(x, y); //Paso a cubo el barco (?)
                    b.mostrarBarco();
                    barcos.add(b);
                  //  Cubos.add(m);
                }
                if (entityType.equals("BARREL")) {
                    Barril ba = new Barril(entityId, entityType, x, y, arg1);
                    //Cubo m= oddr_to_cube(x, y);
                    ba.mostrarBarril();
                    barriles.add(ba);
                    //Cubos.add(m);
                }

            }
        
            
           
            for (int i = 0; i < myShipCount; i++) {
                System.err.println("myShipCount"+myShipCount+" i:"+i); 
                Barril mejorBarrilCubo = obtenerElBarrilCercano(barcos.get(i),barriles);
                Barco barcoMasCerca = obtenerElBarcoMasCercano(barcos, barcos.get(i));
             
                int barrilX= mejorBarril.getPosX();
                int barrilY= mejorBarril.getPosY();
                int barcoX= barcoMasCerca.getPosX();
                int barcoY= barcoMasCerca.getPosY();
                
                barriles.clear();
                System.err.println("Se mueve "+ barrilX + " " + barrilY);
               
                System.out.println("MOVE " + barrilX + " " + barrilY);
              
                System.err.println("Dispara "+ barcoX + " " + barcoY);
                
                System.out.println("FIRE " + barcoX + " " + barcoY);
            }
        }
    }


    public static class Barco {
        int entityId;
        String entityType;
        int x;
        int y;
        int cubox;
        int cuboy;
        int cuboz;
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
        public int getArg_4() {
            return this.arg4;
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