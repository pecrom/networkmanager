import cz.pecrom.controller.main.*;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 13:35
 */
public class Launcher {
  public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    DesktopController controller = new DesktopController("cz.pecrom.view.main.Desktop_Form");
    controller.execute();
  }
}
