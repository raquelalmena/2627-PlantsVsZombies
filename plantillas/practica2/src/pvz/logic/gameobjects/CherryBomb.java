package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.actions.ExplosionAction;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public class CherryBomb extends Plant{
	public static final int COST = 50;
	public static final int ENDURANCE = 2;
	public static final int DAMAGE = 0;
	public static final int EXPLOSION_DAMAGE = 10;
	public static final int EXPLOSION_CYCLES = 2;
	
	private int cycles;
	
	public CherryBomb() {
		super(COST, ENDURANCE, DAMAGE);
	}
	
	public CherryBomb(Position p,  GameWorld gameWorld) {
		super(gameWorld, p, COST, ENDURANCE, DAMAGE);
	}
	
	@Override
	public String getSymbol() {
		if (cycles == EXPLOSION_CYCLES - 1) {
			return Messages.CHERRY_BOMB_SYMBOL.toUpperCase();
		}
		else {
			return Messages.CHERRY_BOMB_SYMBOL.toLowerCase();
		}
	}
	
	@Override
	public String getName() {
		return Messages.CHERRY_BOMB_NAME;
	}
	
	@Override
	public String getNameAndShortcut() {
		return Messages.CHERRY_BOMB_NAME_SHORTCUT;
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
		game.pushAction(new ExplosionAction(EXPLOSION_DAMAGE,true, positions));
		endurance = 0;
	}
	
	@Override
	public Plant copy(Position p, GameWorld gameWorld) {
		return new CherryBomb(p, gameWorld);
	}
	
	@Override
	public void update() {
		if (isAlive()) {
			cycles++;
			
			if (cycles == EXPLOSION_CYCLES) {
				this.onExit();
				cycles = 0;
			}
		}
	}
}
