package pvz.logic.gameobjects;

import pvz.logic.GameWorld;

//aparece en el tablero

import utils.Position;
import pvz.view.Messages;

public class Sun extends GameObject {
	public static final int SUNCOINS = 10;
	public static final int ENDURANCE =  11;
	
	private int endurance;
	
	
	public Sun(Position p, GameWorld game) {
		super(game, p);
		this.endurance = ENDURANCE;
	}
	

	public String getSymbol() {
		return Messages.SUN_SYMBOL;
	}
	
	@Override //devuelve el icono con el simbolo y los ciclos que le quedan
	public String getIcon() {
		return String.format(Messages.GAME_OBJECT_ICON, getSymbol(), endurance);
	}
	
	public String getDescription() {
		return Messages.SUN_DESCRIPTION;
	}
	
	@Override
	public boolean isAlive() {//esta vivo mientras le quede endurance
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
	public void update() {//cada ciclo pierde uno, si llega a 0 desaparece
		if (isAlive()) {
			endurance--;
		}
	}
	
	@Override
	public boolean canShareCell() {//el sol SÍ puede compartir casilla
		return true;
	}
	
	@Override
	public boolean canBeSpawnedAt(Position p) {//en cualquiera dentro del tablero
		return game.isInsideBoard(p);
	}
	
	@Override
	//si el player lo coge da soles y desaparece
	public boolean catchObject(Position p) {
		if (isAlive() && isInPosition(p)) {
			game.generateCoins(SUNCOINS);
			endurance = 0;
			return true;
		}
		return false;
	}
}
