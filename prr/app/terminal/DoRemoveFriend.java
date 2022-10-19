package prr.app.terminal;


import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.UnknownTerminalException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove friend.
 * Command present in the Terminal's Menu, used to Remove a Terminal as a Friend.
 * When selected asks the User for the Terminal's Key.
 * Throws an Exception when the Terminal associated with the given Key isn't
 * registered in the Network.
 */
class DoRemoveFriend extends TerminalCommand {

  /**
   * Constructor of the Class.
   * Calls the constructor of the SuperClass passing the Command's label, associated
   * Network and associated Terminal as arguments. Furthermore, asks the User for the
   * Terminal's Key.
   * @param context Network associated with the Command.
   * @param terminal Terminal associated with the Command.
   */
  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("friendId", Message.terminalKey());
  }
  
  /**
   * Execute method, overriding the SuperClass's one.
   * Gets the attributes previously given by the User and tries to remove
   * the Terminal as a Friend, catching an Exception when the Terminal's Key
   * isn't registered.
   * @throws UnknownTerminalKeyException Exception thrown when the
   * Key of the Terminal isn't registered in the Network
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
