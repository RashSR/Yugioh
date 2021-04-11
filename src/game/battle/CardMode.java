package game.battle;

public enum CardMode {
	FACE_UP, FACE_DOWN;
	
	public CardMode changeMode() {
		if(this == FACE_UP) {
			return FACE_DOWN;
		}
		return FACE_UP;
	}
}
