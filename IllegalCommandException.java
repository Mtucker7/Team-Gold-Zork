package shtabnaya_borkv3;

/**
 * This exception is thrown if a user enters an illegal command.
 * @author Sandra Shtabnaya
 * @version 3
 */
public class IllegalCommandException extends Exception{
    public IllegalCommandException(String errorMsg){
        super(errorMsg);
    }
}
