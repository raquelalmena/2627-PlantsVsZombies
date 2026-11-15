package pvz.control.command;

import java.util.Arrays;
import java.util.List;
import pvz.logic.GameModel;
import pvz.view.GameView;

public abstract class Command {
	
	//tienen la info de cada comando
	protected final String name;
	protected final String shortcut;
	protected final String details;
	protected final String help;

	//lista de todos los commadns
	protected static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
	    new AddPlantCommand(),
	    new AddZombieCommand(),
	    new AddPlantCheatCommand(),
	    new CatchCommand(),
	    new ListPlantsCommand(),
	    new ListZombiesCommand(),
	    new ResetCommand(),
	    new HelpCommand(),
	    new ExitCommand(),
	    new UpdateCommand()

	  );
	  
	  //constructora del comando
	  public Command(String name, String shortcut, String details, String help) { 
		  this.name = name; 
		  this.shortcut = shortcut; 
		  this.details = details; 
		  this.help = help; 
	  }
	  
	  
	  //devuelve el comando segun lo que escribe el player
	  public static Command parse(String[] c) {	
		  Command com = null;

		  if (c == null || c.length == 0 || c[0].isEmpty())  
			  com = new UpdateCommand();//por si entrada vacía o nula (enter por ej)
		  else {
			  int i = 0;
			  //recorre todos los comandos hasta que encuentre el que coincide
			  while (com == null && i < AVAILABLE_COMMANDS.size()) {
				  Command command = AVAILABLE_COMMANDS.get(i);
				
				  if (command.matchCommand(c[0])) {//si la primera palabra (commandWords[0])coincide con el nombre del comando i
					  com = command.create(c); //com == null es falsa y para el bucle
				  }
				  i++;
			  }
		  }
		  
		  if (com == null) {//ha recorrido la lista pero no habia concidencias
			  com = new UnknownCommand();
		  }
		  
		  return  com;
	  }
	   
	  //comprueba si la palabra escrita por el player coincide con el name/shortcut del comand
	  public boolean matchCommand(String c) {
		  boolean match = false;
		  if (c != null) match = name.equalsIgnoreCase(c) || shortcut.equalsIgnoreCase(c);//no hacen falta getters
		  return match;
	  }
	  
	  public Command create(String[] c) {//para no tener que implementar create en todos los comandos
		  return this;
	  }
	  
	  //cada comando sí que implementa su propio execute
	  public abstract void execute(GameModel gm, GameView gv);//ejecuta la acción del comando
	  
	  public String getDetails() {
		  return details;
	  }
	  
	  public String getHelp() {
		  return help;
	  }
}
