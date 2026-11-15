package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

public abstract class Zombie extends GameObject{
	private int damage;
	private int endurance;
	private int totalEndurance;
	private int speed;
	
	private int cycles;
	
	Zombie(int damage, int endurance, int speed) {
		this.damage = damage;
		this.endurance = endurance;
		this.speed = speed;
		this.cycles = 0;
		this.totalEndurance = endurance;
	}
	
	Zombie(GameWorld game, Position p, int damage, int endurance, int speed) {
		super(game, p);
		this.damage = damage;
		this.endurance = endurance;
		this.speed = speed;
		this.cycles = 0;
		this.totalEndurance = endurance;
	}
	
	public abstract String getName();
	public abstract String getSymbol();
	
	@Override
	public String getIcon() {
		return String.format(Messages.GAME_OBJECT_ICON, getSymbol(), endurance);
	}
	
	
	public String getDescription() {
		return String.format(Messages.ZOMBIE_DESCRIPTION, getName(), speed, damage, totalEndurance);
	}
	
	@Override
	public boolean isAlive() {
		return endurance > 0;
	}
	
	@Override
	public void onEnter() {}
	
	@Override
	public void onExit() {
		game.zombieKilled();
	}
	
	@Override
	public void update() {
		if (isAlive()) {
			
			Position nextPos = position.left();
			
			if (game.isEmpty(nextPos)) {
				if (cycles == speed) {
					this.position = nextPos;
					cycles = 1;
				}
				else {
					cycles++;
				}

			}
			else {
				if (cycles < speed){
					cycles++;
				}
				
				game.zombieAttack(nextPos, damage);
			}
			
			
		}
	}
	
	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}
	
	@Override
	public boolean receivePlantAttack(int damage) {
		endurance -= damage;
		return true;
	}
	
	public abstract Zombie copy(Position p,GameWorld gameWorld);
	
	@Override
	public boolean canShareCell() {
		return false;
	}
	
	@Override
	public boolean catchObject(Position p) {
		return false;
	}
	
	@Override
	public boolean canBeSpawnedAt(Position p) {
		return (game.isInsideBoard(p) || game.isInBoardLimit(p)) && game.isEmpty(p);
	}
}
