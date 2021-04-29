package game.listener;

import game.map.FieldElement;
import game.map.PlayField;

public class EquipCardListener extends Thread{
	private PlayField pf;
	private FieldElement[] spellField;
	private int spellIndex;
	private FieldElement[] monsterField;
	private int monsterIndex;
	private int atkBonus;
	private int defBonus;
	private FieldElement monsterFieldToWatch;
	
	public EquipCardListener(PlayField pf, FieldElement[] spellField, int spellIndex, FieldElement[] monsterField, int monsterIndex, int atkBonus, int defBonus) {
		this.pf = pf;
		this.spellField = spellField;
		this.spellIndex = spellIndex;
		this.monsterField = monsterField;
		this.monsterIndex = monsterIndex;
		this.atkBonus = atkBonus;
		this.defBonus = defBonus;
		this.monsterFieldToWatch = monsterField[monsterIndex];
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			if(spellField[spellIndex].isEmpty()) {
				monsterField[monsterIndex].addToAtkChange(-1 * atkBonus);
				monsterField[monsterIndex].addToDefChange(-1 * defBonus);
				this.interrupt();
			}else if(!monsterField[monsterIndex].equals(monsterFieldToWatch)) {
				//TODO: Falls ChangeOfHeart aktiv überpüfen wohin es geht etc.
				pf.sendCardFromFieldToGrave(spellField, spellIndex);
				/*
				monsterField[monsterIndex].addToAtkChange(-1 * atkBonus);
				monsterField[monsterIndex].addToDefChange(-1 * defBonus);
				*/
				this.interrupt();
			}
		}
	}
}
