package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

//cuando el comando escrito no existe

public class UnknownCommand extends Command {
	public UnknownCommand() {
		super(null, null, null, null);
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		gv.showError(Messages.UNKNOWN_COMMAND);//enseña error de comando unknown
	}
	
	@Override //siempre devuelve false pq no coincide con nigun comand
	public boolean matchCommand(String c) {
		return false;
	}
}
