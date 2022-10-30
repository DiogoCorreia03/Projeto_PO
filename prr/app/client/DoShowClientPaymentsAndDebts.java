package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownClientException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField("key", Message.key());
    //FIXME add command fields
  }
  
  @Override
  protected final void execute() throws CommandException {
    String key= stringField("key");
    try {
    _display.popup(Message.clientPaymentsAndDebts(key, Math.round(_receiver.showClient(key).getPayments()), Math.round(_receiver.showClient(key).getDebts())));
    }
    catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
