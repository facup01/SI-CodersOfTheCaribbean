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
        Barco[] barcos;
        Barril[] barriles; 

        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            barcos=new Barco[myShipCount];
            barriles=new Barril[entityCount];
            
            int indiceBarco=0;
            int indiceBarril=0;
           
            
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();
                

                if(entityType.equals("SHIP") && indiceBarco<barcos.length){
                  //  System.err.println(entityId +" "+ x+" "+y+" "+arg1+" "+arg2+" "+arg3+" "+arg4);
                    barcos[indiceBarco]=new Barco(entityId, x,y,arg1,arg2,arg3,arg4);
                    indiceBarco++;
                }
                
                if(entityType.equals("BARREL")){
                    
                    if(indiceBarril<barriles.length){
                        barriles[indiceBarril]=new Barril(entityId, x,y,arg1,arg2,arg3,arg4);
                        indiceBarril++;
                        }
                    
                    if(barcos[0]!=null){
                        
                        barcos[0].moverBarco(x,y);
                        }
                }
                    
            }
            
            for (int i = 0; i < myShipCount; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
              
             //   System.out.println("MOVE 11 10"); // Any valid action, such as "WAIT" or "MOVE x y"
                
                barcos[i].moverBarco(barriles[0].getPosX(),barriles[0].getPosY());
                
                
            }
        }
    }
    
    public static class Barco{
        int entityId;
        int x;
        int y;
        int arg1;//Orientacion de rotacion
        int arg2;//Velocidad barco
        int arg3;//Stock de ron barco
        int arg4;//1 si controlo barco, 0 si no
        
        public Barco(int id,int  x,int  y,int  arg1,int  arg2,int  arg3,int  arg4){
            this.entityId=id;
            this.x=x;
            this.y=y;
            this.arg1=arg1;
            this.arg2=arg2;
            this.arg3=arg3;
            this.arg4=arg4;
            }
            
        public void moverBarco(int xNuevo,int yNuevo){
            System.out.println("MOVE "+xNuevo+" "+yNuevo);
            }
        
        }
                
        
    public static class Barril{
        int entityId;
        int x;
        int y;
        int arg1;//Orientacion de rotacion
        int arg2;//Velocidad barco
        int arg3;//Stock de ron barco
        int arg4;//1 si controlo barco, 0 si no
        
        public Barril(int id,int  x,int  y,int  arg1,int  arg2,int  arg3,int  arg4){
            this.entityId=id;
            this.x=x;
            this.y=y;
            this.arg1=arg1;
            this.arg2=arg2;
            this.arg3=arg3;
            this.arg4=arg4;
            }
            
        public int getPosX(){
            return this.x;
            }
            
        public int getPosY(){
            return this.y;
            }
       
        
        }
}