package pvz.control.command;

import pvz.logic.GameModel; 
import pvz.view.GameView; 
import pvz.view.Messages;

public class HelpCommand extends Command {
	private String errorMessage;
	
	public HelpCommand() {
		super (
			Messages.COMMAND_HELP_NAME,
			Messages.COMMAND_HELP_SHORTCUT,
			Messages.COMMAND_HELP_DETAILS,
			Messages.COMMAND_HELP_HELP
		);
	}
	
	//constructora para cuando hayerror
	private HelpCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	@Override
	public Command create(String[] c) {
		//help tmpco tiene parametros, solo puede ser 1
	    if (c.length != 1) {
	        return new HelpCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	    }
	    return this;
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) { 
		if (errorMessage != null) {//si hay error lo enseña y termina
			gv.showError(errorMessage);
			return;
		}
		
		//stringbuilder par air guardando todo el texto de la aydua
		StringBuilder helpText = new StringBuilder();
        helpText.append(Messages.HELP_AVAILABLE_COMMANDS);//añade la primera línea. 'título'
        
        //recorremos rodos los comandos y añade su descripicon y ayuda
        for (int i = 0; i < AVAILABLE_COMMANDS.size(); i++) {
        	      	
        	Command command = AVAILABLE_COMMANDS.get(i);
        	
        	helpText.append(Messages.LINE_SEPARATOR);
        	helpText.append(command.getDetails());
        	helpText.append(Messages.HELP_DETAILS_COMMAND_HELP_SEPARATOR);
        	helpText.append(command.getHelp());
        }
        
        gv.showMessage(helpText.toString());//enseña toda la ayuda
	}

}
