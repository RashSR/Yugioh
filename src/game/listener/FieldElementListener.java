package game.listener;

import game.map.FieldElement;

public class FieldElementListener extends Thread{

	private FieldElement[] field;
	private int index;
	private PhaseListener pl;
	private FieldElement toWatch;

	public FieldElementListener(FieldElement[] field, int index, PhaseListener pl) {
		this.field = field;
		this.index = index;
		this.pl = pl;
		this.toWatch = field[index];
		
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			if(!field[index].equals(toWatch)) {
				pl.interrupt();
				this.interrupt();
			}
		}
	}
}
