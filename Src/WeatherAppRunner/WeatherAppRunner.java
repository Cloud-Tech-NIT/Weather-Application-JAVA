package Src.WeatherAppRunner;

import Src.AppUI.TerminalUI;
import Src.BusinessLogic.DUIFiller;
import Src.BusinessLogic.TerminalUI.TUI_DB;
import Src.BusinessLogic.TerminalUI.TUI_Txt;
import Src.AppUI.App;

public class WeatherAppRunner {

  WeatherAppRunner(String cmd) {

    if (cmd.equals("TUI_TXT")) {
      this.RunTUI_TXT();
    } else if (cmd.equals("TUI_SQL")) {
      this.RunTUI_SQL();
    } else if (cmd.equals("DUI_SQL")) {
      this.RunDUI_SQL();
    } else if (cmd.equals("DUI_TXT")) {
      this.RunDUI_TXT();
    }
  }

  private String DBType;

  public void RunTUI_TXT() {
    DBType = "txt";
    TUI_Txt txt = new TUI_Txt();
    TerminalUI terminal = new TerminalUI(txt);
    terminal.RunF();
    System.out.println("TUI_TXT");
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

    WeatherAppRunner AppRunner = new WeatherAppRunner(args[0]);
    // AppRunner.RunDUI_TXT();
    // AppRunner.RunTUI_TXT();
    // AppRunner.RunTUI_SQL();
  }
}
