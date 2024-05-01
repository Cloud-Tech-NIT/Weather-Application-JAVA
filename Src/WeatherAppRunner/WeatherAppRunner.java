package Src.WeatherAppRunner;

import Src.AppUI.App;
import Src.AppUI.TerminalUI;
import Src.BusinessLogic.DUIFiller;
import Src.BusinessLogic.TerminalUI.TUI_Txt;

public class WeatherAppRunner {
  public static void main(String[] args) {
    char input = 'D';

    if (input == 'T') {
      TUI_Txt TUI = new TUI_Txt();
      TerminalUI terminal = new TerminalUI();
      terminal.RunF();
    } else if (input == 'D') {
      launchAppUI();
    } else {
      System.out.println("Invalid input. Please enter 'T' or 'D'.");
    }

  }

  private static void launchAppUI() {
    // Launch JavaFX Application (AppUI)
    App.launch(App.class);
  }

}
