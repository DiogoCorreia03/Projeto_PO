package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnknownCommunicationException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    addIntegerField("key", Message.commKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      int id = integerField("key");
      _receiver.makePayment(id);
    }
    catch (UnknownCommunicationException e) {
      _display.popup(Message.invalidCommunication());
    }
  }
}
