package shtabnaya_borkv3;

 /* Deals with commands that move a player to a new room.
 * @author Sandra Shtabnaya
 * @version 3
 */
class MovementCommand extends Command{
    private String dir; //stores the direction the player wishes to go.


    /**
     * Constructs a MovementCommand object.
     * @param dir  the direction the player wishes to go.
     */
    MovementCommand(String dir){
        this.dir = dir;
    }


    /**
     * Executes a command to move to a new room.
     * @return  the direction the player wishes to go.
     */
    String execute(){
        GameState state = GameState.instance();
		Room currentRoom = state.getAdventurersCurrentRoom();
		Room newRoom = currentRoom.leaveBy(dir);

		if(newRoom != null){
			state.setAdventurersCurrentRoom(newRoom);
		}
		else{
			return "You can't go " + dir + ".\n";
		}

		return state.getAdventurersCurrentRoom().describe();
    }
}
