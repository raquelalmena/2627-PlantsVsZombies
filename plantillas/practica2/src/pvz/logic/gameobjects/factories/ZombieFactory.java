package pvz.logic.gameobjects.factories;
import pvz.logic.gameobjects.*;
import utils.*;
import pvz.logic.GameWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

//factory que crea zombies y guarda los disponibles

public class ZombieFactory {
	//lista con plantilla de cada zombi disponible
	private static final List<Zombie> AVAILABLE_ZOMBIES = Arrays.asList(
		new CommonZombie(),
		new BucketHeadZombie(),
		new SportyZombie(),
		new ExplosiveZombie()
	);
	
	//devuelve un nuevo zombie segun el nombre/simbolo q escribe el player
	public static Zombie spawnZombie(String zombieName, GameWorld game, Position position) {
		boolean found = false;
		int pos = 0;
		Zombie zombie = null;
		
		//recorre los zombies disponibles hasta encontrar el q coincida
		while (!found && pos < AVAILABLE_ZOMBIES.size()) {
			zombie = AVAILABLE_ZOMBIES.get(pos);
			
			if (zombie.getName().equalsIgnoreCase(zombieName) || zombie.getSymbol().equalsIgnoreCase(zombieName)) {
				found = true;
			}
			pos++;
		}
		
		if (!found) {
			zombie = null;
		}
		else {
			zombie = zombie.copy(position, game);//crea copia si lo encuentra como planfactory
		}
		
		return zombie;
	}
	
	//devuelve la lista no modificable de los disponibles para listzombiescommadn
	public static List<Zombie> getAvailableZombies() {
		return Collections.unmodifiableList(AVAILABLE_ZOMBIES);
	}
	
	//comprueba nombre o simbolo del player y mira si coincide
	public static boolean validZombieName(String zombieName) {
		if (zombieName == null) {
			return false;
		}
		
		Zombie zombie = null;
		int pos = 0;
		boolean found = false;
		
		while (!found && pos < AVAILABLE_ZOMBIES.size()) {
			zombie = AVAILABLE_ZOMBIES.get(pos);
			if (zombie.getName().equalsIgnoreCase(zombieName) || zombie.getSymbol().equalsIgnoreCase(zombieName)) {
				found = true;
			}
			pos++;
		}
		
		return found;
	}
}
