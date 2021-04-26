package game.effects;

import java.util.ArrayList;
import java.util.Scanner;

import cards.Card;
import cards.monster.MonsterCard;
import cards.spell.SpellCard;
import game.Player;
import game.map.CardMode;
import game.map.FieldElement;
import game.map.MonsterMode;
import game.map.PlayField;

public class SpellEffects {
	//TODO: Ausspielvorausetzungen festlegen
	public static void activateEffect(SpellCard sc, PlayField pf, int index) {
		switch (sc.getName()) {
		case "Topf der Gier":
			pf.getPlayer().drawCard();
			pf.getPlayer().drawCard();
			break;
		case "Dian Keto, Meisterheiler":
			pf.getPlayer().setLifePoints(pf.getPlayer().getLifePoints() + 1000);
			break;
		case "Funken":
			Player opponent = pf.getGame().getNotActivePlayer();
			opponent.setLifePoints(opponent.getLifePoints() - 200);
			break;
		case "Rote Medizin":
			pf.getPlayer().setLifePoints(pf.getPlayer().getLifePoints() + 500);
			break;
		case "Wiedergeburt":
			monsterReborn(pf);
			break;
		case "Schwarzes Loch":
			pf.destroyAllMonster();
			pf.getGame().getNotActivePlayer().getPlayField().destroyAllMonster();
			break;
		case "Raigeki":
			pf.getGame().getNotActivePlayer().getPlayField().destroyAllMonster();
			break;
		case "Anmutige Barmherzigkeit":
			pf.getPlayer().drawCard();
			pf.getPlayer().drawCard();
			pf.getPlayer().drawCard();
			pf.getGame().checkTooMuchCards(pf.getPlayer().getHand().size() - 2);
			break;
		case "Fluchzerstörer":
			deSpell(pf, index);
			break;
		default:
			break;
		}
	}

	private static void deSpell(PlayField pf, int index) {
		PlayField opponentField = pf.getGame().getNotActivePlayer().getPlayField();
		System.out.println("You can destroy a Spell-Card from your or your Opponents playfield.");
		if(pf.getSpellAndTrapCount() > 1) {
			System.out.println("Your side: ");
			for(int i = 0; i < 6; i++) {
				if(i < 5 && !pf.getFieldElement(1, i).isEmpty()) {
					String cardName = pf.getCardAt(1, i).getName();
					if(!(cardName.equals("Fluchzerstörer") && pf.getFieldElement(1, i).getCardMode() == CardMode.FACE_UP)) {
						System.out.println(i + ": " + cardName);
					}
				}
				else if(i == 5){
					if(pf.hasFieldSpell()) {
						System.out.println(i+": " + pf.getFieldSpell().getName());
					}
				}
			}
		}
		if(opponentField.getSpellAndTrapCount() > 0) {
			System.out.println("Opponents side: ");
			for(int i = 0; i < 6; i++) {
				if(i < 5 && !opponentField.getFieldElement(1, i).isEmpty()) {
					System.out.println(i + ": " + opponentField.getCardAt(1, i).getName());
				}
				else if(i == 5){
					if(opponentField.hasFieldSpell()) {
						System.out.println(i+": " + opponentField.getFieldSpell().getName());
					}
				}
			}
		}
		System.out.println("Do you want to destroy one of your(0) Spell-Cards or from your Opponent(1)?");
		Scanner sc = new Scanner(System.in);
		int playerChoice;
		if(pf.getSpellAndTrapCount() > 1 || opponentField.getSpellAndTrapCount() > 0) {
			if(pf.getSpellAndTrapCount() <= 1) {
				playerChoice = 1;
				System.out.println("Only your opponent has Spell-Cards.");
			}else if(opponentField.getSpellAndTrapCount() == 0) {
				playerChoice = 0;
				System.out.println("Only you have Spell-Cards.");
			}else {
				playerChoice = sc.nextInt();
			}
			System.out.println("Which Spell-Card do you want to destroy?");
			int cardChoice;
			if(playerChoice == 0 && pf.getSpellAndTrapCount() == 2) {
				System.out.println("You only have one Spell-Card.");
				cardChoice = pf.getOnlySpellOrTrapIndex(index);
			}else if(playerChoice == 1 && opponentField.getSpellAndTrapCount() == 1) {
				System.out.println("Your opponent only has one Spell-Card.");
				cardChoice = opponentField.getOnlySpellOrTrapIndex();
			}else {
				cardChoice = sc.nextInt();
			}
			Card c = null;
			if(playerChoice == 0) {
				if(cardChoice == 5) {
					pf.removeFieldSpell();
				}else {
					c = pf.getCardAt(1, cardChoice);
					if(c instanceof SpellCard) {
						pf.sendCardFromFieldToGrave(pf.getSpellAndTrapField(), cardChoice);
					}
				}
			}else {
				if(cardChoice == 5) {
					opponentField.removeFieldSpell();
				}else {
					c = opponentField.getCardAt(1, cardChoice);
					if(c instanceof SpellCard) {
						opponentField.sendCardFromFieldToGrave(opponentField.getSpellAndTrapField(), cardChoice);
					}
				}
			}
		}

	}

