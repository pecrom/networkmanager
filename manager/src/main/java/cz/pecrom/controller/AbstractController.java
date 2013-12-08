package cz.pecrom.controller;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 14:48
 */
public abstract class AbstractController extends SwingWorker<Void, Void>{
  protected Class viewClazz;
  protected JComponent view;
  public AbstractController(String clazz) throws ClassNotFoundException {
    setViewClazz(Class.forName(clazz));
    this.execute();
  }

  public Class getViewClazz() {
    return viewClazz;
  }

  public void setViewClazz(Class viewClazz) {
    this.viewClazz = viewClazz;
  }

  @Override
  protected Void doInBackground() throws Exception {
    view = (JComponent) getViewClazz().newInstance();
    view.setVisible(true);
    view.requestFocus();
    return null;
  }
}
