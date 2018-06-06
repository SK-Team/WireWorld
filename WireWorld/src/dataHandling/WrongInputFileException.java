package dataHandling;

public class WrongInputFileException extends Exception {
	public WrongInputFileException() {
		super("Incorrect input file format");
	}
}
