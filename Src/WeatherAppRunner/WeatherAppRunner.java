package Src.WeatherAppRunner;

import Src.AppUI.TerminalUI;
import Src.BusinessLogic.TerminalUI.TUI_DB;
import Src.BusinessLogic.TerminalUI.TUI_Txt;
import Src.AppUI.Screen3Controller;
import Src.AppUI.mainscreenController;
import Src.AppUI.mainscreen2controller;
import Src.AppUI.App;
import Src.AppUI.App2;

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

  WeatherAppRunner() {
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
    DBType = "Txt";
    mainscreen2controller c1 = new mainscreen2controller(DBType);
    // c1.setdb(DBType);
    App2 app = new App2();
    app.startWithController(DBType, c1);
  }

  public void RunDUI_SQL() {
    DBType = "SQL";
    mainscreenController c1 = new mainscreenController(DBType);
    // c1.setdb(DBType);
    App app = new App();
    app.startWithController(DBType, c1);
  }

  public static void main(String[] args) {
    WeatherAppRunner AppRunner = new WeatherAppRunner(args[0]);
  }
}
