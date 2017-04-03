package shtabnaya_borkv3;

/**
 * This exception is thrown if the bork file
 * does not follow the proper format.
 * @author Sandra Shtabnaya
 * @version 3
 */
public class IllegalDungeonFormatException extends Exception{
	public IllegalDungeonFormatException(String errorMsg) {
		super("Illegal bork file format: " + errorMsg);
	}
}
