package game.effects;

import cards.monster.MonsterCard;
import game.map.PlayField;

public class MonsterEffects {

	public static void activateEffect(MonsterCard mc, PlayField pf) {
		switch (mc.getName()) {
		case "Fallenmeister":
			System.out.println("ich flippe jetzt!");
			break;
		case "Menschenfresskäfer":
			System.out.println("ich flippe jetzt!");
			break;
		case "Hane-Hane":
			System.out.println("ich flippe jetzt!");
			break;
		}
	}

}
