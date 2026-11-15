package pvz.control.command;
import java.util.Scanner;


import utils.Position;
import pvz.logic.*;
import pvz.view.*;

public class CatchCommand extends Command{
	private int col;
	private int row;
	
	private String errorMessage;
	
	public CatchCommand() {
		super(
				Messages.COMMAND_CATCH_NAME,
				Messages.COMMAND_CATCH_SHORTCUT,
				Messages.COMMAND_CATCH_DETAILS,
				Messages.COMMAND_CATCH_HELP
		);
	}
	
	private CatchCommand(int c, int r) {
		this();
		this.col = c;
		this.row = r;
	}
	
	private CatchCommand(String errorMessage) {
		this();
		this.errorMessage = errorMessage;
	}
	
	
	@Override
	public Command create(String[] c) {
		// comprobamos que String[] c tenga 3 elementos, los que necesita CatchCommand
		if (c.length < 3) {
			return new CatchCommand(Messages.COMMAND_PARAMETERS_MISSING);
		}
		
		if (c.length > 3) {
			return new CatchCommand(Messages.INVALID_COMMAND);
		}
		
		// leemos la columna y la fila de String[] c
		Scanner colScanner = new Scanner(c[1]);
		Scanner rowScanner = new Scanner(c[2]);
		
		// comprobamos que ambas sean números enteros
		if (!colScanner.hasNextInt() || !rowScanner.hasNextInt()) {
			colScanner.close();
			rowScanner.close();
			return new CatchCommand(Messages.INVALID_COMMAND);
		}
		
		// creamos variables y almacenamos los valores enteros
		int new_col = colScanner.nextInt();
		int new_row = rowScanner.nextInt();
		
		colScanner.close();
		rowScanner.close();
		
		return new CatchCommand(new_col, new_row);
	}
	
	@Override
	public void execute(GameModel gm, GameView gv) {
		if (errorMessage != null) {
			gv.showError(errorMessage);
			return;
		}
		
		Position pos = new Position(row, col);
		
		if (!gm.isInsideBoard(pos)) {
			gv.showError(Messages.INVALID_POSITION);
		}
		else if (!gm.catchObject(pos)) {
			gv.showError(String.format(Messages.NO_CATCHABLE_IN_POSITION, col, row));
		}
		else {
			gv.showGame();
		}
	}
}
