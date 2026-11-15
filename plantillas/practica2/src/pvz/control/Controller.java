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

	//constructora. asocia un GameModel y un GameView con el parámetro game
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
			//tiene que leer el comando introducido por el player
			String[] c = view.getPrompt();//declarado en game view, lee una alínea y la devuelve separada por espacios
			Command command = Command.parse(c); //ahora es el parse() quien busca el comando que coincida con el del player
										
			command.execute(game, view);//se ejecuta el comando 
			
		}
		view.showEndMessage();//muestra Game over y el motivo
	}
}