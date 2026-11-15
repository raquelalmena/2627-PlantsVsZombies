package pvz.logic.gameobjects;
import utils.Position;

public interface GameItem {
	boolean receiveZombieAttack(int damage);
	boolean receivePlantAttack(int damage);
	boolean canShareCell();
	boolean catchObject(Position p);
}
