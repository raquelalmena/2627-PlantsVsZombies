package pvz.logic;
import java.util.Random;
import pvz.logic.gameobjects.GameObject;
import pvz.logic.gameobjects.factories.PlantFactory;
import pvz.logic.gameobjects.factories.ZombieFactory;
import utils.Position;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.Sun;
import pvz.logic.gameobjects.Zombies;
import pvz.control.Level;
import pvz.logic.gameobjects.GameObjectContainer;
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
	private int remainingZombies;
	
	private GameObjectContainer container;
	private ZombieManager zombieManager;
	private SunManager sunManager;
	
	
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
		remainingZombies = level.getNumberOfZombies();
		rand = new Random(seed);
		container = new GameObjectContainer();
		zombieManager = new ZombieManager(this, level, rand);
	}
	
	//TODO. Falta comentar lo que hace cada una auqnue sea obvio para que no nos penalice jeje:
	
	@Override
	public GameItem getGameItemInPosition (Position p) {
		return container.getGameItemInPosition(p);
	}
	
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
	}

    @Override
    public void update() {
    	// Falta eliminar objectos muertos
        numCycle++;
        sunManager.update();
        zombieManager.update();
        container.update();
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
    public void addSuncoins(int coins) {
        suncoins += coins;
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
        return container.getGameItemInPosition(p) == null;
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
        return remainingZombies;
    }

    @Override
    public String positionToString(Position p) {
        return this.container.positionToString(p);
    }

    @Override
    public boolean playerWins() {
        return remainingZombies == 0 && zombieManager.allZombiesWereKilled();
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
			generatedCoins++;
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
}
