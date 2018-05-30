package test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import data.Board;
import data.Element;
import simulator.Simulator;

@RunWith(MockitoJUnitRunner.class)
public class SimulatorTest {

	Simulator simulator;
	Board board;

	@Before
	public void setUp() {
		board = new Board();
		simulator = new Simulator();
	}

	@Test
	public void shouldCountElectronHeadsInNeighbourHoodWhenCellIsNearBoundary() {
		// given
		int[][] cells = {
				{ Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD },
				{ Element.CONDUCTOR, Element.CONDUCTOR, Element.EMPTY_CELL, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD },
				{ Element.CONDUCTOR, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 1;
		// when
		int expectedResult = 3;
		int result = simulator.countElectronHeadsInNeighbourhood(board, y, x); // celowe odwrócenie wspó³rzêdnych
		// then
		assertThat(result).isEqualTo(expectedResult);

	}

	@Test
	public void shouldCountElectronHeadsInNeighbourHoodWhenCellIsInTheMiddle() {
		// given
		int[][] cells = { { Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD },
				{ Element.ELECTRON_HEAD, Element.CONDUCTOR, Element.EMPTY_CELL },
				{ Element.ELECTRON_HEAD, Element.ELECTRON_TAIL, Element.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 1;
		int y = 1;
		// when
		int expectedResult = 6;
		int result = simulator.countElectronHeadsInNeighbourhood(board, y, x); // celowe odwrócenie wspó³rzêdnych
		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnNewStateOfElectronHead() {
		// given
		int[][] cells = {
				{ Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD },
				{ Element.CONDUCTOR, Element.CONDUCTOR, Element.EMPTY_CELL, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD },
				{ Element.CONDUCTOR, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 0;

		// when
		int expectedResult = Element.ELECTRON_TAIL;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnNewStateOfElectronTail() {
		// given
		int[][] cells = {
				{ Element.ELECTRON_TAIL, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD },
				{ Element.CONDUCTOR, Element.CONDUCTOR, Element.EMPTY_CELL, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD },
				{ Element.CONDUCTOR, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD,
						Element.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 0;

		// when
		int expectedResult = Element.CONDUCTOR;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnNewStateOfEmptyCell() {
		// given
		int[][] cells = { { Element.EMPTY_CELL, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD },
				{ Element.ELECTRON_HEAD, Element.CONDUCTOR, Element.EMPTY_CELL },
				{ Element.ELECTRON_HEAD, Element.ELECTRON_TAIL, Element.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 0;

		// when
		int expectedResult = Element.EMPTY_CELL;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
	@Test
	public void shouldReturnNewStateOfConductorWithMoreThan2ElectronHeadsInNeighbourHood() {
		// given
		int[][] cells = { { Element.EMPTY_CELL, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD },
				{ Element.ELECTRON_HEAD, Element.CONDUCTOR, Element.EMPTY_CELL },
				{ Element.ELECTRON_HEAD, Element.ELECTRON_TAIL, Element.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 1;
		int y = 1;

		// when
		int expectedResult = Element.CONDUCTOR;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
	@Test 
	public void shouldReturnNewStateOfConductorWith2ElectronHeadsInNeighbourhood() {
		// given
		int[][] cells = { { Element.EMPTY_CELL, Element.ELECTRON_TAIL, Element.ELECTRON_HEAD },
				{ Element.ELECTRON_TAIL, Element.CONDUCTOR, Element.EMPTY_CELL },
				{ Element.ELECTRON_HEAD, Element.ELECTRON_TAIL, Element.ELECTRON_TAIL } };
		board.setCells(cells);
		int x = 1;
		int y = 1;

		// when
		int expectedResult = Element.ELECTRON_HEAD;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
	@Test
	public void shouldSimulateOneGeneration() {
		// given
		int[][] cells = { { Element.ELECTRON_HEAD, Element.ELECTRON_HEAD, Element.ELECTRON_HEAD },
				{ Element.ELECTRON_HEAD, Element.CONDUCTOR, Element.EMPTY_CELL },
				{ Element.ELECTRON_HEAD, Element.ELECTRON_TAIL, Element.ELECTRON_HEAD } };
		board.setCells(cells);


		// when
		int[][] expectedResult = board.copyCells();
		expectedResult[0][0] = Element.ELECTRON_TAIL;
		expectedResult[0][1] = Element.ELECTRON_TAIL;
		expectedResult[0][2] = Element.ELECTRON_TAIL;
		expectedResult[1][0] = Element.ELECTRON_TAIL;
		expectedResult[1][1] = Element.CONDUCTOR;
		expectedResult[1][2] = Element.EMPTY_CELL;
		expectedResult[2][0] = Element.ELECTRON_TAIL;
		expectedResult[2][1] = Element.CONDUCTOR;
		expectedResult[2][2] = Element.ELECTRON_TAIL;
		
	
		simulator.simulateGeneration(board);
		int[][] result = board.getCells();
		board.printToConsole();

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
}
