package pvz.logic.gameobjects;

public interface GameItem {
	boolean receiveZombieAttack(int damage);
	boolean receivePlantAttack(int damage);
	boolean canShareCell();
	boolean catchObject();
}
