package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

public abstract class Plant extends GameObject{
	 private int cost;
	 private int endurance;
	 private int totalEndurance;
	 private int damage;

	 Plant(int cost, int endurance, int damage) {
		 super();
		 this.cost = cost;
		 this.endurance = endurance;
		 this.damage = damage;
		 this.totalEndurance = endurance;
	 }
	 
	 Plant(GameWorld game, Position p, int cost, int endurance, int damage) {
		 super(game, p);
		 this.cost = cost;
		 this.endurance = endurance;
		 this.damage = damage;
		 this.totalEndurance = endurance;
	 }
	 
	 public abstract String getNameAndShortcut();
	 public abstract String getName();
	 public abstract String getSymbol();
	 
	 @Override
	 public String getIcon() {
		 int enduranceAux = 0;
		 if (isAlive()) enduranceAux = this.endurance;
		 return String.format(Messages.GAME_OBJECT_ICON, getSymbol(), enduranceAux);
	 }
	 
	public String getDescription() {
		return String.format(Messages.PLANT_DESCRIPTION, getNameAndShortcut(), cost, damage, totalEndurance);
	}
	 
	 @Override
	 public boolean isAlive() {
		 return endurance > 0;
	 }
	 
	 @Override
	 public void onEnter() {}
	 
	 @Override
	 public void onExit() {}
	 
	 @Override
	 public boolean receiveZombieAttack(int damage) {
		 endurance -= damage;
		 return true;
	 }
	 
	 public int getCost() {
		    return this.cost;
	 }
	 
	 public int getEndurance() {
		    return this.endurance;
	 }
	 
	 public int getDamage() {
		 return damage;
	 }
	
	 @Override
	 public boolean receivePlantAttack(int damage) {
		 return false;
	 }
	 
	 @Override
	public boolean canBeSpawnedAt(Position p) {
		 return game.isInsideBoard(p) && game.isEmpty(p);
	 }
	 
	 public abstract Plant copy(Position p, GameWorld gameWorld);
	 
	 @Override
	 public boolean canShareCell() {
		 return false;
	 }
	 
	 @Override
	 public boolean catchObject(Position p) {
		 return false;
	 }
	 
	 protected void die() {
		 endurance = 0;
	 }
}
