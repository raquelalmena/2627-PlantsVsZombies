package pvz.control;

import pvz.control.command.Command;
import pvz.logic.Game;
import pvz.view.GamePrinter;
import pvz.logic.GameModel;
import pvz.view.GameView;


public class Controller {
	
	
	//no cambian durante toda la aprtida
	private final GameModel game;
	private final GameView view;

	//constructora. une game (modelo)con la  gameprinter(vista)
	public Controller(Game game) {
		this.game = game;
		this.view = new GamePrinter(game);
	}

	/**
	 * Runs the game logic.
	 */
	public void run() {
		view.showGame(); //la vista muestra el estado inicial del tablero y del juego
		
		while (!game.hasGameFinished()) { //se ejecuta mientras la partida no termine			
			//tiene que leer al player
			String[] c = view.getPrompt();//declarado en game view, lee una alínea y la devuelve separada por espacios
			Command command = Command.parse(c); //ahora es el parse() quien busca el comando que coincida con el del player
										
			command.execute(game, view);//si es válido, se ejecuta
			
		}
		view.showEndMessage();//muestra Game over y el motivo
	}
}