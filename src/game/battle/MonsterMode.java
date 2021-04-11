package game.battle;

public enum MonsterMode {
	ATTACK, DEFENSE;
	
	public MonsterMode changeMode() {
		if(this == ATTACK) {
			return DEFENSE;
		}
		return ATTACK;
	}
}
