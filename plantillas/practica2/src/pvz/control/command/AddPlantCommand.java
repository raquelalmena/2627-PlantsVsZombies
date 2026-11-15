package pvz.control.command;
import java.util.Scanner;

import pvz.logic.GameModel;
import pvz.view.GameView; 
import pvz.view.Messages;
import utils.Position;

//este comadno permite añadir plantas gastando suncoins
//hereda de Command 

public class AddPlantCommand extends Command{//extends por la herencia de la superclase
	private String plantName;
	private int col;
	private int row;
	
	private String errorMessage;//guarda mensaje de error si comando está mal ecrito
	
	//constructora sin parámetros para AVAILABlE_COMANDS
	public AddPlantCommand() {
		super (//llama a la constructora de Command (superclase)
				Messages.COMMAND_ADD_PLANT_NAME,
				Messages.COMMAND_ADD_PLANT_SHORTCUT,
				Messages.COMMAND_ADD_PLANT_DETAILS,
				Messages.COMMAND_ADD_PLANT_HELP
		);
	}
	
	//constructora con parámetros
	private AddPlantCommand(String pN, int c, int r) {
		// llama primero a la constructora sin parámetros
		this();
		this.plantName = pN;
		this.col = c;
		this.row = r;
	}
	
	private AddPlantCommand(String errorMessage) {//llama a construct sin parámetros y guarga el mensaje de error
		this();
		this.errorMessage = errorMessage;
	}
	

	@Override //sobreescribo el método create heredado de Command
	public Command create(String[] c) { 
		
		if (c.length < 4) {//menso de 4 parametros
			return new AddPlantCommand(Messages.COMMAND_PARAMETERS_MISSING);
		}
		
		if (c.length > 4) {//sobran parametros
			return new AddPlantCommand(Messages.INVALID_COMMAND);
		}
		
		//repito como cheatplant
		String pN = c[1];
		
		Scanner colScanner = new Scanner(c[2]);
		Scanner rowScanner = new Scanner(c[3]);
		
		if (!colScanner.hasNextInt() || !rowScanner.hasNextInt()) {
			colScanner.close();
			rowScanner.close();
			return new AddPlantCommand(Messages.INVALID_COMMAND);
		}
		
		int col = colScanner.nextInt();
		int row = rowScanner.nextInt();
		
		colScanner.close();
		rowScanner.close();
		
		return new AddPlantCommand(pN, col, row);
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		if (errorMessage != null) {//si hay mensaje de error, lo muestra y termina
			gv.showError(errorMessage);
			return;
		}
		
		
		Position pos = new Position(row, col);
	
		//si name mal
		if (!gm.validPlantName(this.plantName)) {
			gv.showError(Messages.INVALID_GAME_OBJECT);
		}
		//si casilla fuera u ocupafa
		else if (!gm.isInsideBoard(pos) || !gm.isEmpty(pos)) {
			gv.showError(Messages.INVALID_POSITION);
		}
		else { //intenta añadir pagando
			if (gm.addGameObject(this.plantName, pos)) {
                gm.update();
                gv.showGame();
            } 
			else  {//no hay soles suficientes
				gv.showError(Messages.NOT_ENOUGH_COINS);
			}
		}	
	}
}