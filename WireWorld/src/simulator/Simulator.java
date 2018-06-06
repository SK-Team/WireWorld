package simulator;

import dataHandling.Board;
import elements.Element;

public class Simulator {

	private boolean[][] changes;

	public Simulator() {
		changes = new boolean[Board.HEIGHT][Board.WIDTH];
	}

	public int countElectronHeadsInNeighbourhood(Board b, int x, int y) { // przyjmuje wsp�rz�dne "tablicowe"
		int[][] cells = b.getCells();
		int electronHeads = 0;

		if (y + 1 < b.getWIDTH()) {
			electronHeads += cells[x][y + 1] == Element.ELECTRON_HEAD ? 1 : 0;
			if (x - 1 >= 0)
				electronHeads += cells[x - 1][y + 1] == Element.ELECTRON_HEAD ? 1 : 0;
			if (x + 1 < b.getHEIGHT())
				electronHeads += cells[x + 1][y + 1] == Element.ELECTRON_HEAD ? 1 : 0;
		}

		if (y - 1 >= 0) {
			electronHeads += cells[x][y - 1] == Element.ELECTRON_HEAD ? 1 : 0;
			if (x - 1 >= 0)
				electronHeads += cells[x - 1][y - 1] == Element.ELECTRON_HEAD ? 1 : 0;
			if (x + 1 < b.getHEIGHT())
				electronHeads += cells[x + 1][y - 1] == Element.ELECTRON_HEAD ? 1 : 0;
		}

		if (x - 1 >= 0) {
			electronHeads += cells[x - 1][y] == Element.ELECTRON_HEAD ? 1 : 0;
		}
		if (x + 1 < b.getHEIGHT()) {
			electronHeads += cells[x + 1][y] == Element.ELECTRON_HEAD ? 1 : 0;
		}

		return electronHeads;
	}

	public int whatHappensWithCell(Board b, int x, int y) { // przyjmuje wsp�rz�dne "tablicowe"
		int[][] cells = b.getCells();

		if (cells[x][y] == Element.EMPTY_CELL) {
			return Element.EMPTY_CELL;
		} else if (cells[x][y] == Element.ELECTRON_HEAD) {
			return Element.ELECTRON_TAIL;
		} else if (cells[x][y] == Element.ELECTRON_TAIL) {
			return Element.CONDUCTOR;
		} else if (cells[x][y] == Element.CONDUCTOR) {
			int electronHeads = countElectronHeadsInNeighbourhood(b, x, y);
			if (electronHeads == 1 || electronHeads == 2) {
				return Element.ELECTRON_HEAD;
			} else {
				return Element.CONDUCTOR;
			}

		} else {
			return Element.CONDUCTOR;
		}
	}

	public int simulateGeneration(Board b) {
		int[][] cells = b.getCells();
		int[][] newCells = new int[b.getHEIGHT()][b.getWIDTH()];

		for (int i = 0; i < b.getHEIGHT(); i++) {
			for (int j = 0; j < b.getWIDTH(); j++) {
				newCells[i][j] = whatHappensWithCell(b, i, j);

				changes[i][j] = newCells[i][j] == cells[i][j] ? false : true;

			}
		}
		b.setCells(newCells);
		return 1;
	}

	public boolean[][] getChanges() {
		return changes;
	}
}
