package shtabnaya_borkv3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * This class allows for the storage and creation of rooms in a dungeon.
 * @author Sandra Shtabnaya
 * @version 3
 */
public class Dungeon {
	private String name; //stores the name of a dungeon.
	private Room entry; //stores the entry of a dungeon.
	private String fileName; //stores the file containing the dungeon.
	private Hashtable<String, Room> rooms = new Hashtable<>(); //stores the rooms in the dungeon.
	private Hashtable<String, Item> items = new Hashtable<>(); //stores all the items in the dungeon, with its primary name as the key.


	/**
	 * This constructs a Dungeon using a bork file.
	 * @param fileName	the name of the file describing the dungeon.
	 * @param initState if the dungeon should be restored or reset.
	 * @throws IllegalDungeonFormatException	if the dungeon file is not compatible.
	 */
	public Dungeon(String fileName, boolean initState) throws IllegalDungeonFormatException{
		Scanner input;
		String line;

		try{
			input = new Scanner(new File(fileName));
			this.fileName = fileName;
		}
		catch(FileNotFoundException e){
			throw new IllegalDungeonFormatException("The bork file '" + fileName + "' cannot be found.");
		}

		//reads in the name of the dungeon.
		name = input.nextLine();

		line = input.nextLine();
		if(!line.equals(GameState.VERSION)){
			input.close();
			throw new IllegalDungeonFormatException("The bork file format '" + line + "' is not compatible "
					+ "with the current version of bork.");
		}

		//if the delimiter is not found.
		line = input.nextLine();
		if(!line.equals("===")){
			throw new IllegalDungeonFormatException("'===' is expected. Found '" + line + "'.");
		}

		//if the Items: heading is not found.
		line = input.nextLine();
		if(!line.equals("Items:")){
			throw new IllegalDungeonFormatException("'Items:' is expected. Found '" + line + "'.");
		}

		try{
			//continues to add an item while a new item exists.
			while(true){
				add(new Item(input));
			}
		}
		catch(NoItemException e){}

		//if the Rooms: heading is not found.
		line = input.nextLine();
		if(!line.equals("Rooms:")){
			throw new IllegalDungeonFormatException("'Rooms:' is expected. Found '" + line + "'.");
		}

		try{
			//continues to add a room while a new room exists.
			while(true){
				add(new Room(input, this, initState));
			}
		}
		catch(NoRoomException e){}

		//the room description might have invalid contents.
		catch(NoItemException e){
			throw new IllegalDungeonFormatException(e.getMessage());
		}

		//if the Exits: heading is not found.
		line = input.nextLine();
		if(!line.equals("Exits:")){
			throw new IllegalDungeonFormatException("'Exits:' is expected. Found '" + line + "'.");
		}

		try{
			//continues to create an exit while a new exit exists.
			while(true){
				new Exit(input, this);
			}
		}
		catch(NoExitException e){}
		input.close();
	}


	/**
	 * This constructs a Dungeon with an entry point and a name.
	 * @param entry	the Room that is the entry point.
	 * @param name	the name of the Dungeon.
	 */
	Dungeon(Room entry, String name){
		this.name = name;
		this.entry = entry;
		rooms.put(entry.getTitle(), entry);
	}


	/**
	 * Returns the Room serving as the Dungeon's entry point.
	 * @return Room	the Room object serving as the entry point.
	 */
	public Room getEntry(){
		return entry;
	}


	/**
	 * Sets the entry of the dungeon.
	 * @param entry the first room the player enters.
	 */
	public void setEntry(Room entry) {
		this.entry = entry;
	}


	/**
	 * Gets the name of the bork file storing the Dungeon.
	 * @return name	the name of Dungeon bork file.
	 */
	public String getFileName(){
		return fileName;
	}


	/**
	 * Gets the name of the Dungeon.
	 * @return name	the name of the Dungeon.
	 */
	public String getName(){
		return name;
	}


	/**
	 * Adds a Room object to the Dungeon.
	 * @param room	the Room object.
	 */
	public void add(Room room){
		if(entry == null){
			entry = room;
		}
		rooms.put(room.getTitle(), room);
	}


	/**
	 * Gets the item corresponding with a given name.
	 * @param primaryName the name of the item.
	 * @throws NoItemException if the item is not found in the dungeon contents.
	 * @return the item with the given name.
	 */
	public Item getItem(String primaryName) throws NoItemException{
		Item item = items.get(primaryName);

		if(item == null){
			throw new NoItemException("There is no item called '" + primaryName + "' in your dungeon.");
		}

		return item;
	}


	/**
	 * Adds an Item object to the Dungeon.
	 * @param item	the Item object.
	 */
	public void add(Item item){
		items.put(item.getPrimaryName(), item);
	}


	/**
	 * Returns the Room object associated with a given title.
	 * @param roomTitle		the name of the Room object.
	 * @return				the Room object.
	 */
	public Room getRoom(String roomTitle){
		return rooms.get(roomTitle);
	}


	/**
	 * Saves a dungeon to a .sav file.
	 * @param w		the PrintWriter for outputting to the .sav file.
	 */
	void storeState(PrintWriter w){
		w.println("save data");
		w.println("Dungeon file: " + fileName);

		w.println("Room states:");
		Collection<Room> values = rooms.values();

		for(Room room : values){
			room.storeState(w);
		}

		w.println("===");
	}


	/**
	 * Restores the previous game state from a .sav file.
	 * @param r	the Scanner reading the .sav file.
	 * @throws IllegalSaveFormatException if the room description has invalid contents.
	 */
	void restoreState(Scanner r) throws IllegalSaveFormatException{
		String input = r.nextLine();

		//while there are more rooms to read,
		while(!input.equals("===")){
			input = input.substring(0, input.length() - 1); //removes colon from room name.
			getRoom(input).restoreState(r, this);
			input = r.nextLine();
		}
	}

}
