package pvz.logic.gameobjects;

import pvz.view.Messages;
import pvz.logic.actions.ExplosionAction;
import utils.Position;
import pvz.logic.GameWorld;

public class ExplosiveZombie extends Zombies {
	public static final int DAMAGE = 1;
	public static final int ENDURANCE = 5;
	public static final int SPEED = 2;
	public static final int EXPLOSION_DAMAGE = 3;
	
	
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
		Position[] positions = {
				this.position.up(),
				this.position.upRight(),
				this.position.upLeft(),
				this.position.right(),
				this.position.left(),
				this.position.down(),
				this.position.downRight(),
				this.position.downLeft()
		};
		super.onExit();
		game.pushAction(new ExplosionAction(EXPLOSION_DAMAGE, false, positions));
	}
}
