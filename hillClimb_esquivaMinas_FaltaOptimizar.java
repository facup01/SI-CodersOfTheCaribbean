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
			int myShipCount = in.nextInt(); // the number of remaining ships
			int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
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
						System.err.println("indiceBarco "+indiceBarco);

						barcos[indiceBarco] = new Barco(entityId, x, y, arg1, arg2, arg3, arg4);
						indiceBarco++;
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
					Barril unBarril = new Barril(entityId, x, y, arg1, arg2, arg3, arg4);
					listaBarriles.add(unBarril);
				}
				
				if (entityType.equals("MINE")) {
					Mina unaMina= new Mina(entityId, x, y, arg1, arg2, arg3, arg4);
					listaMinas.add(unaMina);
				}
				
				if (entityType.equals("CANNONBALL")) {
					unaBalaDisparada= new Cannonball(entityId, x, y, arg1, arg2, arg3, arg4);
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
				
				for (Iterator iterator = listaEstadosFuturos.iterator(); iterator.hasNext();) {
					EstadoFuturo unEstadoFuturo = (EstadoFuturo) iterator.next();
					
					if(mejorEstado==null) {
						mejorEstado=unEstadoFuturo;
					}else {
						float mejorHeuristica=mejorEstado.getHeuristica(listaBarriles, listaMinas, unaBalaDisparada);
						float siguienteHeuristica=unEstadoFuturo.getHeuristica(listaBarriles, listaMinas, unaBalaDisparada);
						
						if(mejorHeuristica>siguienteHeuristica) {
							mejorEstado=unEstadoFuturo;
						}
					}//Fin if
				}//Fin for
				
				int movimientoX=mejorEstado.getPosX();
				int movimientoY=mejorEstado.getPosY();
				listaEstadosFuturos.clear();
				
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
			this.miHeuristica = 10000000;
		}


		public int getPosX() {
			return this.x;
		}

		public int getPosY() {
			return this.y;
		}
		
		public float getHeuristica(ArrayList listaBarriles, ArrayList listaMinas, Cannonball unaBala) {

			this.miHeuristica = calcularMiHeuristica(listaBarriles, listaMinas,unaBala);

			return miHeuristica;
		}

		private float calcularMiHeuristica(ArrayList<Barril> listaBarriles, ArrayList<Mina> listaMinas, Cannonball unaBala) {
			/*Metodo que calcula la heuristica del futuro posible estado considerando:
			 * distancia entre el barril mas cercano sumado al que mas Ron tenga*/
			float resultadoFinal = 10000000;
			boolean miPosTieneMina = false;
			boolean miPosTieneBala=false;
			
			
			for (Iterator iterator = listaMinas.iterator(); iterator.hasNext();) {
				// For para verificar si este futuro estado tiene una mina
				Mina mina = (Mina) iterator.next();
				if (this.x == mina.getPosX() && this.y == mina.getPosY()) {
					resultadoFinal = 10000000;
					miPosTieneMina = true;
				}
			} // Fin for minas
			
	/*		if(unaBala!=null) {
				if (this.x == unaBala.getPosX() && this.y == unaBala.getPosY()) {
					resultadoFinal = 10000000;
					miPosTieneBala = true;
				}
			}
		*/	

			if (!miPosTieneMina && !miPosTieneBala) {

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

				} // Fin for barriles

			} // Fin if tiene mina

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
		int x;
		int y;
		int arg1;// Orientacion de rotacion
		int arg2;// Velocidad barco
		int arg3;// Stock de ron barco
		int arg4;// 1 si controlo barco, 0 si no

		public Barco(int id, int x, int y, int arg1, int arg2, int arg3, int arg4) {
			this.entityId = id;
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

	}// Fin clase Barco

	public static class Barril {
		int entityId;
		int x;
		int y;
		int arg1;// Orientacion de rotacion
		int arg2;// Velocidad barco
		int arg3;// Stock de ron barco
		int arg4;// 1 si controlo barco, 0 si no

		public Barril(int id, int x, int y, int arg1, int arg2, int arg3, int arg4) {
			this.entityId = id;
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

		public int getCantRon() {
			return arg1;
		}

	}// Fin clase Barril

	public static class Mina {
		int entityId;
		int x;
		int y;
		int arg1;// Orientacion de rotacion
		int arg2;// Velocidad barco
		int arg3;// Stock de ron barco
		int arg4;// 1 si controlo barco, 0 si no

		public Mina(int id, int x, int y, int arg1, int arg2, int arg3, int arg4) {
			this.entityId = id;
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

		public int getId() {
			return this.entityId;
		}

	}// Fin clase Mina
	
	public static class Cannonball {
		int entityId;
		int x;
		int y;
		int arg1;// Orientacion de rotacion
		int arg2;// Velocidad barco
		int arg3;// Stock de ron barco
		int arg4;// 1 si controlo barco, 0 si no

		public Cannonball(int id, int x, int y, int arg1, int arg2, int arg3, int arg4) {
			this.entityId = id;
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