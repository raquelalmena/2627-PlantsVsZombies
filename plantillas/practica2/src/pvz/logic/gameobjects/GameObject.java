package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import utils.Position;

public abstract class GameObject implements GameItem {
	protected GameWorld game;
	protected Position position;
	
	public GameObject() {
		this.game = null;
		this.position = null;
	}
	
	public GameObject(GameWorld game, Position p) {
		this.game = game;
		this.position = p;
	}
	
	public boolean isInPosition(Position other) {
        return this. position.equals(other);	
	}
	
	public Position getPosition() {
		return position;
	}
	
	public abstract String getIcon();
	
	public abstract boolean isAlive();
	
	public abstract void onEnter();
	
	public abstract void onExit();
	
	public abstract void update();
	
	public abstract boolean canBeSpawnedAt(Position p);
	
}
