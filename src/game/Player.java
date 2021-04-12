package game;

import java.util.ArrayList;

import cards.Card;
import cards.Deck;

public class Player {
	private String name;
	private int lifePoints;
	private Deck deck;
	private int totalGames;
	private int wins;

	private ArrayList<Card> handCards = new ArrayList<>(); 

	public Player(String name, int totalGames, int wins, Deck deck) {
		this.name = name;
		this.totalGames = totalGames;
		this.wins = wins;
		this.deck = deck;
	}

	public Player(String name, int totalGames, int wins) {
		this.name = name;
		this.totalGames = totalGames;
		this.wins = wins;
	}

	public void drawCard() {
		if(deck.getDeckCount() > 0) {
			handCards.add(deck.drawCard());
		}else {
			lifePoints = 0;
		}
	}

	public boolean equals(Player other) {
		if(name.equals(other.getName())) {
			return true;
		}
		return false;
	}

	public ArrayList<Card> getHand(){
		return this.handCards;
	}
	
	public void dropHandCard(int index) {
		if(index < handCards.size() ) {
			this.handCards.remove(getHandCardAt(index));
		}
	}
	
	public Card getHandCardAt(int index) {
		if(index < handCards.size() ) {
			return this.handCards.get(index);
		}
		System.out.println("You dont have that much HandCards");
		return null;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setName() {

	}

	public String getName() {
		return this.name;
	}

	public void showCards() {
		System.out.println("------------------------------------");
		System.out.println("My Hand:");
		for(Card c : handCards) {
			System.out.println(c);
		}
		System.out.println("------------------------------------");
	}

	public String toString() {
		return name+" hat " + wins + " von " + totalGames + " Duellen gewonnen.";
	}

}
