package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;

import prr.core.exception.DuplicateClientException;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.UnknownClientException;
import prr.core.exception.InvalidTerminalException;
import prr.core.exception.UnknownTerminalException;
import prr.core.exception.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  /** Clients of the Network. */
  private Map<String, Client> _clients = new TreeMap<>();

  /**Terminals of the Network. */
  private Map<String, Terminal> _terminals = new TreeMap<>();

  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */

  public void registerClient(String key, String name, int nif) throws DuplicateClientException {
    if (_clients.containsKey(key))
      throw new DuplicateClientException(key);
    Client client = new Client(key, name, nif);
    _clients.put(key, client);
  }

  public Client showClient(String key) throws UnknownClientException {
    if (!_clients.containsKey(key))
      throw new UnknownClientException(key);
    return _clients.get(key);
  }

  public List<Client> showAllClients() {
    List<Client> temp = new ArrayList<>(_clients.values());
    return temp;
  }

  public Terminal registerTerminal(String id, String type, String clientKey) throws DuplicateTerminalException, InvalidTerminalException, UnknownClientException{
    if (_terminals.containsKey(id))
      throw new DuplicateTerminalException(id);
    if (id.length()!=6 || !id.matches("[0-9]+"))
      throw new InvalidTerminalException(id);
    if (!_clients.containsKey(clientKey))
      throw new UnknownClientException(clientKey);

    Client owner = _clients.get(clientKey);

    switch(type) {
      case "BASIC":
        BasicTerminal basicTerminal = new BasicTerminal(id, owner);
        _terminals.put(id, basicTerminal);
        owner.addTerminal(basicTerminal);
        return basicTerminal;
      case "FANCY":
        FancyTerminal fancyTerminal = new FancyTerminal(id, owner);
        _terminals.put(id, fancyTerminal);
        owner.addTerminal(fancyTerminal);
        return fancyTerminal;
    }
    return null;
  }

  public Terminal getTerminal(String id) throws UnknownTerminalException{
    if (!_terminals.containsKey(id))
      throw new UnknownTerminalException(id);
    return _terminals.get(id);
  }

  public List<Terminal> showAllTerminals() {
    List<Terminal> temp = new ArrayList<>(_terminals.values());
    return temp;
  }

  public void addFriend(String terminal, String friend) throws UnknownTerminalException{
    if (!_terminals.containsKey(friend))
      throw new UnknownTerminalException(friend);
      
    _terminals.get(terminal).addTerminalFriend(_terminals.get(friend));
  }

  public void removeFriend(String terminal, String friend) throws UnknownTerminalException{
    if (!_terminals.containsKey(friend))
      throw new UnknownTerminalException(friend);
      
    _terminals.get(terminal).removeTerminalFriend(friend);
  }

  public List<Terminal> getNoActivityTerminals() {
    List<Terminal> list = new ArrayList<>();
    for (Terminal t: _terminals.values())
      if (!t.isActiveTerminal())
        list.add(t);

    return list;
  }

  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
    //FIXME implement method. Done?
  }

  
}

