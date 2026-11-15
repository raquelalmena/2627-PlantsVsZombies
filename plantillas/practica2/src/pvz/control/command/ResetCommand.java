package pvz.control.command;
import java.util.Scanner;

import pvz.control.Level;
//import java.util.Scanner; es sin Scanner, igual que AddPlants
import pvz.logic.GameModel; 
import pvz.view.GameView; 
import pvz.view.Messages;

//propuesta: es más corto así

public class ResetCommand extends Command {
	private Level level;
	private long seed;
	
	boolean resetAll;
	private String errorMessage;
	
	public ResetCommand() {
		super (
				Messages.COMMAND_RESET_NAME, 
				Messages.COMMAND_RESET_SHORTCUT, 
				Messages.COMMAND_RESET_DETAILS, 
				Messages.COMMAND_RESET_HELP
		);
		this.resetAll = false;
	}
	
	public ResetCommand(Level level, long seed) {
		this();
		this.seed = seed;
		this.level = level;
		this.resetAll = true;
	}
	
	private ResetCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	@Override
	public Command create(String[] c) {		
		if (c.length == 1) {
			return new ResetCommand();
		}
		
		if (c.length == 3) {
			Level l = Level.valueOfIgnoreCase(c[1]);
			
			// leemos seed se la cadena de String[] c
			Scanner seedScanner = new Scanner(c[2]);
			
			if (l == null || !seedScanner.hasNextLong()) {
				seedScanner.close();
				return new ResetCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
			
			// creamos una variable de tipo long que almacene seed
			long new_seed = seedScanner.nextLong();
			seedScanner.close();
			
			return new ResetCommand(l, new_seed);
		}
		
		return new ResetCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		if (errorMessage != null) {
			gv.showError(errorMessage);
			return;
		}
		
		if (resetAll) {
			gm.reset(level, seed);
		}
		else {
			gm.reset();//reinica estado del juego, listas y contadores
		}
		
		gv.showGame();//muestra tablero inicial ya reiniciado
	}
}