package shtabnaya_borkv3;

/**
 * Deals with commands that can only be performed on certain objects.
 * @author Sandra Shtabnaya
 * @version 3
 */
class ItemSpecificCommand extends Command{
    private String verb; //stores what a player wants to do with an object.
    private String noun; //stores the name of the object a player wants to manipulate.
    private Item item; //stores the item to perform an action on.


    /**
     * Constructs an ItemSpecificCommand.
     * @param verb  the desired action to be performed on an Item.
     * @param noun  the name of the Item to manipulate.
     */
    ItemSpecificCommand(String verb, String noun){
        this.verb = verb;
        this.noun = noun;
    }


    /**
     * Executes a command to do something to an Item
     * @return  the result of the command.
     */
    String execute(){
       if(!existsInRoom(noun)){
           return "There's no " + noun + " here.\n";
       }
       String message = item.getMessageForVerb(verb);

       if(message == null){
           return "You can't " + verb + " the " + noun + ".\n";
       }
        return message + "\n";
    }


    /**
     * This helper method determines if an item exists in the room
     * or in the player's inventory.
     * @param item  the item to search for.
     * @return  if the item exists in the room.
     */
    private boolean existsInRoom(String item){

        try{ //attempts to find the item in the inventory.
            this.item = state.getItemFromInventoryNamed(item);
            return true;
        }
        catch(NoItemException e){
            try{ //attempts to find the item in the room.
                this.item = state.getItemInVicinityNamed(item);
                return true;
            }
            catch(NoItemException w){
                return false;
            }
        }
    }
}
