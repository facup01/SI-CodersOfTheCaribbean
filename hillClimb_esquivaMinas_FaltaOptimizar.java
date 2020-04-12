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
	static Cannonball unaBalaDisparada;
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		listaBarriles=new ArrayList<Barril>();
		listaMinas=new ArrayList<Mina>();
		listaEstadosFuturos=new ArrayList<EstadoFuturo>();
		
		
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
					if (indiceBarco < barcos.length) {
						
						barcos[indiceBarco] = new Barco(entityId,entityType, x, y, arg1, arg2, arg3, arg4);
						indiceBarco++;
			// 			System.err.println("Barco " + entityId +" X:"+x+" Y:"+y+" Arg1:"+
            // arg1+" Arg2:"+arg2+" Arg3:"+arg3+" Arg4:"+arg4);
					
					} else {
						for (int k = 0; i < barcos.length; k++) {
							if (barcos[k].getId() == entityId) {
								System.err.println("k "+k);

								// Si ya existe el barco, se le actualiza la posicion xy a la entidad que lo
								// representa
								barcos[k].setXY(x, y);
							}
						} // fin for
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
				

				if(posXBarcoRow-1>=0) {
					// axial coordinates q=0 r=-1
					listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow-1, posYBarcoCol));
				}
				
				if(posXBarcoRow-1>=0 && posYBarcoCol+1<=22) {
					// axial coordinates q=+1 r=-1
					listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow-1, posYBarcoCol+1));
				}
				
				if(posYBarcoCol+1<=22) {
					// axial coordinates q=+1 r=0
					listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow, posYBarcoCol+1));
				}
				
				if(posXBarcoRow+1<=20) {
					// axial coordinates q=0 r=+1
					listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow+1, posYBarcoCol));
				}
				
				if(posXBarcoRow+1<=20 && posYBarcoCol-1>=0) {
					// axial coordinates q=-1 r=+1
					listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow, posYBarcoCol+1));
				}
				
				if(posYBarcoCol-1>=0) {
					// axial coordinates q=-1 r=0
					listaEstadosFuturos.add(new EstadoFuturo(posXBarcoRow, posYBarcoCol-1));
				}
				
				EstadoFuturo mejorEstado=null;
				
				// for (Iterator iterator = listaEstadosFuturos.iterator(); iterator.hasNext();) {
				// 	System.err.println("listaEstadosFuturos "+listaEstadosFuturos.size());
		
				// 	EstadoFuturo unEstadoFuturo = (EstadoFuturo) iterator.next();
					
				// 	if(mejorEstado==null) {
				// 		mejorEstado=unEstadoFuturo;
				// 	}else {
				// 		float mejorHeuristica=mejorEstado.getHeuristica(listaBarriles, listaMinas, unaBalaDisparada);
				// 		System.err.println("mejor heuristica de mejor estado "+mejorHeuristica);
		
				// 		float siguienteHeuristica=unEstadoFuturo.getHeuristica(listaBarriles, listaMinas, unaBalaDisparada);
						
				// 		if(mejorHeuristica>siguienteHeuristica) {
				// 			mejorEstado=unEstadoFuturo;
				// 		}
				// 	}//Fin if
				// }//Fin for
				Iterator iterator= listaEstadosFuturos.iterator();
				double mejorHeuristica = 10000;
				
				System.err.println("Estados posibles: "+listaEstadosFuturos.size());
		
				for (int j = 0; j <listaEstadosFuturos.size(); j++) {
					EstadoFuturo unEstado= (EstadoFuturo) iterator.next(); // recorremos el array de estados
					unEstado.mostrarEstado();
		
				
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
				barcos[i].mostrarBarco();
				barcos[i].moverBarco(movimientoX, movimientoY);
				
			} // Fin for barcos
			
			listaBarriles.clear();
			
			listaMinas.clear();
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
			boolean miPosTieneBala=false;
			for (Iterator iterator = listaMinas.iterator(); iterator.hasNext();) {
				Mina mina = (Mina) iterator.next();
				int posMinaX= mina.getPosX();
				int posMinaY=mina.getPosY();
				
				Cubo cuboMina = oddr_to_cube(posMinaX, posMinaY);
				Cubo cuboPosFuturoEstado = oddr_to_cube(this.x, this.y);
				
				float distancia=getDistanciaCubos(cuboPosFuturoEstado, cuboMina);
				System.err.println("Distancia mina: "+distancia);

				
				if(distancia<2) {
	 					miPosTieneMina = true;
				}

		
		}
		 // Fin if tiene mina

			
	// 		for (Iterator iterator = listaMinas.iterator(); iterator.hasNext();) {
	// 			// For para verificar si este futuro estado tiene una mina
	// 			Mina mina = (Mina) iterator.next();
	// 			int posMinaX= mina.getPosX();
	// 			int posMinaY=mina.getPosY();
	// 			int[] arriesgados_1 = {posMinaX, posMinaY};
	// 			int[] arriesgados_2={posMinaX -1, posMinaY-1};
	// 			int[] arriesgados_3={posMinaX, posMinaY-1};
	// 			int[] arriesgados_4={posMinaX+1, posMinaY};
	// 			int[] arriesgados_5={posMinaX, posMinaY+1};
	// 			int[] arriesgados_6={posMinaX-1, posMinaY+1};
	// 			int[] arriesgados_7={posMinaX-1, posMinaY};

	// 			if((this.x == arriesgados_1[0] && this.y== arriesgados_1[1])||
	// 				(this.x == arriesgados_2[0] && this.y== arriesgados_2[1]) ||
	// 				(this.x == arriesgados_3[0] && this.y== arriesgados_3[1]) ||
	// 				(this.x == arriesgados_4[0] && this.y== arriesgados_4[1])||
	// 				(this.x == arriesgados_5[0] && this.y== arriesgados_5[1])||
	// 				(this.x == arriesgados_6[0] && this.y== arriesgados_6[1])||
	// 				(this.x == arriesgados_7[0] && this.y== arriesgados_7[1])){
	// 					System.err.println("tengo mina ");
	// 					resultadoFinal = 10000;
	// 					miPosTieneMina = true;
	// 				}
					
		

	
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
			} // Fin if tiene mina
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