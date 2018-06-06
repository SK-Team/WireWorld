/*
package test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import dataHandling.Board;
import elements.ElementConstans;
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
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.CONDUCTOR, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.CONDUCTOR, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 1;
		// when
		int expectedResult = 3;
		int result = simulator.countElectronHeadsInNeighbourhood(board, y, x); // celowe odwr�cenie wsp�rz�dnych
		// then
		assertThat(result).isEqualTo(expectedResult);

	}

	@Test
	public void shouldCountElectronHeadsInNeighbourHoodWhenCellIsInTheMiddle() {
		// given
		int[][] cells = { { ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_TAIL, ElementConstans.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 1;
		int y = 1;
		// when
		int expectedResult = 6;
		int result = simulator.countElectronHeadsInNeighbourhood(board, y, x); // celowe odwr�cenie wsp�rz�dnych
		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnNewStateOfElectronHead() {
		// given
		int[][] cells = {
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.CONDUCTOR, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.CONDUCTOR, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 0;

		// when
		int expectedResult = ElementConstans.ELECTRON_TAIL;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnNewStateOfElectronTail() {
		// given
		int[][] cells = {
				{ ElementConstans.ELECTRON_TAIL, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.CONDUCTOR, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.CONDUCTOR, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD,
						ElementConstans.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 0;

		// when
		int expectedResult = ElementConstans.CONDUCTOR;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnNewStateOfEmptyCell() {
		// given
		int[][] cells = { { ElementConstans.EMPTY_CELL, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_TAIL, ElementConstans.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 0;
		int y = 0;

		// when
		int expectedResult = ElementConstans.EMPTY_CELL;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
	@Test
	public void shouldReturnNewStateOfConductorWithMoreThan2ElectronHeadsInNeighbourHood() {
		// given
		int[][] cells = { { ElementConstans.EMPTY_CELL, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_TAIL, ElementConstans.ELECTRON_HEAD } };
		board.setCells(cells);
		int x = 1;
		int y = 1;

		// when
		int expectedResult = ElementConstans.CONDUCTOR;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
	@Test 
	public void shouldReturnNewStateOfConductorWith2ElectronHeadsInNeighbourhood() {
		// given
		int[][] cells = { { ElementConstans.EMPTY_CELL, ElementConstans.ELECTRON_TAIL, ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.ELECTRON_TAIL, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_TAIL, ElementConstans.ELECTRON_TAIL } };
		board.setCells(cells);
		int x = 1;
		int y = 1;

		// when
		int expectedResult = ElementConstans.ELECTRON_HEAD;
		int result = simulator.whatHappensWithCell(board, y, x);

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
	@Test
	public void shouldSimulateOneGeneration() {
		// given
		int[][] cells = { { ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_HEAD },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.CONDUCTOR, ElementConstans.EMPTY_CELL },
				{ ElementConstans.ELECTRON_HEAD, ElementConstans.ELECTRON_TAIL, ElementConstans.ELECTRON_HEAD } };
		board.setCells(cells);


		// when
		int[][] expectedResult = board.copyCells();
		expectedResult[0][0] = ElementConstans.ELECTRON_TAIL;
		expectedResult[0][1] = ElementConstans.ELECTRON_TAIL;
		expectedResult[0][2] = ElementConstans.ELECTRON_TAIL;
		expectedResult[1][0] = ElementConstans.ELECTRON_TAIL;
		expectedResult[1][1] = ElementConstans.CONDUCTOR;
		expectedResult[1][2] = ElementConstans.EMPTY_CELL;
		expectedResult[2][0] = ElementConstans.ELECTRON_TAIL;
		expectedResult[2][1] = ElementConstans.CONDUCTOR;
		expectedResult[2][2] = ElementConstans.ELECTRON_TAIL;
		
	
		simulator.simulateGeneration(board);
		int[][] result = board.getCells();
		board.printToConsole();

		// then
		assertThat(result).isEqualTo(expectedResult);
	}
}

*/
