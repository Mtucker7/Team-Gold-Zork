package shtabnaya_borkv3;

import java.util.Arrays;
import java.util.List;

/**
 * This Singleton class parses a command from a user and creates a Command
 * object for it.
 * @author Sandra Shtabnaya
 * @version 3
 */
class CommandFactory {
	private static CommandFactory theInstance; //stores the one instance of CommandFactory.

	/**
	 * Constructs a CommandFactory object.
	 */
	private CommandFactory(){}
	
	/**
	 * Creates only one instance of CommandFactory.
	 * @return	the one instance of CommandFactory.
	 */
	public static synchronized CommandFactory instance(){
		if(theInstance == null){
			theInstance = new CommandFactory();
		}
		return theInstance;
	}
	
	/**
	 * Parses a given player's command for meaning.
	 * @param commandString	the player's command.
	 * @return	a Command object.
	 */
	public Command parse(String commandString){
		String command = commandString.toLowerCase();
		if(commandString.endsWith(".sav")){
			return new SaveCommand(commandString);
		}
		if(isDirection(command)){
			return new MovementCommand(command);
		}
		if(command.equals("i") || command.equals("inventory")){
			return new InventoryCommand();
		}

		String verb = "";
		String noun = "";

		//if the command contains more than one word.
		if(command.contains(" ")){
			//splits up the command into verb-noun.
			verb = command.substring(0, command.indexOf(" ")).trim();
			noun = commandString.substring(command.indexOf(" ") + 1, command.length()).trim();

			//if the verb and noun are split with a "the", removes it.
			if(noun.startsWith("the")){
				noun = commandString.substring(command.indexOf("the") + 3, command.length()).trim();
			}
		}
		else{
			verb = command;
		}

		//if the command doesn't have a verb-noun structure.
		if(noun.isEmpty()) {
			return new UnknownCommand(commandString);
		}
		if(verb.equals("take")){
			return new TakeCommand(noun);
		}
		if(verb.equals("drop")){
			return new DropCommand(noun);
		}

		return new ItemSpecificCommand(verb, noun);

	}



	 /** This helper method determines if the given input is a direction.
	 /* @return	if the input is a direction.
	 */
	 private boolean isDirection(String direction){
		direction = direction.toLowerCase();
	 	List all = Arrays.asList("e", "w", "s", "n", "d", "u");

	 	return all.contains(direction);
	}

}
