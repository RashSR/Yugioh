package game.effects;

import cards.spell.SpellCard;
import game.Player;
import game.battle.PlayField;

public class SpellEffects {

	public static void activateEffect(SpellCard sc, PlayField pf) {
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
			if(pf.getGame().getNotActivePlayer().getPlayField().hasGraveMonster()) {
				if(pf.hasFreeMonsterSpace()) {
					System.out.println("You can summon a Monster from your Opponent");
					//TODO: Auswahl geben
				}
			}
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
		default:
			break;
		}
	}



	/*
		Yami (type: FELD, text: Erh�ht ATK und DEF aller Monster vom Typ Unterweltler und Hexer um 200 Punkte. Verringert au�erdem ATK und DEF aller Monster vom Type Fee um 200.)
		Schwert der dunklen Zerst�rung (type: AUSR�STUNG, text: Ein FINSTERNIS Monster, das mit dieser Karte ausger�stet wird, erh�ht seine ATK um 400 Punkte und verringert seine DEF um 200 Punkte.)
		Fluchzerst�rer (type: NORMAL, text: Zerst�rt eine Zauberkarte auf dem Spielfeld. Wenn das Ziel dieser Karte verdeckt ist, decke die entsprechende Karte auf. Ist die Karte eine Zauberkarte, wird sie zerst�rt. Ist dies nicht der Fall, wird sie wieder umgedreht. Eine so aufgedeckte Karte wird nicht aktiviert.)
		Sogen (type: FELD, text: Erh�ht ATK und DEF aller Monster vom Typ Ungeheuer-Krieger und Krieger um 200 Punkte.)
		Umi (type: FELD, text: Erh�ht ATK und DEF aller Monster vom Typ Fisch, Seeschlange, Donner und Aqua um 200 Punkte. Verringert au�erdem ATK und DEF aller Monster vom Typ Maschine und Pyro um 200 Punkte.)
		Berg (type: FELD, text: Erh�ht ATK und DEF aller Monster vom Typ Drache, Gefluegeltes-Ungeheuer und Donner um 200 Punkte.)
		Fusion (type: NORMAL, text: F�gt 2 oder mehr Fusionsmaterial-Monster zu einem neuen Fusionsmonster zusammen.)
		�berl�ufer (type: NORMAL, text: W�hle ein Monster, das dein Gegner kontrolliert (unabh�ngig von der Position). Kontrolliere das Monster bis zum Ende deines Spielzugs.)
	 */
}
