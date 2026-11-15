package pvz.logic;
import java.util.Random;

import java.util.Deque;
import pvz.logic.actions.GameAction;
import java.util.ArrayDeque;
import pvz.logic.gameobjects.GameObject;
import pvz.logic.gameobjects.factories.PlantFactory;
import pvz.logic.gameobjects.factories.ZombieFactory;
import utils.Position;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.Sun;
import pvz.logic.gameobjects.Zombie;
import pvz.control.Level;


public class Game implements GameStatus, GameWorld, GameModel {
	public static final int INITIAL_COINS = 50;
	
	private boolean playerQuits;
	private Level level;
	private long seed;
	private Random rand;
	private int numCycle;
	private int suncoins;
	private int generatedCoins;
	private int caughtCoins;
	
	// Así podemos comprobar que solo coge soles una vez por cada ciclo
	private boolean caughtSun;
	
	private GameObjectContainer container;
	private ZombiesManager zombieManager;
	private SunsManager sunManager;
	
	private Deque<GameAction> actions;
	
	
	public Game(long seed, Level level) {
		this.seed = seed;
		this.level = level;
		reset();
	}
	
	
	@Override
	public int getCycles() {
		return numCycle;
	}
	
	@Override
    public void reset() {
		numCycle  = 0;
		suncoins = INITIAL_COINS;
		playerQuits = false;
		rand = new Random(seed);
		container = new GameObjectContainer();
		zombieManager = new ZombiesManager(this, level, rand);
		generatedCoins = 0;
		caughtCoins = 0;
		caughtSun = false;
		sunManager = new SunsManager(this);
		
		this.actions = new ArrayDeque<>();        
    }
	
	@Override
	public void reset(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		reset();
	}

    @Override
    public void update() {
    	// 1. Execute pending actions
    	executePendingActions();
    	
    	// 2. Execute game Actions
        zombieManager.update();
    	sunManager.update();

    	
    	// 3. Game object updates
        container.update();
        
        // 4. & 5. Remove dead and execute pending actions
        boolean deadRemoved = true;
        while (deadRemoved || arePendingActionsLeft()) {
        	// 4. Remove dead
        	deadRemoved = this.container.removeDead();
        	
        	// 5. execute pending actions
        	executePendingActions();
        }
        
        this.numCycle++;
        caughtSun = false;
    }
    
    public void pushAction(GameAction gameAction) {
    	  this.actions.addLast(gameAction);
	}

	private void executePendingActions() {
	  while (!this.actions.isEmpty()) {
	    GameAction action = this.actions.removeFirst();
	    action.execute(this);
	  }
	}

	private boolean arePendingActionsLeft() {
	  return !this.actions.isEmpty();
	}

    @Override
    public void quit() {
        this.playerQuits = true;
    }
    
    @Override
    public boolean addGameObject(String objectName, Position p) {
    	// comprobamos si objectName coincide con alguno de los nombres de las plantas disponibles
    	if (PlantFactory.validPlantName(objectName) && isValidPosition(p)) {
    		Plant plant = PlantFactory.spawnPlant(objectName, this, p);
            
            // si nos devuelve null entonces el objectName no corresponde al de una planta
            if (plant != null && this.suncoins >= plant.getCost()) {
                this.suncoins -= plant.getCost();//descuenta soles
                return addGameObject(plant);
            }
            

    	}
    	
    	if (ZombieFactory.validZombieName(objectName)) {
    	        Zombie zombie = ZombieFactory.spawnZombie(objectName, this, p);

    	        if (zombie != null && addGameObject(zombie)) {
    	            zombieManager.zombieAdded();
    	        	return true;
    	        }
    	}
    	
        return false;
    }
    
