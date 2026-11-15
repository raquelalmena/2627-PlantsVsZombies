package pvz.logic;
//GameWorld ahora tiene solo los métodos relacionados con OBJETOS. plntas y zombies


import pvz.logic.actions.GameAction;
import pvz.logic.gameobjects.GameObject;

import utils.Position;

public interface GameWorld {
	int NUM_ROWS = 4;//las variables declaradas en una interface son ya public static final, no hace falta ponerlo
	int NUM_COLS = 8;
	
	String positionToString(Position p); //contenido de la casilla
	boolean isEmpty(Position p);//si casilla vacía
	boolean isInsideBoard(Position p);//si posición válida dentro del tablero
	boolean isInBoardLimit(Position p);
	Position randomPosition();
	void generateSun();
	void generateCoins(int coins);
	
	void zombieAdded();
	void zombieKilled();
	
	boolean plantAttack(Position p, int damage);
	boolean zombieAttack(Position p, int damage);
	
	boolean addGameObject(GameObject gameObject);
	
	void pushAction(GameAction gameAction);
	}
