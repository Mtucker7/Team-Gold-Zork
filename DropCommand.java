package shtabnaya_borkv3;

import java.util.ArrayList;

/**
 * Deals with commands that drop an object.
 * @author Sandra Shtabnaya
 * @version 3
 */
class DropCommand extends Command{
    private String itemName; //stores the name of the Item the player wishes to drop.


    /**
     * Constructs a DropCommand object.
     * @param itemName  the name of the Item the player wishes to drop.
     */
    DropCommand(String itemName){
        this.itemName = itemName;
    }


    /**
     * Executes a command to drop an Item object.
     * @return  the result of the command.
     */
    String execute(){
        try{
            Item droppedItem = state.getItemFromInventoryNamed(itemName);
            state.removeFromInventory(droppedItem);
            state.getAdventurersCurrentRoom().add(droppedItem);
        }
        catch(NoItemException e){
            return "You don't have a " + itemName + ".\n";
        }

        return "Dropped.\n";
    }
}
