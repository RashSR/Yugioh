package game;

import game.battle.Game;
import game.battle.PlayPhase;
import game.effects.SpellEffects;

public class PhaseListener extends Thread{

	private Game game;
	private PlayPhase phaseToListen;

	public PhaseListener(Game game, PlayPhase phaseToListen) {
		this.game = game;
		this.phaseToListen = phaseToListen;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			if(game.getActivePhase() == phaseToListen) {
				this.interrupt();
				SpellEffects.changeOfHeartAfter();
			}
		}
	}
}
