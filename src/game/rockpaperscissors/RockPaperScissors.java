package game.rockpaperscissors;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
	private static Random rand = new Random();
	private static Scanner sc;
	
	public static void play() {
		Option player1 = playerInput();
		Option player2 = pickRandom();
		System.out.println("Spieler: " + player1 + ", Computer: " + player2);
		Option winOption = chooseWinner(player1, player2);
		if(winOption == null) {
			System.out.println("Unentschieden!");
		}else if(winOption == player1) {
			System.out.println("Spieler hat gewonnen!");
		}else {
			System.out.println("Computer hat gewonnen!");
		}
	}

	private static Option playerInput() {
		sc = new Scanner(System.in);
		System.out.println("Do you choose Rock(0), Paper(1) or Scissors(2)?");
		return Option.values()[sc.nextInt()];
	}

	private static Option chooseWinner(Option op1, Option op2) {
		if(op1 == op2) {
			return null;
		}else if(op1 == Option.ROCK) {
			if(op2 == Option.PAPER) {
				return Option.PAPER;
			}
			return Option.ROCK;
		}else if(op1 == Option.SCISSORS) {
			if(op2 == Option.ROCK) {
				return Option.ROCK;
			}
			return Option.SCISSORS;
		}else if(op1 == Option.PAPER) {
			if(op2 == Option.SCISSORS) {
				return Option.SCISSORS;
			}
			return Option.PAPER;
		}
		return null;
	}

	private static Option pickRandom() {
		return Option.values()[rand.nextInt(3)];
	}
}
