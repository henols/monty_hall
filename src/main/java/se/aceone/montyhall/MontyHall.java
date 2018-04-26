package se.aceone.montyhall;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.IntStream;

public class MontyHall {

	public static void runSimulations(int iterations) {
		System.out.println("Running simulation... (" + iterations + " iterations)");

		// Create a list of game sessions that can be reused over different strategies 
		List<GameSession> sessions = IntStream.range(0, iterations).mapToObj(_i -> new GameSession()).collect(toList());
		
		long wins = numberOfWins(sessions, Game::startegyKeepBox);
		BigDecimal proc = new BigDecimal((double) wins / iterations * 100.0).setScale(2, RoundingMode.HALF_UP);
		System.out.println("Keep box,\twins: " + proc + "% (" + wins + "/" + iterations + ")");

		wins = numberOfWins(sessions, Game::startegyReselectRandomBox);
		proc = new BigDecimal((double) wins / iterations * 100.0).setScale(2, RoundingMode.HALF_UP);
		System.out.println("Random box,\twins: " + proc + "% (" + wins + "/" + iterations + ")");

		wins = numberOfWins(sessions, Game::startegySwitchBox);
		proc = new BigDecimal((double) wins / iterations * 100.0).setScale(2, RoundingMode.HALF_UP);
		System.out.println("Switch box,\twins: " + proc + "% (" + wins + "/" + iterations + ")");
	}

	private static long numberOfWins(List<GameSession> sessions, Strategy strategy) {
		// lets create a game per session and play them according to the strategy and count winnings 
		return sessions.stream().parallel().map(session -> new Game(session, strategy)).filter(game -> game.playerWon()).count();
	}

	public static void main(String[] args) {
		runSimulations(241165);
	}
}
