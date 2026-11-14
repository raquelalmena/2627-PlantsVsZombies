package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public class UnknownCommand extends Command {
	public UnknownCommand() {
		super(null, null, null, null);
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		gv.showError(Messages.UNKNOWN_COMMAND);
	}
}
