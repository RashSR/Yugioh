package game.battle;

public enum MonsterMode {
	ATTACK, DEFENSE;
	
	public MonsterMode changeMode() {
		if(this == ATTACK) {
			return DEFENSE;
		}
		return ATTACK;
	}
	
	public String toString() {
		String mode = "DEF";
		if(this == ATTACK) {
			mode = "ATK";
		}
		return mode;
	}
	
}
