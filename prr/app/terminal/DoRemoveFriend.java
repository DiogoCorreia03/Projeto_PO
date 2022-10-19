package prr.app.terminal;


import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.UnknownTerminalException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("friendId", Message.terminalKey());
  }
  
  /**
   * @see pt.tecnico.uilib.menus.Command#execute()
   */
  @Override
  protected final void execute() throws CommandException {
    try {
      String friendId = stringField("friendId");
      _network.removeFriend(_receiver.getId(), friendId);
    }
    catch (UnknownTerminalException e) {
      throw new UnknownTerminalKeyException(e.getId());
    }
  }
}
