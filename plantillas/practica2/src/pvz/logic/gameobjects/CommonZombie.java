package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public class CommonZombie extends Zombies {
	public static final int DAMAGE = 1;
	public static final int ENDURANCE = 5;
	public static final int SPEED = 2;
	
	
	public CommonZombie() {
		super(DAMAGE, ENDURANCE, SPEED);
	}
	
	public CommonZombie(Position p, GameWorld gameWorld) {
		super(gameWorld, p, DAMAGE, ENDURANCE, SPEED);
	}
	
	@Override
	public String getSymbol() {
		return Messages.ZOMBIE_SYMBOL;
	}
	
	@Override
	public String getName() {
		return Messages.ZOMBIE_NAME;
	}
	
	@Override
	public Zombies copy(Position p, GameWorld gameWorld) {
		return new CommonZombie(p, gameWorld);
	}
}
