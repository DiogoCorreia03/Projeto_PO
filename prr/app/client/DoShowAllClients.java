package prr.app.client;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all clients.
 * Command present in the Client's Menu, used to Show All the Clients 
 * registered on the Network.
 * When selected presents all the Clients info, one per line, in 
 * alphabetical order, in the specified format.
 */
class DoShowAllClients extends Command<Network> {

  /**
   * Constructor of the Class.
   * Calls the constructor of the SuperClass passing the Command's
   * label and associated Network as arguments.
   * @param receiver Network associated with the Command.
   */
  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }
  
  /**
   * Execute method, overriding the SuperClass's one.
   * Gets an ArrayList, already sorted, from the Network with all
   * the Clients and displays them to the User.
   * @see pt.tecnico.uilib.menus.Command#execute()
   */
  @Override
  protected final void execute() throws CommandException {
      _display.addAll(_receiver.showAllClients());
      _display.display();
    }
}
