package pvz.logic;

import utils.Position;

public interface GameStatus {
	int getCycles();//ciclos actuales para mostrarlos
	int getCoins();//soles disponibles
	int getGeneratedCoins();
	int getCaughtCoins();
	
	int getRemainingZombies();//zombies que quedan por salir
	boolean playerWins(); //si ha eliminado a todos los zombies
	boolean playerQuits(); //sii ha salido con exit
	boolean hasGameFinished();//si partida terminada
	boolean doZombiesReachedTheHouse();
	String positionToString(Position p);
}
