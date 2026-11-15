package pvz.logic.gameobjects;
import utils.Position;

//los q pueden estar en el tablero

public interface GameItem {
	boolean receiveZombieAttack(int damage);
	boolean receivePlantAttack(int damage);
	boolean canShareCell();
	boolean catchObject(Position p);
}
