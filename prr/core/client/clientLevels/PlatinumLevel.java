package prr.core.client.clientLevels;

import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public class PlatinumLevel implements ClientLevel {

    private static PlatinumLevel _platinumLevel;

    private PlatinumLevel(){};

    public double computeCost(TextCommunication comm) {
        return comm.getSize()<50?0:4;
    }

    public double computeCost(VoiceCommunication comm) {
        return 10 * comm.getSize();
    } 

    public double computeCost(VideoCommunication comm) {
        return 10 * comm.getSize();
    }

    public PlatinumLevel getInstance() {
        if (_platinumLevel == null)
            return new PlatinumLevel();

        return _platinumLevel;
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
