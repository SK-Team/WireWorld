package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import data.Board;
import data.Diode;
import data.Element;
import data.WrongInputFileException;

public class BoardTest {

	private Board board;

	@Before
	public void resetCells() {
		board = new Board();
	}

	@Test(expected = WrongInputFileException.class)
	public void shouldThrowWrongInputFileExceptionAfterReadingFromFileWithWrongCoordinates()
			throws WrongInputFileException {
		String filePath = getClass().getResource("WrongCoordinatesInputFile.txt").getPath();

		try {
			board.readBoardFromFile(filePath);
		} catch (IOException e) {
			fail("IOException thrown!");
		}
	}

	@Test(expected = WrongInputFileException.class)
	public void shouldThrowWrongInputFileExceptionAfterReadingFileWithToManyCoordinates()
			throws WrongInputFileException {
		String filePath = getClass().getResource("TooManyCoordinatesInputFile.txt").getPath();
		try {
			board.readBoardFromFile(filePath);
		} catch (IOException e) {
			fail("IOException thrown!");
		}
	}

	@Test(expected = WrongInputFileException.class)
	public void shouldThrowWrongInputFileExceptionAfterReadingFileWithWrongElementType()
			throws WrongInputFileException {
		String filePath = getClass().getResource("WrongElementTypeInputFile.txt").getPath();
		try {
			board.readBoardFromFile(filePath);
		} catch (IOException e) {
			fail("IOException thrown!");
		}
	}

	@Test(expected = IOException.class)
	public void shouldThrowIOExceptionAfterReadingUnexistingFile() throws IOException, WrongInputFileException {
		String filePath = "UnexistingFile.txt";
		board.readBoardFromFile(filePath);
	}

	@Test
	public void shouldReadBoardFromCorrectFile() throws WrongInputFileException {
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
