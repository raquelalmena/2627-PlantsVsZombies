package pvz.control.command;

import pvz.logic.GameModel; 
import pvz.view.GameView;
import pvz.view.Messages;

public class ExitCommand extends Command{
	private String errorMessage;
	
	public ExitCommand() {
		super (
			Messages.COMMAND_EXIT_NAME,
			Messages.COMMAND_EXIT_SHORTCUT,
			Messages.COMMAND_EXIT_DETAILS,
			Messages.COMMAND_EXIT_HELP
		);
	}
	
	private ExitCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	@Override
	public Command create(String[] c) {
		if (c.length != 1) {
			return new ExitCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		return this;
	}

	@Override
	public void execute(GameModel gm, GameView gv) {//meto gv tb. polimorfismo
		if (errorMessage != null) {
			gv.showError(errorMessage);
			return;
		}
		gm.quit();//jugador sale
	}
}
