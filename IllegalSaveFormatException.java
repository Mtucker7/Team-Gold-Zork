package shtabnaya_borkv3;

/**
 * This exception is thrown if the save file 
 * does not follow the proper format.
 * @author Sandra Shtabnaya
 * @version 3
 */
public class IllegalSaveFormatException extends Exception{
	public IllegalSaveFormatException(String errorMsg) {
		super("Corrupted save file: " + errorMsg);
	}
	public IllegalSaveFormatException() { super("Corrupted save file.");}
}
