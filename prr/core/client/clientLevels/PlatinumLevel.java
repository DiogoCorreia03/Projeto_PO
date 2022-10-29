package prr.core.client.clientLevels;

public class PlatinumLevel implements ClientLevel{

    private static PlatinumLevel _platinumLevel;

    private PlatinumLevel(){};

    public double priceSMS(int s) {
        if (s < 50)
            return 0;
        else
            return 4;
    }

    public double priceVideo(int d) {
        return 10 * d;
    } 

    public double priceVoice(int d) {
        return 10 * d;
    }

    public PlatinumLevel getInstance() {
        if (_platinumLevel == null)
            return new PlatinumLevel();

        return _platinumLevel;
    }
}
