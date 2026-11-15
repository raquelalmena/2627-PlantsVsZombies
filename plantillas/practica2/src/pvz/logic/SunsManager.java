package pvz.logic;

//controla cuando genrar los soles

public class SunsManager {
	private final GameWorld game;
	
	public static final int FREQUENCY = 5;
	
	private int cycles;
	
	public SunsManager(GameWorld game) {
		this.game = game;
		this.cycles = 0;
	}
	
	public void update() {//genera sol cuando el ciclo llega a frequency
		if (cycles == FREQUENCY) {
			game.generateSun();
			cycles = 0;
		}
		
		cycles++;
	}
}
