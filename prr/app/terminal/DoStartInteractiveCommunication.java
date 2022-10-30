package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.TerminalException;
import prr.core.terminal.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("key", Message.terminalKey());
    addOptionField("type", Message.commType(), "VOICE", "VIDEO");
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.startInteractiveCommunication(_receiver, stringField("key"), stringField("type"));
    }
    catch (TerminalException e) { //FIXME exceptions
      System.out.println("erro");
    }
  }
}
