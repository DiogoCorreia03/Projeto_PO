package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.app.exception.FileOpenFailedException;
import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }
  /**
   * @see pt.tecnico.uilib.menus.Command#execute()
   */
  @Override
  protected final void execute() throws CommandException{
    try {
    _receiver.save();
    }
    catch (MissingFileAssociationException e) {
      try {
        _receiver.saveAs(Form.requestString(Message.newSaveAs()));
      }
      catch (MissingFileAssociationException | IOException e1) {
        throw new FileOpenFailedException(e1);
      }
    }
    catch (FileNotFoundException e) {
      throw new FileOpenFailedException(e);
    }
    catch (IOException e) {
      throw new FileOpenFailedException(e);
    }
  }
}
