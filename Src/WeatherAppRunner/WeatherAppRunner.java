package Src.WeatherAppRunner;

import Src.BusinessLogic.DUIFiller;
import Src.BusinessLogic.TUIFiller;

public class WeatherAppRunner {
  public static void main(String[] args) {
    // IF TerminalUI
    char input = 'T';

    if (input == 'T') {
      TUIFiller TUI = new TUIFiller();
      TUI.RunTerminal();
    } else {
      // DUIFiller DesktopUI = new DesktopUI();
    }

  }

}
