package prr.app.client;

import prr.core.Network;
import prr.core.exception.DuplicateClientException;
import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("nif", Message.taxId());
  }
  
  /**
   * @see pt.tecnico.uilib.menus.Command#execute()
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
