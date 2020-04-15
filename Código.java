import java.util.*;

import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {

	static Barco[] barcos;
	static ArrayList<Barril> listaBarriles;
	static ArrayList<Mina> listaMinas;
	static ArrayList<EstadoFuturo> listaEstadosFuturos;
	static ArrayList<Barco> barcosContrarios;
	
	static Cannonball unaBalaDisparada;
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		listaBarriles=new ArrayList<Barril>();
		listaMinas=new ArrayList<Mina>();
		listaEstadosFuturos=new ArrayList<EstadoFuturo>();
		barcosContrarios=new ArrayList<Barco>();
		int cantTurnos=0;
		
		while (true) {
			int myShipCount = in.nextInt();// cantidad de barcos que tengo controlados
			int entityCount = in.nextInt(); // cantidad de objetos en total
			int indiceBarco = 0;

			barcos = new Barco[myShipCount];

			for (int i = 0; i < entityCount; i++) {
				int entityId = in.nextInt();
				String entityType = in.next();
				int x = in.nextInt();
				int y = in.nextInt();
				int arg1 = in.nextInt();
				int arg2 = in.nextInt();
				int arg3 = in.nextInt();
				int arg4 = in.nextInt();

				if (entityType.equals("SHIP")) {
						if (arg4 == 1){ //son mis barcos
							barcos[indiceBarco] = new Barco(entityId,entityType, x, y, arg1, arg2, arg3, arg4);
							 System.err.println("Mi barco" + entityId +" X:"+x+" Y:"+y+" Arg1:"+ arg1+" Arg2:"+arg2+" Arg3:"+arg3+" Arg4:"+arg4);
						
						} else { // es el barco de mi oponente
							Barco oponente =  new Barco(entityId,entityType, x, y, arg1, arg2, arg3, arg4);
							barcosContrarios.add(oponente);
							oponente.mostrarBarco();
						}

					}
					

					
				
				if (entityType.equals("BARREL")) {
					Barril unBarril = new Barril(entityId,entityType, x, y, arg1);
					listaBarriles.add(unBarril);
					unBarril.mostrarBarril();
				}
				
				if (entityType.equals("MINE")) {
					Mina unaMina= new Mina(entityId,entityType,x,y);
					listaMinas.add(unaMina);
					unaMina.mostrarMina();
				}
				
				if (entityType.equals("CANNONBALL")) {
					unaBalaDisparada= new Cannonball(entityId,entityType, x, y, arg1, arg2);
				}
				
			} // Fin for entityCount

			for (int i = 0; i < myShipCount; i++) {
				int posXBarcoRow=barcos[i].getPosX();
				int posYBarcoCol=barcos[i].getPosY();
	
			        if(posXBarcoRow % 2 == 0 ) { // r par 0<x<22
			//  [[+1,  0], [ 0, -1], [-1, -1], [-1,  0], [-1, +1], [ 0, +1]],
  
                    if(posXBarcoRow+1<=22) {
                        listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow+1, posYBarcoCol));
                    }
                    if(posYBarcoCol-1>=0) {
                        listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow, posYBarcoCol-1));
                    }
                    if(posXBarcoRow-1>=0 && posYBarcoCol+1<=20) {
                       listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow-1, posYBarcoCol-1));
                    }
                    if(posXBarcoRow-1>=0) {
                        listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow-1, posYBarcoCol));
                    }
				
                    if(posXBarcoRow-1>=0 && posYBarcoCol+1<=20) {
                        listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow-1, posYBarcoCol+1));
					}
					if(posYBarcoCol+1<=20) {
                        listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow, posYBarcoCol+1));
                    }
									
            }else{
                //[[+1,  0], [+1, -1], [ 0, -1], [-1,  0], [ 0, +1], [+1, +1]],
                if(posXBarcoRow+1<=22) {
                    listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow+1, posYBarcoCol));
                }
                if(posXBarcoRow+1<=22 && posYBarcoCol-1>=0) {
                    listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow+1, posYBarcoCol-1));
                }
                if(posYBarcoCol-1>=0) {
                    listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow, posYBarcoCol-1));
                }
                if(posXBarcoRow-1>=0) {
                    listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow-1, posYBarcoCol));
                }
                if(posYBarcoCol+1<=20) {
                    listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow, posYBarcoCol+1));
                }
            
                if(posXBarcoRow+1<=22 && posYBarcoCol+1<=20) {
                    listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow+1, posYBarcoCol+1));
                }
            }
				
				
				EstadoFuturo mejorEstado=null;
				Iterator iterator= listaEstadosFuturos.iterator();
				double mejorHeuristica = 10000;
				
				for (int j = 0; j <listaEstadosFuturos.size(); j++) {
					EstadoFuturo unEstado= (EstadoFuturo) iterator.next(); // recorremos el array de estados
				
					float heuristica=unEstado.getHeuristica(listaBarriles, listaMinas, unaBalaDisparada); //calculamos la heuristica
					System.err.println("heuristica: "+heuristica + "mejorHeuristica: "+ mejorHeuristica);
		
					if (heuristica < mejorHeuristica) {
						mejorHeuristica = heuristica;
						mejorEstado=unEstado;
					}
					
				}
				int movimientoX=mejorEstado.getPosX();
				int movimientoY=mejorEstado.getPosY();
				listaEstadosFuturos.clear();
				if (mejorHeuristica==-10000 && cantTurnos==4){
			cantTurnos++;
//				 if(cantTurnos==4){

					System.err.println("Cant Turnos: "+cantTurnos);
		
					System.out.println("FIRE " + barcosContrarios.get(i).getPosX() + " " + barcosContrarios.get(i).getPosY());
					cantTurnos=0;
					barcos[i].moverBarco(movimientoX, movimientoY);
					
			//	 }
				}else{

					barcos[i].moverBarco(movimientoX, movimientoY);
				}
				cantTurnos++;

				
			} // Fin for barcos
			
			listaBarriles.clear();
			
			listaMinas.clear();
			barcosContrarios.clear();
			barcos=null;
		}
		

	}//Fin main

	
	public static Cubo oddr_to_cube(int fila, int col) {
		int ejeX = col - (fila - (fila & 1)) / 2;
		int ejeZ = fila;
		int ejeY = -ejeX - ejeZ;

		Cubo unCubo = new Cubo(ejeX, ejeY, ejeZ);

		return unCubo;
	}
	
	public static class EstadoFuturo {

		private int x, y;
		private float miHeuristica;

		public EstadoFuturo(int unX, int unY) {
			this.x = unX;
			this.y = unY;
			this.miHeuristica = 10000;
		}


		public int getPosX() {
			return this.x;
		}

		public int getPosY() {
			return this.y;
		}
		
		public void mostrarEstado (){
            System.err.println("Estado "+" X:"+this.x+" Y:"+this.y);
            }

		public float getHeuristica(ArrayList listaBarriles, ArrayList listaMinas, Cannonball unaBala) {

			this.miHeuristica = calcularMiHeuristica(listaBarriles, listaMinas,unaBala);
					
			return miHeuristica;
		}

		private float calcularMiHeuristica(ArrayList<Barril> listaBarriles, ArrayList<Mina> listaMinas, Cannonball unaBala) {
			/*Metodo que calcula la heuristica del futuro posible estado considerando:
			 * distancia entre el barril mas cercano sumado al que mas Ron tenga*/
			float resultadoFinal = 10000;
			boolean miPosTieneMina = false;
			boolean miPosDispara=false;
			// for (Iterator iterator = listaMinas.iterator(); iterator.hasNext();) {
			// 	Mina mina = (Mina) iterator.next();
			// 	int posMinaX= mina.getPosX();
			// 	int posMinaY=mina.getPosY();
				
			// 	Cubo cuboMina = oddr_to_cube(posMinaX, posMinaY);
			// 	Cubo cuboPosFuturoEstado = oddr_to_cube(this.x, this.y);
				
			// 	float distancia=getDistanciaCubos(cuboPosFuturoEstado, cuboMina);
			// 	System.err.println("Distancia mina: "+distancia);

				
			// 	if(distancia<3) {
	 		// 			miPosTieneMina = true;
			// 	}
			// }	
			// for (Iterator iterator = barcosContrarios.iterator(); iterator.hasNext();) {
			// 	Barco oponente = (Barco) iterator.next();
			// 	int posBarcoX= oponente.getPosX();
			// 	int posBarcoY=oponente.getPosY();
				
			// 	Cubo cuboOponente = oddr_to_cube(posBarcoX, posBarcoY);
			// 	Cubo cuboPosFuturoEstado = oddr_to_cube(this.x, this.y);
				
			// 	float distancia=getDistanciaCubos(cuboPosFuturoEstado, cuboOponente);
			// 	System.err.println("Distancia otro barco: "+distancia);

				
			// 	if(distancia<10) {
			// 			 miPosDispara = true;
			// 		//	System.out.println("FIRE " + posBarcoX + " " + posBarcoY);
			// 			 resultadoFinal = -10000;
			
			// 	}
				
			// }	

				// System.err.println("MINA, THIS X "+this.x+" THIS Y"+this.y);
				// //if ((this.x == posMinaX && (this.y == posMinaY+1 ||this.y == posMinaY-1)) || (this.y == posMinaY && (this.x == posMinaX+1 ||this.x == posMinaX-1))   ) {
				// 	if ((this.x -2 == posMinaX && (this.y == posMinaY+1 ||this.y == posMinaY-1)) || (this.y == posMinaY && (this.x == posMinaX+1 ||this.x == posMinaX-1))   ) {
					
				// System.err.println("tengo mina ");
				// 	//resultadoFinal = 10000;
				// 	miPosTieneMina = true;
				// }
			 // Fin for minas
			
	/*		if(unaBala!=null) {
				if (this.x == unaBala.getPosX() && this.y == unaBala.getPosY()) {
					resultadoFinal = 10000;
					miPosTieneBala = true;
				}
			}
		*/	
	//	System.err.println("!miPosTieneMina "+!miPosTieneMina+"!miPosTieneBala"+ !miPosTieneBala);

			if (!miPosTieneMina) {
// && !miPosTieneBala
				for (Iterator iterator = listaBarriles.iterator(); iterator.hasNext();) {
					Barril barrilActual = (Barril) iterator.next();

					int posXBarril = barrilActual.getPosX();
					int posYBarril = barrilActual.getPosY();
					int cantRon = barrilActual.getCantRon();
				
					Cubo cuboBarril = oddr_to_cube(posXBarril, posYBarril);
					Cubo cuboPosFuturoEstado = oddr_to_cube(this.x, this.y);
					
					float distancia=getDistanciaCubos(cuboPosFuturoEstado, cuboBarril);

				

					float posibleHeuristica=distancia-cantRon;
				
					if(resultadoFinal>posibleHeuristica) {
						resultadoFinal=posibleHeuristica;
					}

				 // Fin for barriles
			}
			} 
		System.err.println("Resultado final: "+resultadoFinal);

			return resultadoFinal;
		}

		private static Cubo oddr_to_cube(int fila, int col) {
			int ejeX = col - (fila - (fila & 1)) / 2;
			int ejeZ = fila;
			int ejeY = -ejeX - ejeZ;

			Cubo unCubo = new Cubo(ejeX, ejeY, ejeZ);

			return unCubo;
		}

		private static float getDistanciaCubos(Cubo origen, Cubo destino) {
		
			float dist = (Math.abs(origen.getPosX() - destino.getPosX()) + Math.abs(origen.getPosY() - destino.getPosY())
					+ Math.abs(origen.getPosZ() - destino.getPosZ())) / 2;

			return dist;
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

		public void moverBarco(int xNuevo, int yNuevo) {
			System.out.println("MOVE " + xNuevo + " " + yNuevo);
		}

		public void setXY(int xNuevo, int yNuevo) {
			this.x = xNuevo;
			this.y = yNuevo;
		}

		public int getPosX() {
			return this.x;
		}

		public int getPosY() {
			return this.y;
		}

		public int getId() {
			return this.entityId;
		}
		public int getArg_4() {
            return this.arg4;
        }

        public void mostrarBarco (){
            System.err.println("Barco " + this.entityId +" X:"+this.x+" Y:"+this.y+" Arg1:"+
            this.arg1+" Arg2:"+this.arg2+" Arg3:"+this.arg3+" Arg4:"+this.arg4);
            }


	}// Fin clase Barco

	public static class Barril {
		int entityId;
		String entityType;
		int x;
		int y;
		int arg1;// Cantidad de ron del barril
	

		public Barril(int id, String tipo, int x, int y, int arg1) {
            this.entityId = id;
            this.entityType = tipo;
            this.x = x; 
            this.y = y; 
            this.arg1 = arg1;
        }


		public int getID() {
			return this.entityId;
		}

		public int getPosX() {
			return this.x;
		}

		public int getPosY() {
			return this.y;
		}

		public int getCantRon() {
			return arg1;
		}
		public void mostrarBarril (){
            System.err.println("Barril " + this.entityId +" X:"+this.x+" Y:"+this.y+" Arg1:"+
            this.arg1);
            }


	}// Fin clase Barril

	public static class Mina {
		int entityId;
		String entityType; //MINE
		int x;
		int y;
		

		public Mina(int id,  String tipo,int x, int y) {
			this.entityId = id;
			this.entityType = tipo;
			this.x = x;
			this.y = y;
			
		}

		public int getPosX() {
			return this.x;
		}

		public int getPosY() {
			return this.y;
		}
				public int getId() {
			return this.entityId;
		}
		public void mostrarMina (){
            System.err.println("Mina " + this.entityId +" TIPO:"+this.entityType+" X:"+this.x+" Y:"+this.y);
            }


	}// Fin clase Mina
	
	public static class Cannonball {
		int entityId;
		String entityType; //CANNONBALL
		int x;
		int y;
		int arg1;// el ID de entidad de la nave que disparó esta bola de cañón
        int arg2;//el número de vueltas antes del impacto (1 significa que la bala de cañón aterrizará al final del turno actual)
      
     	public Cannonball(int id, String tipo, int x, int y, int arg1, int arg2) {
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

		public int getId() {
			return this.entityId;
		}

	}// Fin clase Cannonball

	public static class Cubo {
		int x;
		int y;
		int z;

		public Cubo(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
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

	}// Fin clase Cubo

}// Fin clase Player