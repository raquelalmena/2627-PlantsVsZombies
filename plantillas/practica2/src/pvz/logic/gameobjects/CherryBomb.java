package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.actions.ExplosionAction;
import pvz.logic.GameWorld;
import pvz.view.Messages;

//cerezas q explotan. hacen daño alrededor

public class CherryBomb extends Plant{
	public static final int COST = 50;
	public static final int ENDURANCE = 2;
	public static final int EXPLOSION_DAMAGE = 10;
	public static final int EXPLOSION_CYCLES = 2;
	
	private int cycles;
	
	public CherryBomb() {
		super(COST, ENDURANCE, EXPLOSION_DAMAGE);
	}
	
	public CherryBomb(Position p,  GameWorld gameWorld) {
		super(gameWorld, p, COST, ENDURANCE, EXPLOSION_DAMAGE);
	}
	
	@Override
	//devuelve el simbolo en mayusc cuando va a explotar
	public String getSymbol() {
		if (cycles == EXPLOSION_CYCLES) {
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
	public Plant copy(Position p, GameWorld gameWorld) {
		return new CherryBomb(p, gameWorld);
	}
	
	@Override
	//cuando llega al explosion cycles, explota
	public void update() {
		if (isAlive()) {
			cycles++;
			
			if (cycles == EXPLOSION_CYCLES + 1) {
				//casillas a las que hace daño:
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
				
				//añade la accion de explotar y hace daño a las plantas de alrededor
				game.pushAction(new ExplosionAction(EXPLOSION_DAMAGE,true, positions));
				this.die();//muere despues de explotar
				cycles = 0;
			}
		}
	}
}
