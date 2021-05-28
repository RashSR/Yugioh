import java.util.Scanner;

import database.cards.CardExport;
import database.cards.CardImport;

public class ExportSession {
	
	/*
	 *Allows the user to export any Card he wants. 
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean isRunning = true;
		CardImport.importCards();
		while(isRunning) {
			System.out.println("Do you want to export Monster(0), FusionMonster(1), Spell(2) or Trap(3) Cards? ");
			switch (sc.nextLine()) {
			case "0":
				CardExport.exportMonsterCard();
				break;
			case "1":
				CardExport.exportFusionMonster();
				break;
			case "2":
				CardExport.exportSpellCard();
				break;
			case "3":
				CardExport.exportTrapCard();
			default:
				isRunning = false;
				break;
			}
			CardImport.importCards();
		}
		System.out.println("Danke fürs Eintragen!");
		sc.close();
	}

}
