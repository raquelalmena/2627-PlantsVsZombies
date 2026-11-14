package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

public abstract class Zombies extends GameObject{
	private int damage;
	private int endurance;
	private int speed;
	
	Zombies(int damage, int endurance, int speed) {
		this.damage = damage;
		this.endurance = endurance;
		this.speed = speed;
	}
	
	Zombies(GameWorld game, Position p, int damage, int endurance, int speed) {
		super(game, p);
		this.damage = damage;
		this.endurance = endurance;
		this.speed = speed;
	}
	
	public abstract String getName();
	
	@Override
	public String getIcon() {
		return String.format(Messages.GAME_OBJECT_ICON, getSymbol(), endurance);
	}
	
	@Override
	public String getDescription() {
		return String.format(Messages.ZOMBIE_DESCRIPTION, getSymbol(), speed, damage, endurance);
	}
	
	@Override
	public boolean isAlive() {
		return endurance > 0;
	}
	
	@Override
	public void onEnter() {}
	
	@Override
	public void onExit() {}
	
	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}
	
	@Override
	public boolean receivePlantAttack(int damage) {
		endurance -= damage;
		return true;
	}
	
	public abstract Zombies copy(Position p,GameWorld gameWorld);
	
	@Override
	public boolean canShareCell() {
		return false;
	}
	
	@Override
	public boolean catchObject() {
		return false;
	}
}
