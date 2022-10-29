package prr.core.client.clientLevels;

public class NormalLevel implements ClientLevel{

  private static NormalLevel _normalLevel;

  private NormalLevel() {
    //FIXME é preciso este construtor?
  }
  
  public double priceSMS(int s) {
    if (s < 50)
      return 10;
    else if (s < 100)
      return 16;
    else
      return 2*s;
  }

  public double priceVoice(int d) {
    return 20*d;
  }

  public double priceVideo(int d) {
    return 30*d;
  }

  public static NormalLevel getInstance() {
    if (_normalLevel == null)
      _normalLevel = new NormalLevel();

    return _normalLevel;
  }

}
