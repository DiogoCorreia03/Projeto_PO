package prr.core;

import java.util.Comparator;

import prr.core.client.Client;

public class DebtsComparator implements Comparator<Client> {
    public int compare(Client c1, Client c2) {
        return Double.compare(c2.getDebts(), c1.getDebts());
    }
}
