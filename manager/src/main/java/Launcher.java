import cz.pecrom.controller.main.*;
import cz.pecrom.view.main.*;

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
    DesktopView view = new DesktopView();
    DesktopController controller = new DesktopController(view,"cz.pecrom.view.main.form.Desktop_Form");
//    controller.execute();
  }
}
