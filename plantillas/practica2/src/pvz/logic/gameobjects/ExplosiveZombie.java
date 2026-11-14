package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;

public class ExplosiveZombie extends Zombies {
	public static final int DAMAGE = 1;
	public static final int ENDURANCE = 5;
	public static final int SPEED = 2;
	
	
	public ExplosiveZombie() {
		super(DAMAGE, ENDURANCE, SPEED);
	}
	
	public ExplosiveZombie(Position p, GameWorld gameWorld) {
		super(gameWorld, p, DAMAGE, ENDURANCE, SPEED);
	}
	
	@Override
	public String getSymbol() {
		return Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
	}
	
	@Override
	public String getName() {
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
	}
	
	@Override
	public Zombies copy(Position p, GameWorld gameWorld) {
		return new ExplosiveZombie(p, gameWorld);
	}
	
	@Override
	public void onExit() {
		
	}
}
