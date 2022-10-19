package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.UnknownClientException;
import prr.core.exception.InvalidTerminalException;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  /**
   * @param receiver
   */
  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
    addStringField("clientKey", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      String id = stringField("id");
      String type = stringField("type");
      String clientKey = stringField("clientKey");
      _receiver.registerTerminal(id, type, clientKey);
    }
    catch (DuplicateTerminalException e) {
      throw new DuplicateTerminalKeyException(e.getId());
    }
    catch (InvalidTerminalException e) {
      throw new InvalidTerminalKeyException(e.getId());
    }
    catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
