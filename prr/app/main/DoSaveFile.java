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
 * Command present in the Main Menu, used to save the data of the current
 * session on to a File.
 * When selected saves the data in the associated File. If there isn't any File
 * associated, asks the User for the name of a File.
 * Throws Exceptions when there's issues saving the data on the File.
 */
class DoSaveFile extends Command<NetworkManager> {

  /**
   * Constructor of the Class.
   * Calls the constructor of the SuperClass passing the Command's label and
   * associated NetworkManager as arguments.
   * @param receiver NetworkManager associated with the Command.
   */
  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }
  
  /**
   * Execute method, overriding the SuperClass's one.
   * Tries to save the data in the associated File, if there isn't any associated file,
   * asks the user for a File name.
   * Catches Exceptions when there's issues saving the data in the File.
   * @see pt.tecnico.uilib.menus.Command#execute()
   * @throws FileOpenFailedException Exception thrown when there's an issue opening
   * or processing the file.
   * @exception MissingFileAssociationException Exception thrown when there isn't any File associated.
   * @exception FileNotFoundException Exception thrown when the program can't find the specified file.
   * @exception ClassNotFoundException
   * @exception IOException
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
      catch (MissingFileAssociationException e1) {
        throw new FileOpenFailedException(e1);
      }
      catch (IOException e1) {
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
