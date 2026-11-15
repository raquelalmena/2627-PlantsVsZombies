package pvz.logic;

import java.util.ArrayList;

import java.util.List;

import pvz.logic.gameobjects.GameObject;
import pvz.view.Messages;
import utils.Position;

public class GameObjectContainer {
	
	private List<GameObject> gameObjects;
	
	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
	}
	
	public void add(GameObject o) {
		if (o != null) {
			this.gameObjects.add(o);
			o.onEnter();
		}
	}
	
	public void update() {
        for (int i = 0; i < gameObjects.size(); i++) {
        	if(gameObjects.get(i).isAlive()) {
            	gameObjects.get(i).update();
        	}
        }
    }

	public String positionToString(Position p) {
		String c = Messages.EMPTY_STRING;
        
        
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject objectAct = gameObjects.get(i);
            if (objectAct.isAlive() && objectAct.isInPosition(p)) {
                c += objectAct.getIcon();
            }
        }
        
        return c;
    }
	
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
	
	public boolean plantAttack(Position p, int damage) {
		boolean attacked = false;
		int col = p.column();
		
		while (!attacked && col < GameWorld.NUM_COLS) {
			Position nextPos = new Position(p.row(), col);
			for (int i = 0; i < gameObjects.size(); i++) {
				GameObject gameObject= gameObjects.get(i);
				if (!attacked && gameObject.isAlive() && gameObject.isInPosition(nextPos)) {
					attacked = gameObject.receivePlantAttack(damage);
				}
			}
			col++;
			
		}
		
		return attacked;
	}
	
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
