package game.map;

public enum CardMode {
	FACE_UP, FACE_DOWN;
	
	public CardMode changeMode() {
		if(this == FACE_UP) {
			return FACE_DOWN;
		}
		return FACE_UP;
	}
	
	public String toString() {
		String mode = "F_UP";
		if(this == FACE_DOWN) {
			mode = "F_DOWN";
		}
		return mode;
	}
}
