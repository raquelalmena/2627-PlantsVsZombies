package pvz.logic;

import java.util.Random;
import java.util.List;
import pvz.control.Level;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.factories.ZombieFactory;
import utils.Position;


/**
 * Manages the full lifecycle of zombies for a game session.
 *
 * <p>Responsibilities: deciding each cycle whether to spawn a new zombie
 * (probabilistically, subject to the remaining quota from {@link Level}),
 * delegating per-cycle updates and dead-removal to the underlying
 * {@link ZombieList}, and answering win/loss queries
 *
 */
public class ZombieManager {

	private GameWorld game;
	private Level level;
	private Random rand;
	private int remainingZombies;
	private int zombiesAlive; //3.1

	public ZombieManager(Game game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		this.rand = rand;
		this.remainingZombies = level.getNumberOfZombies();
		this.zombiesAlive = 0;
	}

	/**
	 * Checks if the game should add (if possible) a zombie to the game.
	 * 
	 * @return <code>true</code> if a zombie should be added to the game.
	 */
	private boolean shouldAddZombie() {
		return rand.nextDouble() < level.getZombieFrequency();
	}
	
	/**
	 * Return a random row within the board limits.
	 * 
	 * @return a random row.
	 */
	
	private int randomZombieRow() {
		return rand.nextInt(GameWorld.NUM_ROWS);
	}
	
	private void addZombie() {
		int row = randomZombieRow();
		String zombieName = randomZombieName();
		addZombie(row, zombieName);
	}

	public void addZombie(int row, String zombieName) {
		if (remainingZombies > 0 && shouldAddZombie()) {
			Position spawnPos = new Position(row, GameWorld.NUM_COLS);
			
			Zombie newZombie = ZombieFactory.spawnZombie(zombieName, game, spawnPos);

			// si se ha podido añadir el Zombie entonces restamos el número de zombies que faltan
			if (newZombie != null && game.addGameObject(newZombie)) {
				remainingZombies--;
			}
		}
		
		
	}
	
	private String randomZombieName() {
		// Obtenemos la lista de Zombies disponibles
		List<Zombie> zombiesList = ZombieFactory.getAvailableZombies();
		// Elegimos una posición de la lista aleatoria para obtener un zombie aleatorio
		int randomListPos = rand.nextInt(zombiesList.size());
		
		// Devolvemos el nombre de ese Zombie aleatorio
		return zombiesList.get(randomListPos).getName();
	}
	
	public void update() {
		addZombie();
	}
	
	public int getRemainingZombies() {
		return remainingZombies;
	}
	
	public boolean allZombiesWereKilled() {
		return (remainingZombies == 0 && zombiesAlive == 0); //3.1
	}
	
	public void zombieKilled() {
		zombiesAlive--; //3.1
	}
	
	public void zombieAdded() {//3.1
		zombiesAlive++;
	}
 }
