package game.battle;

import java.util.ArrayList;

import cards.Card;
import cards.Deck;
import cards.monster.MonsterCard;
import cards.monster.fusion.FusionMonster;
import cards.spell.SpellCard;
import game.Player;

public class PlayField {
	private Player player;
	private SpellCard fieldSpell;
	private Deck deck;
	private ArrayList<Card> graveyard = new ArrayList<>();
	private MonsterCard[] monsterField = new MonsterCard[5];
	private Card[] spellAndTrapField = new Card[5];
	private ArrayList<FusionMonster> fusionMonsters;
	
	public PlayField(Player player) {
		this.player = player;
		this.deck = player.getDeck();
		this.fusionMonsters = deck.getFusionMonster();
		this.deck.shuffleDeck();
	}
	
	public void playCard(Card card) {
		int index;
		if(card instanceof MonsterCard) {
			index = getFreeIndex(monsterField);
			if(index > -1) {
				monsterField[index] = (MonsterCard)card;
			}
		}else {
			index = getFreeIndex(spellAndTrapField);
			if(index > -1) {
				spellAndTrapField[index] = card;
			}
		}
	}
	
	public FusionMonster getFusionMonsterByName(String name) {
		for(FusionMonster fm : fusionMonsters) {
			if(name.equals(fm.getName())) {
				fusionMonsters.remove(fm);
				return fm;
			}
		}
		return null;
	}
	
	private int getFreeIndex(Card[] arr) {
		for(int i = 0; i < 5; i++) {
			if(arr[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public Card getSpellOrTrapCardAt(int index) {
		return spellAndTrapField[index];
	}
	
	public MonsterCard getMonsterAt(int index) {
		return monsterField[index];
	}
	
	public int getDeckCount() {
		return deck.getDeckCount();
	}
	
	public ArrayList<Card> getGraveyard(){
		return this.graveyard;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setFieldSpell(SpellCard fieldSpell){
		this.fieldSpell = fieldSpell;
	}
	
	public void removeFieldSpell() {
		this.fieldSpell = null;
	}
	
	public boolean hasFieldSpell() {
		if(fieldSpell == null) {
			return false;
		}
		return true;
	}
	
	public void print() {
		String s = "";
		s += ("\n_________________        _________________________________________________________________________________        _________________");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|       ,       |        |               |               |               |               |               |        |               |");
		s += ("\n|    __/ \\__    |        |               |               |               |               |               |        |   Friedhof:   |");
		s += ("\n|    \\     /    |        |               |               |               |               |               |        |       " + graveyard.size() + "       |");
		s += ("\n|    /_   _\\    |        |               |               |               |               |               |        |               |");
		s += ("\n|      \\ /      |        |               |               |               |               |               |        |               |");
		s += ("\n|       '       |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n-----------------        |-------------------------------------------------------------------------------|        -----------------");
		s += ("\n_________________        |_______________________________________________________________________________|        |_______________|");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|   Fusions-    |        |               |               |               |               |               |        |               |");
		s += ("\n|   monster:    |        |               |               |               |               |               |        |               |");
		s += ("\n|      " + fusionMonsters.size() + "        |        |               |               |               |               |               |        |     Deck:     |");
		s += ("\n|               |        |               |               |               |               |               |        |      " + deck.getDeckCount() + "       |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n|               |        |               |               |               |               |               |        |               |");
		s += ("\n-----------------        ---------------------------------------------------------------------------------        -----------------");
		System.out.println(s);
		
	}
}
