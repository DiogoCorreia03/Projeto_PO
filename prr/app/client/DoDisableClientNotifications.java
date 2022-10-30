package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownClientException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("key",Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      String key = stringField("key");
      if (!_receiver.disableClientNotifications(key))
        _display.popup(Message.clientNotificationsAlreadyDisabled());
    }
    catch (UnknownClientException e) {
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
