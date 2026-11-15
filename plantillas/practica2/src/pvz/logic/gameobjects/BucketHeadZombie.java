package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public class BucketHeadZombie extends Zombie {
	public static final int DAMAGE = 1;
	public static final int ENDURANCE = 8;
	public static final int SPEED = 4;
	
	public BucketHeadZombie() {
		super(DAMAGE, ENDURANCE, SPEED);
	}
	
	public BucketHeadZombie(Position p, GameWorld gameWorld) {
		super(gameWorld, p, DAMAGE, ENDURANCE, SPEED);
	}
	
	@Override
	public String getSymbol() {
		return Messages.BUCKET_HEAD_ZOMBIE_SYMBOL;
	}
	
	@Override
	public String getName() {
		return Messages.BUCKET_HEAD_ZOMBIE_NAME;
	}
	
	@Override
	public Zombie copy(Position p, GameWorld gameWorld) {
		return new BucketHeadZombie(p, gameWorld);
	}
}
