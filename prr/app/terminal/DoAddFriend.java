package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.exception.UnknownTerminalException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("friendId", Message.terminalKey());
  }
  
  /**
   * @see pt.tecnico.uilib.menus.Command#execute()
   */
  @Override
  protected final void execute() throws CommandException {
    try {
      String friendId = stringField("friendId");
      _network.addFriend(_receiver.getId(), friendId);
    }
    catch (UnknownTerminalException e) {
      throw new UnknownTerminalKeyException(e.getId());
    }
  }
}
