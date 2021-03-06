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
		handCards.add(deck.drawCard());
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
	
	public String toString() {
		return name+" hat " + wins + " von " + totalGames + " Duellen gewonnen.";
	}
	
}
