package pvz.logic.actions;

import pvz.logic.GameWorld;
import utils.Position;
import java.util.List;
import java.util.Arrays;

public class ExplosionAction implements GameAction {
	private Position position;
	private int damage;
	
	public ExplosionAction(Position position, int damage) {
		this.position = position;
		this.damage = damage;
	}
	
	public void execute(GameWorld game) {
		List<Position> positions = Arrays.asList(
				this.position.up(),
				this.position.upRight(),
				this.position.upLeft(),
				this.position.right(),
				this.position.left(),
				this.position.down(),
				this.position.downRight(),
				this.position.downLeft()
		);
		
		for (int i = 0; i < positions.size(); i++) {
			if (!game.plantAttack(positions.get(i), this.damage)) {
				game.zombieAttack(positions.get(i), this.damage);
			}
		}
	}
}
