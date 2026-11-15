package pvz.control.command;
import java.util.Scanner;
import pvz.logic.GameModel;
import pvz.view.GameView; 
import pvz.view.Messages;
import utils.Position;

public class AddPlantCommand extends Command{//extends por la herencia de la superclase
	private String plantName;
	private int col;
	private int row;
	
	private String errorMessage;
	
	//constructora sin parámetros . se usa para registrarlo en AVAILABlE_COMANDS
	public AddPlantCommand() {
		super (//invoca a la constructora de Command (superclase)
				Messages.COMMAND_ADD_PLANT_NAME,
				Messages.COMMAND_ADD_PLANT_SHORTCUT,
				Messages.COMMAND_ADD_PLANT_DETAILS,
				Messages.COMMAND_ADD_PLANT_HELP
		);
	}
	
	//constructora con parámetros
	private AddPlantCommand(String pN, int c, int r) {
		// llamamos primero a la constructora sin parámetros para inicializar los atributos de Command
		this();
		this.plantName = pN;
		this.col = c;
		this.row = r;
	}
	
	private AddPlantCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	//propuesta create sin scanner. 
	//pq enunciado: *En el caso de que los argumentos introducidos por el usuario no sean válidos para el comando, 
	//el método* *create(String[])* *devolverá un comando con un mensaje de error*

	@Override //pq sobreescribimos el método create heredado de Command
	public Command create(String[] c) { 
		
		if (c.length < 4) {
			return new AddPlantCommand(Messages.COMMAND_PARAMETERS_MISSING);
		}
		
		if (c.length > 4) {
			return new AddPlantCommand(Messages.INVALID_COMMAND);
		}
		
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
		else {
			if (gm.addGameObject(this.plantName, pos)) {
                gm.update();
                gv.showGame();
            } 
			else  {
				gv.showError(Messages.NOT_ENOUGH_COINS);
			}
		}	
	}
}