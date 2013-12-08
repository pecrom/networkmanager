package cz.pecrom.controller.main;

import cz.pecrom.controller.*;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 14:56
 */
public class DesktopController extends AbstractController {
  public DesktopController(String clazz) throws ClassNotFoundException {
    super(clazz);
  }

  @Override
  protected Void doInBackground() throws Exception {
    view = (JComponent) getViewClazz().newInstance();
    view.setVisible(true);
    view.requestFocus();
    JFrame frame = new JFrame();
    frame.add(view);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(1280,760);
    return null;
  }
}
