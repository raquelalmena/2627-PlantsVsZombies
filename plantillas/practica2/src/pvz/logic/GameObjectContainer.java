package pvz.logic;

import java.util.ArrayList;

import java.util.List;

import pvz.logic.gameobjects.GameObject;
import pvz.view.Messages;
import utils.Position;

//guarda todos los objetos del tablero y hace las acciones sobre ellos

public class GameObjectContainer {
	
	private List<GameObject> gameObjects;//lista de todos los objetos
	
	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
	}
	
	//añade o a la lista si no es null
	public void add(GameObject o) {
		if (o != null) {
			this.gameObjects.add(o);
		}
	}
	
	//actualiza todos los objetos vivos
	public void update() {
        for (int i = 0; i < gameObjects.size(); i++) {
        	if(gameObjects.get(i).isAlive()) {
            	gameObjects.get(i).update();
        	}
        }
    }

	public String positionToString(Position p) {
		String c = Messages.EMPTY_STRING;
        
        //recorre los objetos y añade el icon de los de esa pos p
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject objectAct = gameObjects.get(i);
            if (objectAct.isAlive() && objectAct.isInPosition(p)) {
                c += objectAct.getIcon();
            }
        }
        
        return c;
    }
	
	//elimina muertos y on exit
	public boolean removeDead() {
		boolean removed = false;
		int pos = 0;
		
		
		while (pos < gameObjects.size()) {
			if (!gameObjects.get(pos).isAlive()) {
				gameObjects.get(pos).onExit();
				gameObjects.remove(pos);
				removed = true;
			}
			else {
				pos++;
			}
		}
		
		return removed;
	}
	
	//compriueba si en p no hay ningun objeto q pueda compartir casilla
	public boolean isPositionEmpty(Position p) {
		int pos = 0;
		boolean found = false;
		
		while (!found && pos < gameObjects.size()) {
			GameObject gameObject= gameObjects.get(pos);
			found = gameObject.isAlive() && gameObject.isInPosition(p) && !gameObject.canShareCell();
			pos++;
		}
		
		return !found;
	}
	
	//busca objetos vivos en column
	public boolean anyInColumn(int column) {
		int pos = 0;
		boolean found = false;
		
		while (!found && pos < gameObjects.size()) {
			GameObject gameObject= gameObjects.get(pos);
			found = gameObject.isAlive() && gameObject.getPosition().column() == column;
			pos++;
		}
		
		return found;
	}
	
	//coge los objetos que se pueden coger en p y devuelve cuantos soles da
	public int catchObject(Position p) {
		int numCought = 0;
		
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject gameObject= gameObjects.get(i);
			if (gameObject.isAlive() && gameObject.catchObject(p)) {
				numCought++;
			}
		}
		
		return numCought;
	}
	
	//planta ataca al primer objeto de p
	public boolean plantAttack(Position p, int damage) {
		boolean attacked = false;
		int pos = 0;
		
		while (!attacked && pos < gameObjects.size()) {
			GameObject gameObject= gameObjects.get(pos);
			if (gameObject.isAlive() && gameObject.isInPosition(p)) {
				attacked = gameObject.receivePlantAttack(damage);
			}
			pos++;
			
		}
		
		return attacked;
	}
	
	//zombie ataca al primer objeto de p
	public boolean zombieAttack(Position p, int damage) {
		int pos = 0;
		boolean found = false;
		
		while (!found && pos < gameObjects.size()) {
			GameObject gameObject= gameObjects.get(pos);
			if (gameObject.isAlive() && gameObject.isInPosition(p)) {
				found = gameObject.receiveZombieAttack(damage);
			}
			
			pos++;
		}
		
		return found;
	}
}
