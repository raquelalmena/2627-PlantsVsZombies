package pvz.logic.actions;

import pvz.logic.GameWorld;
import utils.Position;

public class ExplosionAction implements GameAction {
	private Position[] positions;
	private int damage;
	private boolean plantAttack;
	
	public ExplosionAction(int damage, boolean plantAttack, Position[] positions) {
		this.positions = positions;
		this.damage = damage;
		this.plantAttack = plantAttack;
	}
	
	public void execute(GameWorld game) {		
		for (int i = 0; i < positions.length; i++) {
			if (!plantAttack) {
				game.zombieAttack(positions[i], this.damage);
			}
			else {
				game.plantAttack(positions[i], this.damage);
			}
		}
	}
}
