package cz.pecrom.controller;

import cz.pecrom.controller.main.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 15:45
 */
public class InternalController extends AbstractController {
  protected DesktopController desktop;

  public InternalController(String viewClazz, DesktopController mainController) throws ClassNotFoundException {
    super(viewClazz, mainController);
  }


  public DesktopController getDesktop() {
    return desktop;
  }

  public void setDesktop(DesktopController desktop) {
    this.desktop = desktop;
  }

  @Override
  protected Void doInBackground() throws Exception {
    super.doInBackground();
    getMainController().createInternalFrame(this);

    return null;
  }
}
