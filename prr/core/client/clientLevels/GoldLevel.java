package prr.core.client.clientLevels;

public class GoldLevel implements ClientLevel {
    
    private static GoldLevel _goldLevel;

    private GoldLevel(){}

    public double priceSMS(int s) {
        if (s < 100)
            return 10;
        else
            return s * 2;
    }

    public double priceVoice(int d) {
        return 10 * d;
    }

    public double priceVideo(int d) {
        return 20 * d;
    }

    public static GoldLevel getInstance() {
        if (_goldLevel == null)
          _goldLevel = new GoldLevel();
    
        return _goldLevel;
      }
}
