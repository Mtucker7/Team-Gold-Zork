package shtabnaya_borkv3;

/** This exception is thrown if a room, inventory or bork file contains no more items.
 * @author Sandra Shtabnaya
 * @version 3
 */
public class NoItemException extends Exception{
    public NoItemException(String errorMsg) {
        super(errorMsg);
    }
}

