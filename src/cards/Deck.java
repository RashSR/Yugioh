package cards;

import java.util.ArrayList;
import java.util.Collections;

import cards.monster.fusion.FusionMonster;

public class Deck {
	private String pre = "[Deck] ";
	private static final int MIN_CARD_LIMIT = 5;
	private static final int MAX_CARD_LIMIT = 60;
	private ArrayList<Card> cards;
	private ArrayList<FusionMonster> fusionMonsters;
	private boolean isReady;

	public Deck() {
		isReady = false;
		cards = new ArrayList<>();
		fusionMonsters = new ArrayList<>();
	}

	public Card drawCard() {
		Card draw = cards.get(0);
		cards.remove(0);
		return draw;
	}

	public void addToDeck(Card card) {
		if(card != null) {
			if(card instanceof FusionMonster) {
				if(canInsert(card)) {
					addFusionMonsterToDeck((FusionMonster) card);
				}else {
					System.out.println("You already have " + card.getName() + " 3 times in your Fusion Deck!");
				}
			}else {
				if(cards.size() < MAX_CARD_LIMIT) {
					if(canInsert(card)) {
						cards.add(card);
					}else {
						System.out.println("You already have " + card.getName() + " 3 times in your Deck!");
					}
				}else {
					System.out.println(pre + "You have already " + MAX_CARD_LIMIT + " Cards in your Deck.");
				}	
			}
		}else {
			System.out.println("Your Card is null.");
		}
	}

	private void addFusionMonsterToDeck(FusionMonster fm) {
		fusionMonsters.add(fm);
	}

	private boolean canInsert(Card card) {
		int amount = 0;
		String cardName = card.getName();
		if(card instanceof FusionMonster) {
			for(FusionMonster fm : fusionMonsters) {
				if(fm.getName().equals(cardName)) {
					amount++;
				}
			}
			if(amount < 3) {
				return true;
			}
		}else {
			for(Card c : cards) {
				if(c.getName().equals(cardName)) {
					amount++;
				}
			}
			if(amount < 3) {
				return true;
			}
		}
		return false;
	}

	public void finishDeck() {
		if(cards.size() < MIN_CARD_LIMIT) {
			System.out.println(pre + "You have too few Cards in your Deck!");
		}else {
			System.out.println(pre + "Your Deck is ready!");
			isReady = true;
		}
	}

	public ArrayList<FusionMonster> getFusionMonster(){
		return this.fusionMonsters;
	}

	public int getDeckCount() {
		return this.cards.size();
	}

	public boolean isReady() {
		return isReady;
	}

	public void shuffleDeck() {
		Collections.shuffle(cards);
	}

	public void printDeck() {
		for(Card c : cards) {
			System.out.println(c);
		}
		System.out.println("This deck have " + cards.size() + " Cards in it and " + fusionMonsters.size() + " Fusion Monster.");
		for(FusionMonster fm : fusionMonsters) {
			System.out.println(fm);
		}
	}

}
