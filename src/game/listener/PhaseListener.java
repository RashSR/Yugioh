package game.listener;

import game.battle.Game;
import game.battle.PlayPhase;
import game.effects.SpellEffects;
import game.map.PlayField;

public class PhaseListener extends Thread{
	private Game game;
	private PlayField pf;
	private int myIndex;
	private int choice;
	private PlayPhase phaseToListen;

	public PhaseListener(PlayField pf, PlayPhase phaseToListen, int myIndex, int choice) {
		this.phaseToListen = phaseToListen;
		this.pf = pf;
		this.game = pf.getGame();
		this.myIndex = myIndex;
		this.choice = choice;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			if(game.getActivePhase() == phaseToListen) {
				this.interrupt();
				SpellEffects.changeOfHeartAfter(pf, myIndex, choice);
			}
		}
	}
}
