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
		//coge todos los zombies q se pueden usar
		List<Zombie> zombies = ZombieFactory.getAvailableZombies();
		
		//va guardando con stringbuilder la descripcion de cada uno
		StringBuilder zombiesDescriptions = new StringBuilder();
		
		for (int i = 0; i < zombies.size(); i++) {
			if (i > 0) {
				zombiesDescriptions.append(Messages.LINE_SEPARATOR);
			}
			
			zombiesDescriptions.append(zombies.get(i).getDescription());
		}
		
		zombiesDescriptions.append(Messages.LINE_SEPARATOR);
		
		//enseña todas las descripciones
		gv.showMessage(zombiesDescriptions.toString());
	}
}
