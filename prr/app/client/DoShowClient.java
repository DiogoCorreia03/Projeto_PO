package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownClientException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 * Command present in the Client's Menu, used to Show a specific Client
 * registered on the Network.
 * When selected asks the User for the Key of the Client and presents all
 * the Client's info, in one line, in the
 * specified format.
 * Throws an Exception when the Client's Key isn't registered on the Network.
 */
class DoShowClient extends Command<Network> {

  /**
   * Constructor of the Class.
   * Calls the constructor of the SuperClass passing the Command's label and
   * associated Network as arguments. Furthermore, asks the User for the Client's Key.
   * @param receiver Network associated with the Command.
   */
  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField("key", Message.key());
  }
  
  /**
   * Execute method, overriding the SuperClass's one.
   * Gets the attribute previously given by the User and tries to get the
   * associated Client from the Network, catching an Exception when the
   * Client's Key doesn't exist on the Network.
   * @see pt.tecnico.uilib.menus.Command#execute()
   * @throws UnknownClientKeyException Exception thrown when the
   * Key of the Client doesn't exist on the Network.
   */
  @Override
  protected final void execute() throws CommandException {
    try {
      String key = stringField("key");
      _display.addLine(_receiver.showClient(key));
      _display.display();
    }
    catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
