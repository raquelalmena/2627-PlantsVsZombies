package pvz.control.command;
import pvz.logic.GameModel;
import pvz.logic.gameobjects.factories.ZombieFactory;
import pvz.logic.gameobjects.Zombie;
import java.util.List;
import pvz.view.*;

public class ListZombiesCommand extends Command{
	private String errorMessage;
	
	public ListZombiesCommand() {
		super(
			Messages.COMMAND_LIST_ZOMBIES_NAME,
			Messages.COMMAND_LIST_ZOMBIES_SHORTCUT,
			Messages.COMMAND_LIST_ZOMBIES_DETAILS,
			Messages.COMMAND_LIST_ZOMBIES_HELP
		);
	}
	
	private ListZombiesCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	@Override
	public Command create(String[] c) {
		// comprobamos que la cadena String[] c no tenga más que un elemento, el propio comando
		if (c.length != 1) {
			return new ListZombiesCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		return this;
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) {//muestra descripción de las plantas pero no avanza ciclo
		if (errorMessage != null) {
			gv.showError(errorMessage);
			return;
		}
		
		List<Zombie> zombies = ZombieFactory.getAvailableZombies();
		
		String zombiesDescriptions = "";
		
		for (int i = 0; i < zombies.size(); i++) {
			if (!zombiesDescriptions.isEmpty()) {
				zombiesDescriptions += Messages.LINE_SEPARATOR;
			}
			
			zombiesDescriptions += zombies.get(i).getDescription();
		}
		
	zombiesDescriptions += Messages.LINE_SEPARATOR;
		
		gv.showMessage(zombiesDescriptions);
	}
}
