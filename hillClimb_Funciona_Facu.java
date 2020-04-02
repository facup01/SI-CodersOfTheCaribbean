import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Player {
	static Barco[] barcos;
	// Barril barriles;
	static List<Movimiento> listaMovimientos = new ArrayList<Movimiento>();

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			int myShipCount = in.nextInt(); // the number of remaining ships
			int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
			barcos = new Barco[myShipCount];

			int indiceBarco = 0;
			int indiceBarril = 0;

			for (int i = 0; i < entityCount; i++) {
				int entityId = in.nextInt();
				String entityType = in.next();
				int x = in.nextInt();
				int y = in.nextInt();
				int arg1 = in.nextInt();
				int arg2 = in.nextInt();
				int arg3 = in.nextInt();
				int arg4 = in.nextInt();
				System.err.println(entityType);

				if (entityType.equals("SHIP")) {
					if (indiceBarco < barcos.length) {

						barcos[indiceBarco] = new Barco(entityId, x, y, arg1, arg2, arg3, arg4);
						indiceBarco++;
					} else {
						for (int k = 0; i < myShipCount; k++) {
							if(barcos[k].getId()==entityId) {
								barcos[k].setXY(x, y);
							}
						}//fin for
					}
				}//fin SHIP

				if (entityType.equals("BARREL")) {

					Iterator iterator = listaMovimientos.iterator();
					boolean yaExisteEntidad = false;

					while (iterator.hasNext()) {
						Movimiento movimientoAux = (Movimiento) iterator.next();
						if (movimientoAux.getEntityID() == entityId) {
							yaExisteEntidad = true;
						}
					} // Fin while

					if (!yaExisteEntidad) {
						Barril unBarril = new Barril(entityId, x, y, arg1, arg2, arg3, arg4);
						Movimiento unMovimiento = new Movimiento(entityType, unBarril, entityId);
						listaMovimientos.add(unMovimiento);
					}

				}

			}

			
			
			
			for (int i = 0; i < myShipCount; i++) {

				Movimiento movimientoFinal = obtenerMejorMovimiento(barcos[i]);
				Barril barrilAMover = movimientoFinal.getBarril();
				
				listaMovimientos.remove(listaMovimientos.indexOf(movimientoFinal));
				barcos[i].moverBarco(barrilAMover.getPosX(), barrilAMover.getPosY());

			} // fin for
			listaMovimientos.clear();
		}
	}

	public static Movimiento obtenerMejorMovimiento(Barco unBarco) {
		Movimiento posibleMovimiento = null;
		Movimiento posibleMovimientoAux = null;
		Iterator iterator = listaMovimientos.iterator();

        if(listaMovimientos.size()>=2){
    		while (iterator.hasNext()) {
    
    			if (posibleMovimiento != null) {
    
    				if (posibleMovimiento.getHeuristica(unBarco) > posibleMovimientoAux.getHeuristica(unBarco)) {
    					posibleMovimiento = posibleMovimientoAux;
    				}
    
    			} else {
    				posibleMovimiento = (Movimiento) iterator.next();
    			}
    
    			posibleMovimientoAux = (Movimiento) iterator.next();
    
    		} // fin while
        }else if(listaMovimientos.size()==1){
            posibleMovimiento = (Movimiento) iterator.next();
        }
        
        
		return posibleMovimiento;
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

	}

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

	}

	public static class Movimiento {
		String tipoEntidad;
		Barril barril;
		int entityId;

		public Movimiento(String tipoEntidad, Barril unBarril, int entityId) {

			this.tipoEntidad = tipoEntidad;
			this.barril = unBarril;
			this.entityId = entityId;
		}

		public int getHeuristica(Barco unBarco) {// mientras mas chica mejor

			int resultado = 0;

			int xBarco = unBarco.getPosX();
			int yBarco = unBarco.getPosY();
			int xBarril = this.barril.getPosX();
			int yBarril = this.barril.getPosY();

			int sumaX = (xBarril - xBarco) * (xBarril - xBarco);
			int sumaY = (yBarril - yBarco) * (yBarril - yBarco);

			resultado = (sumaX + sumaY) - this.barril.getCantRon();

			return resultado;
		}

		public Barril getBarril() {
			return this.barril;
		}

		public int getEntityID() {
			return this.entityId;
		}

	}

}