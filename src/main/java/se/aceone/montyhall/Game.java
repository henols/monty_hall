package se.aceone.montyhall;

import java.util.List;
import java.util.Random;

public class Game {

	private final int winningBox;
	private final List<Box> boxes;
	private int selectedBox = -1;

	public Game(GameSession session, Strategy strategy) {
		boxes = session.getBoxes();
		winningBox = session.getWinningBoxId();
		play(strategy);
	}

	public boolean playerWon() {
		return selectedBox == winningBox;
	}

	private void play(Strategy strategy) {
		selectedBox = selectBox();
		strategy.play(this);
	}
	
	private int selectBox() {
		// select box will select any box that isn't previously selected 
		Box box = boxes.stream().filter(d -> d.getId() != selectedBox).findAny().get();
		return box != null ? box.getId() : -1; // non-existent id if null
	}

	private void openBox() {
		if(boxes.size()>=3) {
			// by removing the box from the list is a simplification, 
			// it could be done by states and more complicated filtering in select and open box methods 
			Box box = boxes.stream().filter(d -> d.getId() != winningBox && d.getId() != selectedBox).findAny().get();
			boxes.remove(box);
		}
	}


	/**
	 * This strategy will switch the selected to another box.
	 * Assumption 66.6% of selecting the right box.
	 * @param game
	 */
	public static void startegySwitchBox(Game game) {
		game.openBox();
		game.selectedBox = game.selectBox();
	}

	/**
	 * This strategy will make a new random selection of a box.
	 * Assumption 50% of selecting the right box.
	 * @param game
	 */
	public static void startegyReselectRandomBox(Game game) {
		game.openBox();
		Random random = new Random();
		game.selectedBox = game.boxes.get(random.nextInt(game.boxes.size())).getId();

	}

	/**
	 * This strategy will stick with the selected box.
	 * Assumption 33.3% of selecting the right box.
	 * @param game
	 */
	public static void startegyKeepBox(Game game) {
	}


}
