package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.AndGate;
import data.Board;
import data.Diode;
import data.Element;
import data.NorGate;
import data.OrGate;

public class BoardTest {

	private Board board;

	@Before
	public void resetCells() {
		board = new Board();
	}

	@Test
	public void shouldReturnErrorAfterReadingFromFileWithWrongCoordinates() {
		// given
		String filePath = getClass().getResource("WrongCoordinatesInputFile.txt").getPath();
		// when
		int result = 0;
		int expectedResult = Board.WRONG_INPUT_FILE_FORMAT;
		try {
			result = board.readBoardFromFile(filePath);
		} catch (IOException e) {
			fail("IOException thrown!");
		}

		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnErrorAfterReadingFileWithToManyCoordinates() {
		// given
		String filePath = getClass().getResource("TooManyCoordinatesInputFile.txt").getPath();
		// when
		int result = 0;
		int expectedResult = Board.WRONG_INPUT_FILE_FORMAT;
		try {
			result = board.readBoardFromFile(filePath);
		} catch (IOException e) {
			fail("IOException thrown!");
		}

		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	public void shouldReturnErrorAfterReadingFileWithWrongElementType() {
		// given
		String filePath = getClass().getResource("WrongElementTypeInputFile.txt").getPath();
		// when
		int result = 0;
		int expectedResult = Board.WRONG_INPUT_FILE_FORMAT;
		try {
			result = board.readBoardFromFile(filePath);
		} catch (IOException e) {
			fail("IOException thrown!");
		}

		// then
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test(expected = IOException.class)
	public void shouldThrowIOExceptionAfterReadingUnexistingFile() throws IOException {
		String filePath = "UnexistingFile.txt";
		board.readBoardFromFile(filePath);
	}

	@Test
	public void shouldReadBoardFromCorrectFile() {
		String filePath = getClass().getResource("CorrectInputFile.txt").getPath();

		int[][] expectedCellsState = board.copyCells();
		System.out.println("expected: ");
		for (int[] t : expectedCellsState) {
			for (int c : t) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
		expectedCellsState[0][0] = Element.CONDUCTOR;
		expectedCellsState[1][1] = Element.ELECTRON_TAIL;
		expectedCellsState[1][2] = Element.ELECTRON_HEAD;
		expectedCellsState[1][3] = Element.EMPTY_CELL;

		try {
			board.readBoardFromFile(filePath);

			List<Element> elements = board.getElements();
			if (elements.size() != 1) {
				fail("Bledna ilosc elementow: " + elements.size());
			}
			if (!(elements.get(0) instanceof Diode)) {
				fail("Wczytany bledny element!");
			}
			Diode readDiode = (Diode) elements.get(0);
			for (Point p : readDiode.getLocation()) {
				expectedCellsState[p.y][p.x] = Element.CONDUCTOR;
			}
		} catch (IOException e) {
			fail("IOEXception thrown!");
		}
		int[][] cellsState = board.getCells();

		assertThat(cellsState).isEqualTo(expectedCellsState);
	}
}
