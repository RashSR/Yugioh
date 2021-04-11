package game.battle;

import cards.Card;
import cards.monster.MonsterCard;
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
		setupGame();
	}
	
	private void setupGame() {
		this.player1.setLifePoints(START_LIFE_POINTS);
		this.player2.setLifePoints(START_LIFE_POINTS);
		activePlayer = player1;
		for(int i = 0; i < 5; i++) {
			player1.drawCard();
			player2.drawCard();
		}
		playField1.print();
		startGame();
	}
	
	private void startGame() {
		while(player1.getLifePoints() > 0 || player2.getLifePoints() > 0) {
			nextPhase();
			player1.showCards();
			player2.showCards();
			break;
		}
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
	
	public void changeCardState(PlayField pf, int row, int col) {
		pf.changeState(row, col);
	}
	
	public void attack(PlayField attPf, int attCol, PlayField defPf, int defCol) {
		FieldElement attElement = attPf.getFieldElement(0, attCol);
		FieldElement defElement = defPf.getFieldElement(0, defCol);
		MonsterCard attacker = (MonsterCard) attElement.getCard();
		MonsterCard defender = (MonsterCard) defElement.getCard();
		int attATK = attacker.getAtk();
		int defATK = defender.getAtk();
		int defDEF = defender.getDef();
		if(defElement.getMonsterMode() == MonsterMode.DEFENSE) {
			if(attATK == defATK) {
				sendCardToGrave(attacker);
				sendCardToGrave(defender);
			}else if(attATK < defATK) {
				sendCardToGrave(attacker);
				reduceLifePoints(activePlayer, defATK - attATK);
			}else if(attATK > defATK) {
				sendCardToGrave(defender);
				reduceLifePoints(getNotActivePlayer(), attATK - defATK);
			}
		}else if(defElement.getMonsterMode() == MonsterMode.ATTACK) {
			if(attATK > defDEF) {
				sendCardToGrave(defender);
			}else if(attATK < defDEF) {
				reduceLifePoints(activePlayer, defDEF-attATK);
			}else if(attATK == defDEF) {
				System.out.println("Nothing happen.");
			}	
		}
	}
	
	private void sendCardToGrave(Card c) {
		//TODO: send Cards to the Graveyard
	}
	
	private void reduceLifePoints(Player p, int amount) {
		p.setLifePoints(p.getLifePoints()-amount);
	}
	
	private Player getNotActivePlayer() {
		if(activePlayer.equals(player1)) {
			return player2;
		}
		return player1;
	}
	
}
