package dataHandling;

import elements.*;

import java.awt.*;

public class InputFileParser {
	public static Point parseCoordinatesFromLineSplit(String[] split) throws WrongInputFileException {
		String[] xySplit;
		int x, y;
		try {
			xySplit = split[1].split(",");
			xySplit[1] = xySplit[1].trim();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new WrongInputFileException();
		}
		if (xySplit.length == 2) {
			x = Integer.valueOf(xySplit[0]);
			y = Integer.valueOf(xySplit[1]);
			// celowe odwrocenie ukladu wspolrzednych
			if (x < 0 || x > Board.HEIGHT || y < 0 || y > Board.WIDTH) {
				throw new WrongInputFileException();
			}
		} else {
			throw new WrongInputFileException();
		}
		return new Point(x, y);
	}

	public static int parseElementExceptWireTypeFromLineSplit(String[] split) throws WrongInputFileException {
		if (split.length >= 3) {
			split[2] = split[2].trim();
			if (split[2].equals("R")) {
				return ElementConstans.REVERSED_TYPE;
			} else if (split[2].equals("D")) {
				return ElementConstans.DEFAULT_TYPE;
			} else {
				throw new WrongInputFileException();
			}
		}
		return ElementConstans.DEFAULT_TYPE;
	}

	public static Integer parseCellStateFromLineSplit(String[] split) {
		if (split[0].equals("ElectronHead:")) {
			return ElementConstans.ELECTRON_HEAD;
		} else if (split[0].equals("ElectronTail:")) {
			return ElementConstans.ELECTRON_TAIL;
		} else if (split[0].equals("Conductor:")) {
			return ElementConstans.CONDUCTOR;
		} else if (split[0].equals("Blank:")) {
			return ElementConstans.EMPTY_CELL;
		} else {
			return null;
		}
	}

	public static Wire parseWireFromLineSplit(String[] split, Point givenStartPoint) {
		int length;
		int type;
		if (split[0].equals("Wire:")) {
			length = Wire.DEFAULT_LENGTH;
			type = ElementConstans.DEFAULT_TYPE;
			if (split.length >= 3) {
				if (split[2].equals("R")) {
					type = ElementConstans.REVERSED_TYPE;
				} else if (split[2].equals("D")) {
					type = ElementConstans.DEFAULT_TYPE;
				} else {
					length = Integer.valueOf(split[2]);
				}
			}
			if (split.length >= 4) {
				if (split[3].equals("R")) {
					type = ElementConstans.REVERSED_TYPE;
				} else if (split[3].equals("D")) {
					type = ElementConstans.DEFAULT_TYPE;
				}
			}
			return new Wire(givenStartPoint, type, length);
		} else {
			return null;
		}
	}

	public static Element parseElementExceptWireFromLineSplit(String[] split, Point givenStartPoint)
			throws WrongInputFileException {
		int type;
		type = parseElementExceptWireTypeFromLineSplit(split);

		if (split[0].equals("Diode:")) {
			return new Diode(givenStartPoint, type);
		} else if (split[0].equals("OR:")) {
			return new OrGate(givenStartPoint, type);
		} else if (split[0].equals("NOR:")) {
			return new NorGate(givenStartPoint, type);
		} else if (split[0].equals("AND:")) {
			return new AndGate(givenStartPoint, type);
		}
		return null;
	}

}