	private static void monsterReborn(PlayField pf) {
		if(pf.getGame().getNotActivePlayer().getPlayField().hasGraveMonster() || pf.hasGraveMonster()) {
			if(pf.hasFreeMonsterSpace()) {
				System.out.println("You can summon a Monster from your or your Opponents graveyard.");
				ArrayList<MonsterCard> myGraveMonster = new ArrayList<>();
				ArrayList<MonsterCard> opponentGraveMonster = new ArrayList<>();
				for(Card c : pf.getGraveyard()) {
					if(c instanceof MonsterCard) {
						myGraveMonster.add((MonsterCard)c);
					}
				}
				for(Card c : pf.getGame().getNotActivePlayer().getPlayField().getGraveyard()) {
					if(c instanceof MonsterCard) {
						opponentGraveMonster.add((MonsterCard)c);
					}
				}
				if(myGraveMonster.size() > 0) {
					System.out.println("Your Grave:");
					int i = 0;
					for(MonsterCard mc : myGraveMonster) {
						System.out.println("Nr. " + i + ": " + mc);
						i++;
					}
				}
				if(opponentGraveMonster.size() > 0) {
					int i = 0;
					System.out.println("Opponents Grave:");
					for(MonsterCard mc : opponentGraveMonster) {
						System.out.println("Nr. " + i + ": " + mc);
						i++;
					}
				}
				Scanner sc = new Scanner(System.in);
				System.out.println("Do you want your(0) Card or from your opponent(1)?");
				int playerChoice;
				if(myGraveMonster.size() == 0) {
					System.out.println("Only your opponent got Monster at the graveyard.");
					playerChoice = 1;
				}else if(opponentGraveMonster.size() == 0) {
					System.out.println("Only you have Monster at the graveyard.");
					playerChoice = 0;
				}else {
					playerChoice = sc.nextInt();
				}
				System.out.println("Which Card do you want?");
				int cardChoice;
				if(playerChoice == 1 && opponentGraveMonster.size() == 1) {
					System.out.println("Your opponent got only one Monster at the graveyard.");
					cardChoice = 0;
				}else if(playerChoice == 0 && myGraveMonster.size() == 1) {
					System.out.println("You have only one Monster at the graveyard.");
					cardChoice = 0;
				}else {
					cardChoice = sc.nextInt();
				}
				System.out.println("Do you want to play the Monster in ATK(0) or DEF(1) mode?");
				int monsterMode = sc.nextInt();
				MonsterMode mm = MonsterMode.DEFENSE;
				if(monsterMode == 0) {
					mm = MonsterMode.ATTACK;
				}
				MonsterCard chosenMonster = null;
				int index = pf.getFreeIndex(pf.getMonsterField());
				if(playerChoice == 0) {
					chosenMonster = myGraveMonster.get(cardChoice);
					pf.getMonsterField()[index] = new FieldElement((Card)chosenMonster, pf.getPlayer(), mm, CardMode.FACE_UP, false);
					pf.getGraveyard().remove(chosenMonster);
				}else {
					chosenMonster = opponentGraveMonster.get(cardChoice);
					pf.getMonsterField()[index] = new FieldElement((Card)chosenMonster, pf.getGame().getNotActivePlayer(), mm, CardMode.FACE_UP, false);
					pf.getGame().getNotActivePlayer().getPlayField().getGraveyard().remove(chosenMonster);
				}
			}
		}
	}



	/*
		Yami (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Unterweltler und Hexer um 200 Punkte. Verringert außerdem ATK und DEF aller Monster vom Type Fee um 200.)
		Schwert der dunklen Zerstörung (type: AUSRÜSTUNG, text: Ein FINSTERNIS Monster, das mit dieser Karte ausgerüstet wird, erhöht seine ATK um 400 Punkte und verringert seine DEF um 200 Punkte.)
		Sogen (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Ungeheuer-Krieger und Krieger um 200 Punkte.)
		Umi (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Fisch, Seeschlange, Donner und Aqua um 200 Punkte. Verringert außerdem ATK und DEF aller Monster vom Typ Maschine und Pyro um 200 Punkte.)
		Berg (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Drache, Gefluegeltes-Ungeheuer und Donner um 200 Punkte.)
		Fusion (type: NORMAL, text: Fügt 2 oder mehr Fusionsmaterial-Monster zu einem neuen Fusionsmonster zusammen.)
		Überläufer (type: NORMAL, text: Wähle ein Monster, das dein Gegner kontrolliert (unabhängig von der Position). Kontrolliere das Monster bis zum Ende deines Spielzugs.)
	 */
}
