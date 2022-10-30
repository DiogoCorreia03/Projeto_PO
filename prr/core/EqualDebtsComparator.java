package prr.core;

import java.util.Arrays;
import java.util.Comparator;

import prr.core.client.Client;

public class EqualDebtsComparator implements Comparator<Client> {

    public int compare(Client c1, Client c2) {
        if (c1.getDebts() == c2.getDebts()) {
            String[] ord = {c1.getKey(), c2.getKey()};
            String [] NOrd = {c1.getKey(), c2.getKey()};
            Arrays.sort(ord);

            if (ord == NOrd)
                return -1;
            else
                return 1;
        }
        return 0;
    } 
}
