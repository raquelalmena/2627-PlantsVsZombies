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
import pvz.logic.gameobjects.Zombies;
import pvz.control.Level;
import pvz.logic.gameobjects.GameItem;


// FALTA RANDOM

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
	
	private GameObjectContainer container;
	private ZombieManager zombieManager;
	private SunManager sunManager;
	
	private Deque<GameAction> actions;
	
	
	public Game(long seed, Level level) {
		this.seed = seed;
		this.level = level;
		initGame();
	}
	
	private void initGame() { //separo del cosntructor para usar en reset
		//ya no Random rand pq no es local
		numCycle  = 0;
		suncoins = INITIAL_COINS;
		playerQuits = false;
		rand = new Random(seed);
		container = new GameObjectContainer();
		zombieManager = new ZombieManager(this, level, rand);
	}
	
	//TODO. Falta comentar lo que hace cada una auqnue sea obvio para que no nos penalice jeje:
	
	@Override
	public int getCycles() {
		return numCycle;
	}
	
	@Override
    public void reset() {
        initGame();
        
    }
	
	@Override
	public void reset(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		initGame();
		this.actions = new ArrayDeque<>();
	}

    @Override
    public void update() {
    	// 1. Execute pending actions
    	executePendingActions();
    	
    	// 2. Execute game Actions
    	
    	// 3. Game object updates
    	
        numCycle++;
        sunManager.update();
        zombieManager.update();
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
        Plant plant = PlantFactory.spawnPlant(objectName, this, p);
        
        // si nos devuelve null entonces el objectName no corresponde al de una planta
        if (plant != null && this.suncoins >= plant.getCost()) {
            this.suncoins -= plant.getCost();//descuenta soles
            return addGameObject(plant);
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
        Zombies zombie = ZombieFactory.spawnZombie(objectName, this, p);
        
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
    		limit = p.column() == -1 || p.column() == Game.NUM_COLS;
    	}
    	return limit;
    }
    
    @Override
    public boolean isValidPosition(Position p) {
    	boolean valid = false;
    	if (p != null) {
    		valid = isInsideBoard(p) || isInBoardLimit(p);
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
        return playerQuits || playerWins() || container.doZombieReachedTheHouse();
    }
    
    @Override
    public boolean doZombiesReachedTheHouse() {
    	return container.doZombieReachedTheHouse();
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
			generateCoins(Sun.SUNCOINS);
		}
    }
    
    @Override
    public boolean catchObject(Position p) {
    	GameItem objectInPosition = container.getGameItemInPosition(p);
    	
    	boolean catched = objectInPosition.catchObject();
    	
    	if (catched) {
    		caughtCoins++;
    		container.removeDead();
    	}
    	
    	return catched;
    }
    
    @Override
    public boolean plantAttack(Position p, int damage) {
    	GameItem objectInPosition = container.getGameItemInPosition(p);
    	
    	return objectInPosition.receivePlantAttack(damage);
    }
    
    @Override
	public boolean zombieAttack(Position p, int damage) {
    	GameItem objectInPosition = container.getGameItemInPosition(p);
    	
    	return objectInPosition.receiveZombieAttack(damage);
    }
    
    @Override
    public void generateCoins(int coins) {
    	generatedCoins += coins;
    	suncoins += coins;
    }
    
    @Override
    public void zombieKilled() {
        zombieManager.zombieKilled();
    }
}
