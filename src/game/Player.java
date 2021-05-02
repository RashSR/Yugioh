package game;

import java.util.ArrayList;

import cards.Card;
import cards.Deck;
import game.map.PlayField;

public class Player {
	private String name;
	private int lifePoints;
	private PlayField playfield;
	private Deck deck;
	private int totalGames;
	private int wins;
	private int summonCount = 0;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public ArrayList<Card> getHand(){
		return this.handCards;
	}

	public void dropHandCard(int index) {
		if(index < handCards.size() ) {
			this.handCards.remove(index);
		}
	}

	public Card getHandCardAt(int index) {
		if(index < handCards.size() ) {
			return this.handCards.get(index);
		}
		System.out.println("You dont have that much HandCards");
		return null;
	}

	public int getSummonCount() {
		return summonCount;
	}

	public void setSummonCount(int summonCount) {
		this.summonCount = summonCount;
	}

	public PlayField getPlayField() {
		return this.playfield;
	}

	public void setPlayField(PlayField playfield) {
		this.playfield = playfield;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		if(lifePoints < 0) {
			this.lifePoints = 0;
		}else {
			this.lifePoints = lifePoints;
		}
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

	public String getName() {
		return this.name;
	}

	public void showCards() {
		System.out.println("------------------------------------");
		System.out.println(name + " Hand:");
		int i = 0;
		for(Card c : handCards) {
			System.out.println(i + ": " + c);
			i++;
		}
		System.out.println("------------------------------------");
	}

	public String toString() {
		return name+" hat " + wins + " von " + totalGames + " Duellen gewonnen.";
	}

}
