package pvz.logic.actions;

import pvz.logic.GameWorld;
import utils.Position;

//puede ser de planta o zombie
//hace daño a las casilla que recibe

public class ExplosionAction implements GameAction {
	private Position[] positions; //casillas que van a sre atacadas
	private int damage;
	private boolean plantAttack; //true si plantaa, false si zombie
	
	public ExplosionAction(int damage, boolean plantAttack, Position[] positions) {
		this.positions = positions;
		this.damage = damage;
		this.plantAttack = plantAttack;
	}
	
	//recorre todas las casillas y hace daño segun quien ataque
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
