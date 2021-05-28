package game.battle;

public enum PlayPhase {
	/*
	 * Enumeration for the PlayPhases which are possible in a Yu-Gi-Oh Game
	 */
	DRAW, STANDBY, MAIN_1, BATTLE, MAIN_2, END; 

	/*
	 * Return the next PlayPhase from a given PlayPhase
	 */
	public PlayPhase nextPhase() {
		switch (this) {
		case DRAW:
			return STANDBY;
		case STANDBY: 
			return MAIN_1;
		case MAIN_1:
			return BATTLE;
		case BATTLE:
			return MAIN_2;
		case MAIN_2: 
			return END;
		case END:
			return DRAW;
		}
		return null;
	}
	
}
