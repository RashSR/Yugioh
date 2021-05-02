package game.effects;

import java.util.ArrayList;
import java.util.Scanner;

import cards.Card;
import cards.monster.Attribute;
import cards.monster.MonsterCard;
import cards.spell.SpellCard;
import game.Player;
import game.battle.PlayPhase;
import game.listener.EquipCardListener;
import game.listener.FieldElementListener;
import game.listener.PhaseListener;
import game.map.CardMode;
import game.map.FieldElement;
import game.map.MonsterMode;
import game.map.PlayField;

@SuppressWarnings("resource")
public class SpellEffects{
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
		case "Überläufer":
			changeOfHeartBefore(pf);
			break;
		case "Schwert der dunklen Zerstörung":
			swordOfDarkness(pf, index);
			break;
		default:
			break;
		}
	}

	private static void swordOfDarkness(PlayField pf, int spellIndex) {
		int myDarkMonsterCount = pf.getMonsterWithAttributeCount(Attribute.FINSTERNIS);
		int opDarkMonsterCount = pf.getOpponentField().getMonsterWithAttributeCount(Attribute.FINSTERNIS);
		if(myDarkMonsterCount > 0 || opDarkMonsterCount > 0) {
			System.out.println("Do you want to equip your(0) or your Opponents(1) Monster?");
			pf.listMonsterByAttribute(Attribute.FINSTERNIS);
			pf.getOpponentField().listMonsterByAttribute(Attribute.FINSTERNIS);
			int playerChoice;
			Scanner sc = new Scanner(System.in);
			if(myDarkMonsterCount == 0) {
				System.out.println("Only your Opponent got a Darkness Monster.");
				playerChoice = 1;
			}else if(opDarkMonsterCount == 0) {
				System.out.println("Only you have a Darkness Monster.");
				playerChoice = 0;
			}else {
				playerChoice = sc.nextInt();
			}
			System.out.println("Which Monster do you want to equip?");
			int monsterChoice;
			if(playerChoice == 0 && myDarkMonsterCount == 1) {
				System.out.println("You only have one Darkness Monster.");
				monsterChoice = pf.getOnlyAttributeMonsterIndex(Attribute.FINSTERNIS);
			}else if(playerChoice == 1 && opDarkMonsterCount == 1){
				System.out.println("Your opponent only has one Darkness Monster.");
				monsterChoice = pf.getOpponentField().getOnlyAttributeMonsterIndex(Attribute.FINSTERNIS);
			}else {
				monsterChoice = sc.nextInt();
			}
			if(playerChoice == 0) {
				pf.getMonsterField()[monsterChoice].addToAtkChange(400);
				pf.getMonsterField()[monsterChoice].addToDefChange(-200);
				EquipCardListener eql = new EquipCardListener(pf, pf.getSpellAndTrapField(), spellIndex, pf.getMonsterField(), monsterChoice, 400, -200);
				eql.start();
			}else {
				pf.getOpponentField().getMonsterField()[monsterChoice].addToAtkChange(400);
				pf.getOpponentField().getMonsterField()[monsterChoice].addToDefChange(-200);
				EquipCardListener eql = new EquipCardListener(pf, pf.getSpellAndTrapField(), spellIndex, pf.getOpponentField().getMonsterField(), monsterChoice, 400, -200);
				eql.start();
			}
		}
	}


	private static void changeOfHeartBefore(PlayField pf) {
		PlayField opponentField = pf.getOpponentField();
		if(opponentField.containsMonster() && pf.hasFreeMonsterSpace()) {
			System.out.println("Which Monster do you want from your opponent?");
			int count = 0;
			int choice = -1;
			for(int i = 0; i < 5; i++) {
				String monsterName = "Monster is Face-Down.";
				if(!opponentField.getFieldElement(0, i).isEmpty()) {
					if(opponentField.getFieldElement(0, i).getCardMode() == CardMode.FACE_UP) {
						monsterName = opponentField.getMonsterAt(i).getName();
					}
					System.out.println(i + ": " + monsterName);
					count++;
					choice = i;
				}
			}
			if(count > 1) {
				Scanner sc = new Scanner(System.in);
				choice = sc.nextInt();
			}else {
				System.out.println("Your opponent only has one Monster.");
			}
			FieldElement oppFieldElement = opponentField.getMonsterField()[choice];
			int myIndex = pf.getFreeIndex(pf.getMonsterField());
			pf.getMonsterField()[myIndex] = new FieldElement(oppFieldElement.getCard(), oppFieldElement.getOwner(), oppFieldElement.getMonsterMode(), oppFieldElement.getCardMode(), false);
			opponentField.getMonsterField()[choice] = new FieldElement(opponentField.getPlayer());
			PhaseListener pl = new PhaseListener(pf, PlayPhase.END, myIndex, choice);
			pl.start();
			FieldElementListener fl = new FieldElementListener(pf.getMonsterField(), myIndex, pl);
			fl.start();
		}
	}

	//TODO SpellCard soll nach Tausch immer noch aktiv sein
	public static void changeOfHeartAfter(PlayField pf, int myIndex, int choice) {
		FieldElement fe = pf.getFieldElement(0, myIndex);
		pf.getGame().getActivePlayer().getPlayField().getMonsterField()[choice] = new FieldElement(fe.getCard(), fe.getOwner(), fe.getMonsterMode(), fe.getCardMode(), false);
		pf.getMonsterField()[myIndex] = new FieldElement(pf.getPlayer());
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
					String cardName = "Card is Face Down.";
					FieldElement fe = opponentField.getFieldElement(1, i);
					if(fe.getCardMode() == CardMode.FACE_UP) {
						cardName = opponentField.getCardAt(1, i).getName();
					}
					System.out.println(i + ": " + cardName);
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
					}else {
						System.out.println("The card was the Trap: " + c.getName());
					}
				}
			}else {
				if(cardChoice == 5) {
					opponentField.removeFieldSpell();
				}else {
					c = opponentField.getCardAt(1, cardChoice);
					if(c instanceof SpellCard) {
						opponentField.sendCardFromFieldToGrave(opponentField.getSpellAndTrapField(), cardChoice);
					}else {
						System.out.println("The card was the Trap: " + c.getName());
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

	/* TODO: Lichtschwerter, Kartentausch, Spalt, ATK/DEF-STOP
		Yami (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Unterweltler und Hexer um 200 Punkte. Verringert außerdem ATK und DEF aller Monster vom Type Fee um 200.)
		Sogen (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Ungeheuer-Krieger und Krieger um 200 Punkte.)
		Umi (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Fisch, Seeschlange, Donner und Aqua um 200 Punkte. Verringert außerdem ATK und DEF aller Monster vom Typ Maschine und Pyro um 200 Punkte.)
		Berg (type: FELD, text: Erhöht ATK und DEF aller Monster vom Typ Drache, Gefluegeltes-Ungeheuer und Donner um 200 Punkte.)
		Fusion (type: NORMAL, text: Fügt 2 oder mehr Fusionsmaterial-Monster zu einem neuen Fusionsmonster zusammen.)
	 */
}
