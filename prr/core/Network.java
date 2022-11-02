package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;

import prr.core.client.Client;
import prr.core.client.DebtsComparator;
import prr.core.communication.Communication;
import prr.core.exception.DuplicateClientException;
import prr.core.exception.DuplicateTerminalException;
import prr.core.exception.UnknownClientException;
import prr.core.exception.InvalidTerminalException;
import prr.core.exception.TerminalBusyException;
import prr.core.exception.TerminalOffException;
import prr.core.exception.TerminalSilenceException;
import prr.core.exception.UnknownTerminalException;
import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;
import prr.core.terminal.BasicTerminal;
import prr.core.terminal.FancyTerminal;
import prr.core.terminal.Terminal;

/**
 * Class Store implements a store.
 * Class that implements all the functionalities of the App.
 * Stores all the relevant Objects and implements methods on them.
 * Can be Serializable to save it's current state and load it on a different session.
 * @author Diogo Correia IST1103198
 * @author João Santos IST1102746
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  /** Clients of the Network. */
  private Map<String, Client> _clients = new TreeMap<>();

  /** Terminals of the Network. */
  private Map<String, Terminal> _terminals = new TreeMap<>();

  private List<Communication> _communications = new ArrayList<>();


  private Client getClient(String key) throws UnknownClientException {
    String keyLower = key.toLowerCase();
    if (!_clients.containsKey(keyLower))
      throw new UnknownClientException(key);
    return _clients.get(keyLower);
  }


  /**
   * Register a Client in the Network.
   * Receives all the attributes needed to register a Client, verifies them and
   * creates a new Client in the Network.
   * @param key Key of the Client.
   * @param name Name of the Client.
   * @param nif TaxNumber of the Client.
   * @throws DuplicateClientException Exception thrown when the Client's Key is
   * already registered in the Network.
   */
  public void registerClient(String key, String name, int nif) throws DuplicateClientException {
    String keyLower = key.toLowerCase();
    if (_clients.containsKey(keyLower))
      throw new DuplicateClientException(key);
    Client client = new Client(key, name, nif);
    _clients.put(keyLower, client);
  }

  /**
   * Show a Client from it's Key.
   * @param key Key of the Client.
   * @return the Client associated with the given Key formatted to a String.
   * @throws UnknownClientException Exception thrown when the given
   * Key isn't registered in the Network.
   */
  public String showClient(String key) throws UnknownClientException {
    return getClient(key).toString();
  }

  /**
   * Get a list of all the Client's in the Network.
   * @return a list of all the Client's in the Network formatted to a String.
   */
  public List<String> showAllClients() {
    List<String> temp = new ArrayList<>();
    for (Client c: _clients.values())
      temp.add(c.toString());
    return temp;
  }

  public boolean enableClientNotifications(String key) throws UnknownClientException {
    Client c = getClient(key);
    return  c.enableNotifications();
  }

  public boolean disableClientNotifications(String key) throws UnknownClientException {
    Client c = getClient(key);
    return  c.disableNotifications();
  }

  public double getClientPayments(String key) throws UnknownClientException{
    Client c = getClient(key);
    return c.getPayments();
  }

  public double getClientDebts(String key) throws UnknownClientException{
    Client c = getClient(key);
    return c.getDebts();
  }

  public List<String> showClientsWithDebts() { //FIXME ordenacao
    List<Client> list = new ArrayList<>(_clients.values());
    Collections.sort(list, new DebtsComparator());
    List<String> ordered = new ArrayList<>();

    for (Client c : list) {
      ordered.add(c.toString());
    }

    return ordered;
  }

  public List<String> showClientsWithoutDebts() {
    List<String> temp = new ArrayList<>();
    
    for (Client c : _clients.values())
      if (c.getDebts() == 0)
        temp.add(c.toString());

    return temp;
  }

  /**
   * Register a Terminal in the Network.
   * Receives all the attributes needed to register a Terminal, verifies them and
   * creates a new Terminal in the Network.
   * @param id Key of the Terminal.
   * @param type Type of the Terminal.
   * @param clientKey Key of the Owner of the Terminal.
   * @return the instance of the registered Terminal
   * @throws DuplicateTerminalException Exception thrown when the Terminal's Key is
   * already registered in the Network.
   * @throws InvalidTerminalException Exception thrown when Terminal's doesn't match the specified format.
   * @throws UnknownClientException Exception thrown when the Owner's Key isn't registered in the Network.
   */
  public Terminal registerTerminal(String id, String type, String clientKey) throws DuplicateTerminalException, InvalidTerminalException, UnknownClientException{
    String keyLower = clientKey.toLowerCase();
    if (_terminals.containsKey(id))
      throw new DuplicateTerminalException(id);
    if (id.length()!=6 || !id.matches("[0-9]+"))
      throw new InvalidTerminalException(id);
    if (!_clients.containsKey(keyLower))
      throw new UnknownClientException(clientKey);

    Client owner = _clients.get(keyLower);

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

  /**
   * Get a Terminal from it's Key.
   * @param id Key of the Terminal.
   * @return the Terminal associated with the given Key.
   * @throws UnknownTerminalException Exception thrown when the given Key isn't
   * registered in the Network.
   */
  public Terminal getTerminal(String id) throws UnknownTerminalException{
    if (!_terminals.containsKey(id))
      throw new UnknownTerminalException(id);
    return _terminals.get(id);
  }

  /**
   * Get a list of all the Terminals in the Network.
   * @return a list of all the Terminals in the Network.
   */
  public List<String> showAllTerminals() {
    List<String> temp = new ArrayList<>();
    for (Terminal t: _terminals.values())
      temp.add(t.toString());
    return temp;
  }

  /**
   * Add a friend to a specified Terminal.
   * Checks if the to be added friend's Key is registered in the Network. If
   * it is, adds said terminal to the friends list.
   * @param terminal Key of the Terminal on which the friend is getting added.
   * @param friend Key of the Terminal that is getting added as a friend.
   * @throws UnknownTerminalException Exception thrown when the Key for the terminal
   * to be added as a friend isn't registered in the Network.
   */
  public void addFriend(String terminal, String friend) throws UnknownTerminalException{
    if (!_terminals.containsKey(friend) || !_terminals.containsKey(terminal))
      throw new UnknownTerminalException(friend);
      
    _terminals.get(terminal).addTerminalFriend(_terminals.get(friend));
  }

  /**
   * Remove a friend from a specified Terminal.
   * Checks if the to be removed friend's Key is registered in
   * the Network. If it is, removes it from the specified Terminal's
   * friends list.
   * @param terminal Key of the Terminal from which the friend is getting removed.
   * @param friend Key of the Terminal that is getting removed as a friend.
   * @throws UnknownTerminalException Exception thrown when the Key for the terminal
   * to be removed as a friend isn't registered in the Network.
   */
  public void removeFriend(String terminal, String friend) throws UnknownTerminalException{
    if (!_terminals.containsKey(friend))
      throw new UnknownTerminalException(friend);
      
    _terminals.get(terminal).removeTerminalFriend(friend);
  }

  /**
   * Iterate through all the Terminals in the Network and check if
   * they have done any past Communications. If they haven't then
   * they get added to the Array to be returned.
   * @return an Array of all the Terminals with no past Communications.
   */
  public List<String> getNoActivityTerminals() {
    List<String> list = new ArrayList<>();
    for (Terminal t: _terminals.values())
      if (!t.isActiveTerminal())
        list.add(t.toString());

    return list;
  }


  public List<String> showTerminalsWithPositiveBalance() {
    List<String> temp = new ArrayList<>();
    
    for (Terminal t : _terminals.values()) {
      if (t.getPayments() > t.getDebt())
        temp.add(t.toString());
    }

    return temp;
  }

  public List<String> getAllComms() {
    List<String> list = new ArrayList<>();
    for (Communication c: _communications)
      list.add(c.toString());
    return list;
  }

  public void sendTextCommunication(Terminal origin, String receiverId, String msg) throws TerminalOffException, UnknownTerminalException{
    Terminal receiver = getTerminal(receiverId);
    _communications.add(origin.makeSMS(receiver, msg, _communications.size()+1));
  }


  public void startInteractiveCommunication(Terminal origin, String receiverId, String type) throws TerminalOffException, TerminalBusyException, TerminalSilenceException, UnsupportedAtOriginException, UnsupportedAtDestinationException, UnknownTerminalException{
    Terminal receiver = _terminals.get(receiverId);
    Communication interactiveComm = null;
    switch (type) {
      case "VOICE" -> interactiveComm = origin.makeVoiceCall(receiver, _communications.size()+1);
      case "VIDEO" -> interactiveComm = origin.makeVideoCall(receiver, _communications.size()+1);
    }
    _communications.add(interactiveComm);
  }

  public List<String> showCommunicationsfromClient(String id) throws UnknownClientException { //FIXME order errada?
      List<String> temp = new ArrayList<>();
      Client c = getClient(id);
        for (Terminal t : c.getTerminals()) {
            temp.addAll(t.getMadeCommunications());
          }
      return temp;  
  }

  public List<String> showCommunicationsToClient(String id) throws UnknownClientException {
      List<String> temp = new ArrayList<>();
      Client c = getClient(id);
        for (Terminal t : c.getTerminals()) {
            temp.addAll(t.getReceivedCommunications());
          }
      return temp;    
  }


  public double showGlobalPayments() {
    double sum_payments = 0;
    for (Client c : _clients.values())
        sum_payments += c.getPayments();

    return sum_payments;
  }

  public double showGlobalDebts() {
    double sum_debts = 0;
    for (Client c : _clients.values())
        sum_debts += c.getDebts();

    return sum_debts;
  }

  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }  
}

