package pvz.control.command;

import pvz.logic.GameModel; 
import pvz.view.GameView; 
import pvz.view.Messages;

//avanza 1 ciclo

public class UpdateCommand extends Command {
	private String errorMessage;
	
	public UpdateCommand() {
		super (
				Messages.COMMAND_UPDATE_NAME, 
				Messages.COMMAND_UPDATE_SHORTCUT, 
				Messages.COMMAND_UPDATE_DETAILS, 
				Messages.COMMAND_UPDATE_HELP
		);
	}
	
	private UpdateCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	@Override
	public Command create(String[] c) {
		if (c.length != 1) {//no hay parametros , solo puede ser 1
			return new UpdateCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		return this;
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		if (errorMessage != null) {//si hay error, lo enseña y termina, no avanza ciclo
			gv.showError(errorMessage);
			return;
		}
		
		gm.update();//avanza un ciclo
		gv.showGame();//muestra tablero
	}
}
