package se.aceone.montyhall;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameSession {
	private final List<Box> boxes;
	private final int winningBoxId;

	private static final int NUMBER_OF_BOXES = 3;

	public GameSession() {
		Random random = new Random();
		winningBoxId = random.nextInt(NUMBER_OF_BOXES);
		boxes = IntStream.range(0, NUMBER_OF_BOXES).mapToObj(id -> new Box(id)).collect(Collectors.toList());
	}

	public int getWinningBoxId() {
		return winningBoxId;
	}

	public List<Box> getBoxes() {
		return boxes.stream().collect(Collectors.toList());
	}
}
