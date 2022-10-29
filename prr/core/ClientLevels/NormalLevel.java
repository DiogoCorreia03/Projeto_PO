package prr.core.ClientLevels;

public class NormalLevel implements ClientLevel{

  private static NormalLevel _normalLevel;

  private NormalLevel() {
    //FIXME é preciso este construtor?
  }
  
  public double priceSMS(String msg) {
    if (msg.length() < 50)
      return 10;
    else if (msg.length() < 100)
      return 16;
    else
      return 2*msg.length();
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
