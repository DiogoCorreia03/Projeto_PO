package prr.core.communication;

import java.io.Serializable;
import java.util.Comparator;

public class CommsComparator implements Comparator<Communication>, Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    public int compare(Communication c1, Communication c2) {
        return c1.getId() - c2.getId();
    }
}
