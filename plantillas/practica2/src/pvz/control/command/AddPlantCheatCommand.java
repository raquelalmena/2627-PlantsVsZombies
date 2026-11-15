package pvz.control.command;

import pvz.logic.*;
import java.util.Scanner;
import pvz.view.*;
import utils.Position;

//este command permite añadir una planta gratis
//hereda de comand asi q se pone extends Command, para poder sobreescribir funciones de command

public class AddPlantCheatCommand extends Command{
	
	private String plantName;
	private int col;
	private int row;
	private String errorMessage; //guarda mensaje de error quue corresponda
	
	//constructora sin parámetros para AVAILABLE_COMMANDS
	public AddPlantCheatCommand() {
		//super llama a la constructora de command (sperclase)
		super (
				Messages.COMMAND_CHEAT_PLANT_NAME,
				Messages.COMMAND_CHEAT_PLANT_SHORTCUT,
				Messages.COMMAND_CHEAT_PLANT_DETAILS,
				Messages.COMMAND_CHEAT_PLANT_HELP
		);
	}
	
	//constructora con parámetros
	private AddPlantCheatCommand(String pN, int c, int r) {
		this();//llama a la constrcutora sin parámetros
		this.plantName = pN;
		this.col = c;
		this.row = r;
	}
	
	//constructora para cuando hay un error
	private AddPlantCheatCommand(String errorMessage) {
		this(); //llama a la constructora sin parámetros para mantener name, shortcut, details y help del comando
		this.errorMessage = errorMessage;//guarda el error
	}
	
	
	@Override //para sobreescribir funciones de la superclase 
	public Command create(String[] c) {
	    if (c.length < 4) { //faltan datos
	    	//devuelve un nuevo objeto con el error missing parameters
	    	return new AddPlantCheatCommand(Messages.COMMAND_PARAMETERS_MISSING);
	    }
	    
	    if (c.length > 4) { //si hay mas de 4 es que sobran 
	    	//devuelve un nuevo objeto con error invalid command
	    	return new AddPlantCheatCommand(Messages.INVALID_COMMAND);
	    }
	    
	    String pN = c[1];
	    
	    Scanner colScanner = new Scanner(c[2]); //crea Scanner para leer col y lo guarda en colScanner
	    Scanner rowScanner = new Scanner(c[3]); //igual con row
	    
	    if (!colScanner.hasNextInt() || !rowScanner.hasNextInt()) {//con hasNextInt comprueba si NO son enteros
			colScanner.close();
			rowScanner.close();
			return new AddPlantCheatCommand(Messages.INVALID_COMMAND);//devuelve con error invalid command
		}
		
	    //son enteros si no entra en el if,guardo esos valores en col y roww
		int col = colScanner.nextInt();
		int row = rowScanner.nextInt();
		
		colScanner.close();
		rowScanner.close();
		
		//devuelve un nuevo addplantcheat con los 3 parametros correctoe
		return new AddPlantCheatCommand(pN, col, row);
	}
	
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		if (errorMessage != null) { //si hay mensaje de error
			gv.showError(errorMessage);//lo enseña y termina
			return;
		}
		
		Position pos = new Position(row, col);
		//si el name esta mal
		if (!gm.validPlantName(this.plantName)) {
			gv.showError(Messages.INVALID_GAME_OBJECT);
		}
		//si casilla mal (fuera del tablero o ocupada)
		else if (!gm.isInsideBoard(pos) || !gm.isEmpty(pos)) {
			gv.showError(Messages.INVALID_POSITION);
		}
		//si se puede añadir planta gratis
		else if (gm.addGameObjectForFree(plantName, pos)) {
			gm.update();
            gv.showGame();
		}
		//si no se pudo añadir 
		else {
			gv.showError(Messages.INVALID_POSITION);
		}
	}
}
