package shtabnaya_borkv3;

import java.util.Scanner;

/**
 * This class creates a dungeon and prompts the player for commands.
 * @author Sandra Shtabnaya
 * @version 3
 */
class Interpreter {

	public static void main(String[] args) {
		Scanner commandLine = new Scanner(System.in);
		CommandFactory commandFactory = CommandFactory.instance(); 
		GameState state = GameState.instance();
		String input;
		Dungeon dungeon = null;
		
		System.out.print("Please enter a dungeon or save file > ");
		input = commandLine.nextLine();

		boolean isValid = false;
		while(!isValid){
			if(input.contains("/")){
				System.out.println("The file name cannot contain '/'\n");

				System.out.print("Please enter a dungeon or save file > ");
				input = commandLine.nextLine();
			}
			else{
				isValid = true;
			}
		}
		
		try{
			
			if(input.endsWith(".sav")){
				input = "saves/" + input; //.sav files are contained in the saves folder.
				state.restore(input);
				dungeon = state.getDungeon();
				System.out.println();
			}
			else if(input.endsWith(".bork")){
				input = "dungeons/" + input; //.bork files are contained in the dungeons folder.
				
				dungeon = new Dungeon(input, true);
				state.initialize(dungeon);
				System.out.println("\nWelcome to " + dungeon.getName() + "!");
			}
			else{
				System.out.println("This file does not have a legal file extension.");
				System.exit(22);
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(22);
		}
		
		System.out.println(state.getAdventurersCurrentRoom().describe());
		input = promptUser(commandLine);
		
		while(!input.equals("q")){
			if(input.equals("save")){
				String fileName = dungeon.getFileName();
				fileName = fileName.substring(fileName.indexOf("/") + 1, fileName.length() - 1);
				fileName = fileName.substring(0, fileName.indexOf("."));

				System.out.print("Where should I save it? (default is '" + fileName + "')> ");
				input = commandLine.nextLine();

				if(input.isEmpty()){
					input = fileName;
				}
				if(!input.contains(".sav")) {
					input += ".sav";
				}
			}

			Command order = commandFactory.parse(input);
			
			try{
				System.out.println(order.execute());
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				System.out.println(state.getAdventurersCurrentRoom().describeExits());
			}
			
			input = promptUser(commandLine);
			
		}
		commandLine.close();
	}
	
	
	/**
	 * This method prompts the user for more input.
	 * @param in the name of the Scanner.
	 * @return input the input from the user.
	 */
	private static String promptUser(Scanner in){
		System.out.print("> ");
		return in.nextLine();
	}
}
