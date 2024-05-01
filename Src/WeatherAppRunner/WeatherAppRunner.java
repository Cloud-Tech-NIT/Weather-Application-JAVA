package Src.WeatherAppRunner;

public class WeatherAppRunner {

  WeatherAppRunner() {
  }

  private String DBType;

  public void RunTUI_TXT() {
    DBType = "txt";
  }

  public void RunTUI_SQL() {
    DBType = "SQL";
  }

  public void RunDUI_TXT() {
    DBType = "txt";
  }

  public void RunDUI_SQL() {
    DBType = "SQL";
  }

  public static void main(String[] args) {

    WeatherAppRunner AppRunner = new WeatherAppRunner();

    AppRunner.RunDUI_SQL();
    AppRunner.RunDUI_TXT();
    AppRunner.RunTUI_TXT();
    AppRunner.RunTUI_SQL();
  }
}