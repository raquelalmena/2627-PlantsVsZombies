package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

public class Peashooter extends Plant{
	public static final int COST = 50;
	public static final int ENDURANCE = 3;
	public static final int DAMAGE = 1;
	
	public Peashooter() {
		super(COST, ENDURANCE, DAMAGE);
	}
	
	public Peashooter(Position p, GameWorld game) {
		super(game, p, COST, ENDURANCE, DAMAGE);
	}
	
	@Override
	 public void update() {
		 if (isAlive()) {
			 Position attackPosition = position;
			 boolean attacked= false;
			 
			 while(!attacked && game.isInsideBoard(attackPosition)) {
				 attacked = game.plantAttack(attackPosition, DAMAGE);
				 attackPosition = attackPosition.right();
			 }
		 }
	 }
	
	// Aunque Peashooter tenga mensaje propio con su Icon usamos la función getIcon() de la superclase Plant
	
	@Override
    public String getSymbol() {
        return Messages.PEASHOOTER_SYMBOL;
    }

    @Override
    public String getNameAndShortcut() {
        return Messages.PEASHOOTER_NAME_AND_SHORTCUT;
    }
    
    @Override
    public Plant copy(Position p, GameWorld gameWorld) {
    	return new Peashooter(p, gameWorld);
    }
    
    @Override
    public String getName() {
    	return Messages.PEASHOOTER_NAME;
    }
}
