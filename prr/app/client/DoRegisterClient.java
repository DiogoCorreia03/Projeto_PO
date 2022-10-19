package prr.app.client;

import prr.core.Network;
import prr.core.exception.DuplicateClientException;
import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 * Command present in the Client's Menu, used to Register a new Client on the Network.
 * When selected asks the User for the Client's Key, Name and TaxNumber.
 * Throws an Exception when the Key associated with the Client to register is
 * already registered in the Network.
 */
class DoRegisterClient extends Command<Network> {

  /**
   * Constructor of the Class.
   * Calls the constructor of the SuperClass passing the Command's label and
   * associated Network as arguments. Furthermore, asks the User for the
   * Client's attributes.
   * @param receiver Network associated with the Command.
   */
  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("nif", Message.taxId());
  }
  
  /**
   * Execute method, overriding the SuperClass's one.
   * Gets the attributes previously given by the User and tries to register
   * the Client on the Network, catching an Exception when the Client's Key is duplicate.
   * @see pt.tecnico.uilib.menus.Command#execute()
   * @throws DuplicateClientKeyException Exception thrown when the
   * Key of the Client is already registered in the Network.
   */
  @Override
  protected final void execute() throws CommandException {
    try {
      String key = stringField("key");
      String name = stringField("name");
      int nif = integerField("nif");
      _receiver.registerClient(key, name, nif);
    } 
    catch (DuplicateClientException e) {
      throw new DuplicateClientKeyException(e.getKey());
    }
  }
}
