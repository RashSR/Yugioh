package game.battle;

import cards.spell.SpellCard;
import cards.spell.SpellType;
import game.Player;

public class Game {
	private Player player1;
	private Player player2;
	private PlayField playField1;
	private PlayField playField2;
	private static final int START_LIFE_POINTS = 8000;
	private PlayPhase activePhase = PlayPhase.END;
	private Player activePlayer;
	
	public Game(PlayField playField1, PlayField playField2) {
		this.player1 = playField1.getPlayer();
		this.player2 = playField2.getPlayer();
		this.playField1 = playField1;
		this.playField2 = playField2;
		this.player1.setLifePoints(START_LIFE_POINTS);
		this.player2.setLifePoints(START_LIFE_POINTS);
		activePlayer = player1;
		for(int i = 0; i < 5; i++) {
			player1.drawCard();
			player2.drawCard();
		}
		playField1.print();
		for(int i = 0; i < 7; i++) {
			nextPhase();
		}
		playField1.print();
		
	}
	
	
	
	private void nextPhase() {
		activePhase = activePhase.nextPhase();
		if(activePhase == PlayPhase.DRAW) {
			if(player1.getName().equals(activePlayer.getName())) {
				activePlayer = player2;
			}else {
				activePlayer = player1;
			}
			activePlayer.drawCard();
		}
	}
	
	public void playFieldSpell(SpellCard fieldSpell, Player player) {
		if(fieldSpell.getSpellType() == SpellType.FELD) {
			if(player.equals(player1)) {
				if(playField2.hasFieldSpell()) {
					playField2.removeFieldSpell();
				}
				playField1.setFieldSpell(fieldSpell);
			}else {
				if(playField1.hasFieldSpell()) {
					playField1.removeFieldSpell();
				}
				playField2.setFieldSpell(fieldSpell);
			}
		}
	}
	
	
}
