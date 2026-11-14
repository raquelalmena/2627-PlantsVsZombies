package pvz.logic;


public class SunManager {
	private GameWorld game;
	
	public static final int FREQUENCY = 5;
	
	private int cycles;
	
	public SunManager(GameWorld game) {
		this.game = game;
		this.cycles = 0;
	}
	
	public void update() {
		cycles++;
		
		if (cycles == FREQUENCY) {
			game.generateSun();
			cycles = 0;
		}
	}
}
