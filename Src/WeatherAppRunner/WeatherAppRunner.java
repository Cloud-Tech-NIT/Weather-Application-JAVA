package Src.WeatherAppRunner;

import Src.AppUI.TerminalUI;
import Src.BusinessLogic.TerminalUI.TUI_DB;
import Src.BusinessLogic.TerminalUI.TUI_Txt;

public class WeatherAppRunner {

  WeatherAppRunner() {
  }

  private String DBType;

  public void RunTUI_TXT() {
    DBType = "txt";
    TUI_Txt  txt = new TUI_Txt();
    TerminalUI terminal = new TerminalUI(txt);
    terminal.RunF();
  }

  public void RunTUI_SQL() {
    DBType = "SQL";
    TUI_DB db = new TUI_DB();
    TerminalUI terminal = new TerminalUI(db);
    terminal.RunF();
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