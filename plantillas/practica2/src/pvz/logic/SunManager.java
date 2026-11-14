package pvz.logic;

import utils.Position;
import pvz.logic.gameobjects.Sun;

public class SunManager {
	private GameWorld game;
	
	public static final int FREQUENCY = 5;
	
	private int cycles;
	
	public SunManager(GameWorld game) {
		this.game = game;
		this.cycles = 0;
	}
	
	public boolean addSun() {
		Position spawnPos = game.randomPosition();
		
		Sun newSun = new Sun(spawnPos, game);
		
		return game.addGameObject(newSun);
	}
	
	public void update() {
		cycles++;
		
		if (cycles == FREQUENCY) {
			addSun();
			cycles = 0;
		}
	}
}
