package simulator;

import dataHandling.Board;
import elements.ElementConstans;

public class Simulator {

	private boolean[][] changes;
	private boolean anyChange;

	public Simulator() {
		changes = new boolean[Board.HEIGHT][Board.WIDTH];
	}

	public int countElectronHeadsInNeighbourhood(Board b, int x, int y) { // przyjmuje wsp�rz�dne "tablicowe"
		int[][] cells = b.getCells();
		int electronHeads = 0;

		if (y + 1 < b.getWIDTH()) {
			electronHeads += cells[x][y + 1] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
			if (x - 1 >= 0)
				electronHeads += cells[x - 1][y + 1] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
			if (x + 1 < b.getHEIGHT())
				electronHeads += cells[x + 1][y + 1] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
		}

		if (y - 1 >= 0) {
			electronHeads += cells[x][y - 1] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
			if (x - 1 >= 0)
				electronHeads += cells[x - 1][y - 1] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
			if (x + 1 < b.getHEIGHT())
				electronHeads += cells[x + 1][y - 1] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
		}

		if (x - 1 >= 0) {
			electronHeads += cells[x - 1][y] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
		}
		if (x + 1 < b.getHEIGHT()) {
			electronHeads += cells[x + 1][y] == ElementConstans.ELECTRON_HEAD ? 1 : 0;
		}

		return electronHeads;
	}

	public int whatHappensWithCell(Board b, int x, int y) { // przyjmuje wsp�rz�dne "tablicowe"
		int[][] cells = b.getCells();

		if (cells[x][y] == ElementConstans.EMPTY_CELL) {
			return ElementConstans.EMPTY_CELL;
		} else if (cells[x][y] == ElementConstans.ELECTRON_HEAD) {
			return ElementConstans.ELECTRON_TAIL;
		} else if (cells[x][y] == ElementConstans.ELECTRON_TAIL) {
			return ElementConstans.CONDUCTOR;
		} else if (cells[x][y] == ElementConstans.CONDUCTOR) {
			int electronHeads = countElectronHeadsInNeighbourhood(b, x, y);
			if (electronHeads == 1 || electronHeads == 2) {
				return ElementConstans.ELECTRON_HEAD;
			} else {
				return ElementConstans.CONDUCTOR;
			}

		} else {
			return ElementConstans.CONDUCTOR;
		}
	}

	public int simulateGeneration(Board b) {
		int[][] cells = b.getCells();
		int[][] newCells = new int[b.getHEIGHT()][b.getWIDTH()];

		for (int i = 0; i < b.getHEIGHT(); i++) {
			for (int j = 0; j < b.getWIDTH(); j++) {
				newCells[i][j] = whatHappensWithCell(b, i, j);

				if (newCells[i][j] != cells[i][j]) {
					changes[i][j] = true;
					anyChange = true;
				}
			}
		}
		b.setCells(newCells);
		return 1;
	}

	public boolean[][] getChanges() {
		return changes;
	}

	public boolean isAnyChange() {
		return anyChange;
	}
}
