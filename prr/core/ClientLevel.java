package prr.core;

enum ClientLevel {
  NORMAL(10, 16, 2, 20, 30),
  GOLD(10, 10, 2, 10, 20),
  PLATINUM(0, 4, 4, 10, 10);

  private final int _text1;
  private final int _text2;
  private final int _text3;
  private final int _voice;
  private final int _video;

  ClientLevel(int text1, int text2, int text3, int voice, int video) {
    _text1 = text1;
    _text2 = text2;
    _text3 = text3;
    _voice = voice;
    _video = video;
  }

  public int getText1() {
    return _text1;
  }

  public int getText2() {
    return _text2;
  }

  public int getText3() {
    return _text3;
  }

  public int getVoice() {
    return _voice;
  }

  public int getVideo() {
    return _video;
  }
}
