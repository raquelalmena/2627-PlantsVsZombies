package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import utils.Position;
import pvz.view.Messages;

public class Sun extends GameObject {
	public static final int SUNCOINS = 10;
	public static final int ENDURANCE =  11;
	
	private int endurance;
	
	public Sun() {
		super();
		this.endurance = ENDURANCE;
	}
	
	public Sun(Position p, GameWorld game) {
		super(game, p);
		this.endurance = ENDURANCE;
	}
	
	@Override
	public String getSymbol() {
		return Messages.SUN_SYMBOL;
	}
	
	@Override
	public String getIcon() {
		return String.format(Messages.GAME_OBJECT_ICON, getSymbol(), endurance);
	}
	
	@Override
	public String getDescription() {
		return String.format(Messages.SUN_DESCRIPTION);
	}
	
	@Override
	public boolean isAlive() {
		return endurance > 0;
	}
	
	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}
	
	@Override
	public boolean receivePlantAttack(int damage) {
		return false;
	}
	
	@Override
	public void onEnter() {}
	
	@Override
	public void onExit() {}
	
	@Override
	public void update() {
		if (isAlive()) {
			endurance--;
		}
	}
	
	@Override
	public boolean canShareCell() {
		return true;
	}
	
	@Override
	public boolean canBeSpawnedAt(Position p) {
		return game.isInsideBoard(p);
	}
	
	@Override
	public boolean catchObject() {
		return true;
	}
}
