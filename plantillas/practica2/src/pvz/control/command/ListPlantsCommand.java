package pvz.control.command;


import java.util.List;
import pvz.logic.GameModel;
import pvz.view.GameView; 
import pvz.view.Messages;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.factories.PlantFactory;

public class ListPlantsCommand extends Command{
	private String errorMessage;
	
	public ListPlantsCommand() {
		super (//invoca a la constructora de Command (superclase)
			Messages.COMMAND_LIST_PLANTS_NAME,
			Messages.COMMAND_LIST_PLANTS_SHORTCUT,
			Messages.COMMAND_LIST_PLANTS_DETAILS,
			Messages.COMMAND_LIST_PLANTS_HELP
		);
	}
	
	private ListPlantsCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	@Override
	public Command create(String[] c) {
		// comprobamos que la cadena String[] c no tenga más que un elemento, el propio comando
		if (c.length != 1) {
			return new ListPlantsCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		return this;
	}
	

	@Override
	public void execute(GameModel gm, GameView gv) {//muestra descripción de las plantas pero no avanza ciclo
		if (errorMessage != null) {
			gv.showError(errorMessage);
			return;
		}
		
		List<Plant> plants = PlantFactory.getAvailablePlants();
		
		StringBuilder plantDescriptions = new StringBuilder();
		
		for (int i = 0; i < plants.size(); i++) {
			if (i > 0) {
				plantDescriptions.append(Messages.LINE_SEPARATOR);
			}
			
			plantDescriptions.append(plants.get(i).getDescription());
		}
		
		plantDescriptions.append(Messages.LINE_SEPARATOR);
		
		gv.showMessage(plantDescriptions.toString());
    }	
	
}

