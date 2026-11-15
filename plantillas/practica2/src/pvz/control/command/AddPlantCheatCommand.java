package pvz.control.command;




import pvz.logic.*;
import java.util.Scanner;
import pvz.view.*;
import utils.Position;

public class AddPlantCheatCommand extends Command{
	private String plantName;
	private int col;
	private int row;
	
	private String errorMessage;
	
	public AddPlantCheatCommand() {
		super (
				Messages.COMMAND_CHEAT_PLANT_NAME,
				Messages.COMMAND_CHEAT_PLANT_SHORTCUT,
				Messages.COMMAND_CHEAT_PLANT_DETAILS,
				Messages.COMMAND_CHEAT_PLANT_HELP
		);
	}
	
	private AddPlantCheatCommand(String pN, int c, int r) {
		this();
		this.plantName = pN;
		this.col = c;
		this.row = r;
	}
	
	private AddPlantCheatCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	
	@Override
	public Command create(String[] c) {
	    if (c.length < 4) {
	    	return new AddPlantCheatCommand(Messages.COMMAND_PARAMETERS_MISSING);
	    }
	    
	    if (c.length > 4) {
	    	return new AddPlantCheatCommand(Messages.INVALID_COMMAND);
	    }
	    
	    String pN = c[1];
	    
	    Scanner colScanner = new Scanner(c[2]);
	    Scanner rowScanner = new Scanner(c[3]);
	    
	    if (!colScanner.hasNextInt() || !rowScanner.hasNextInt()) {
			colScanner.close();
			rowScanner.close();
			return new AddPlantCheatCommand(Messages.INVALID_COMMAND);
		}
		
		int col = colScanner.nextInt();
		int row = rowScanner.nextInt();
		
		colScanner.close();
		rowScanner.close();
		
		return new AddPlantCheatCommand(pN, col, row);
	}
	
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		if (errorMessage != null) {
			gv.showError(errorMessage);
			return;
		}
		
		Position pos = new Position(row, col);
		//si los parámetros están mal escritos
		if (!gm.validPlantName(this.plantName)) {
			gv.showError(Messages.INVALID_GAME_OBJECT);
		}
		//casilla mal
		else if (!gm.isInsideBoard(pos) || !gm.isEmpty(pos)) {
			gv.showError(Messages.INVALID_POSITION);
		}
		else if (gm.addGameObjectForFree(plantName, pos)) {
			gm.update();
            gv.showGame();
		}
		else {
			gv.showError(Messages.INVALID_POSITION);
		}
	}
}
