package shtabnaya_borkv3;

import java.io.IOException;

 /* Deals with commands to save the game.
 * @author Sandra Shtabnaya
 * @version 3
 */
class SaveCommand extends Command{
    private String saveFileName; //stores the name of the file to save to.


    /**
     * Constructs a SaveCommand object.
     * @param f  the name of the save file to save to.
     */
    SaveCommand(String f){
        saveFileName = f;
    }


    /**
     * Executes a command to save a game.
     * @return  the result of the command.
     * @throws IOException  if the file cannot be closed or written to.
     */
    String execute() throws IOException{
        state.store("saves/" + saveFileName);
        return "Saved to " + saveFileName + "\n";
    }
}