    @Override
    public boolean addGameObjectForFree(String objectName, Position p) {
    	// comprobamos si objectName coincide con alguno de los nombres de las plantas disponibles
        Plant plant = PlantFactory.spawnPlant(objectName, this, p);
        
        // si nos devuelve null entonces el objectName no corresponde al de una planta
        if (plant != null) {
        	// no comprobamos si tenemos suficientes suncoins
            return addGameObject(plant);
        }
        
        // comprobamos si objectName coincide con alguno de los nombres de los zombies disponibles
        Zombie zombie = ZombieFactory.spawnZombie(objectName, this, p);
        
        // si nos devuelve null entonces el objectName no corresponde al de un zombie
        if (zombie != null) {
        	return addGameObject(zombie);
        }
        
        return false;
    }
    
    @Override
    public boolean addGameObject(GameObject gameObject) {
    	if (gameObject == null || !gameObject.canBeSpawnedAt(gameObject.getPosition())) {
    		return false;
    	}
    	
    	container.add(gameObject);
    	gameObject.onEnter();
    	
    	return true;
    }

    @Override
    public int getCoins() {
        return suncoins;
    }

    @Override
    public boolean isInsideBoard(Position p) {
        boolean inside = false;
        if (p != null) {
        	inside = p.row() >= 0 && p.row() < NUM_ROWS && p.column() >= 0 && p.column() < NUM_COLS;
        }
        return inside;
    }
    
    @Override
    public boolean isInBoardLimit(Position p) {
    	boolean limit = false;
    	if (p != null) {
    		limit = p.column() >= -1 && p.column() <= Game.NUM_COLS && p.row() >= 0 && p.row() < NUM_ROWS;
    	}
    	return limit;
    }
    

    public boolean isValidPosition(Position p) {
    	boolean valid = false;
    	if (p != null) {
    		valid = isInsideBoard(p) && isEmpty(p);
    	}
    	return valid;
    }

    @Override
    public boolean isEmpty(Position p) {
        return container.isPositionEmpty(p);
    }

    @Override
    public boolean validPlantName(String plantName) {
    	return PlantFactory.validPlantName(plantName);
    }
    
    @Override
    public boolean validZombieName(String zombieName) {
    	return ZombieFactory.validZombieName(zombieName);
    }

    @Override
    public int getRemainingZombies() {
        return zombieManager.getRemainingZombies();
    }

    @Override
    public String positionToString(Position p) {
        return this.container.positionToString(p);
    }

    @Override
    public boolean playerWins() {
        return zombieManager.allZombiesWereKilled();
    }

    @Override
    public boolean playerQuits() {
        return playerQuits;
    }

    @Override
    public boolean hasGameFinished() {
        return playerQuits || playerWins() || doZombiesReachedTheHouse();
    }
    
    @Override
    public boolean doZombiesReachedTheHouse() {
    	return container.anyInColumn(-1);
    }
    
    @Override
    public Position randomPosition() {
    	int randomRow = rand.nextInt(NUM_ROWS);
    	int randomCol = rand.nextInt(NUM_COLS);
    	
    	return new Position(randomRow, randomCol);
    }
    
    @Override
    public int getGeneratedCoins() {
    	return generatedCoins;
    }
    
    @Override
    public int getCaughtCoins() {
    	return caughtCoins;
    }
    
    @Override
    public void generateSun() {
    	Position spawnPos = randomPosition();
		
		Sun newSun = new Sun(spawnPos, this);
		
		if (addGameObject(newSun)) {
			generatedCoins++;
		}
    }
    
    @Override
    public boolean catchObject(Position p) {
    	boolean caught = false;
    	if (!caughtSun && isInsideBoard(p)) {
    		int collectedSuns = container.catchObject(p);
        	if (collectedSuns > 0) {
        		caughtSun = true;
        		caughtCoins += collectedSuns;
            	caught = true;
        	}

    	}
    	
    	return caught;
    	
    }
    
    @Override
    public boolean plantAttack(Position p, int damage) {
    	return container.plantAttack(p, damage);
    }
    
    @Override
	public boolean zombieAttack(Position p, int damage) {
    	return container.zombieAttack(p, damage);
    }
    
    @Override
    public void generateCoins(int coins) {
    	suncoins += coins;
    }
    
    @Override
    public void zombieKilled() {
        zombieManager.zombieKilled();
    }
}
