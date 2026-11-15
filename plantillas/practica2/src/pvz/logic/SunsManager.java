package pvz.logic;


public class SunsManager {
	private final GameWorld game;
	
	public static final int FREQUENCY = 5;
	
	private int cycles;
	
	public SunsManager(GameWorld game) {
		this.game = game;
		this.cycles = 0;
	}
	
	public void update() {
		if (cycles == FREQUENCY) {
			game.generateSun();
			cycles = 0;
		}
		
		cycles++;
	}
}
