package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public class CommonZombie extends Zombie {
	public static final int DAMAGE = 1;
	public static final int ENDURANCE = 5;
	public static final int SPEED = 2;
	
	
	public CommonZombie() {
		super(DAMAGE, ENDURANCE, SPEED);
	}
	
	public CommonZombie(Position p, GameWorld gameWorld) {
		super(gameWorld, p, DAMAGE, ENDURANCE, SPEED);
	}
	
	// Aunque CommonZombie tenga mensaje propio con su Icon usamos la función getIcon() de la superclase Zombie
	
	
	@Override
	public String getSymbol() {
		return Messages.ZOMBIE_SYMBOL;
	}
	
	@Override
	public String getName() {
		return Messages.ZOMBIE_NAME;
	}
	
	@Override
	public Zombie copy(Position p, GameWorld gameWorld) {
		return new CommonZombie(p, gameWorld);
	}
}
