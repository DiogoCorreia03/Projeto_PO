package prr.core.client.clientLevels;

import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public class GoldLevel implements ClientLevel {
    
    private static GoldLevel _goldLevel;

    private GoldLevel(){}

    public double computeCost(TextCommunication comm) {
        int size = comm.getSize();

        return size<100?10:size*2;
    }

    public double computeCost(VoiceCommunication comm) {
        return 10 * ( (double) comm.getSize()/60);
    }

    public double computeCost(VideoCommunication comm) {
        return 20 * ( (double) comm.getSize()/60);
    }

    public static GoldLevel getInstance() {
        if (_goldLevel == null)
          _goldLevel = new GoldLevel();
    
        return _goldLevel;
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
