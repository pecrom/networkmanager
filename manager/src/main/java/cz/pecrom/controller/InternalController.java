package cz.pecrom.controller;

import cz.pecrom.controller.main.*;
import cz.pecrom.model.*;
import cz.pecrom.view.*;

import java.beans.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 15:45
 */
public class InternalController extends AbstractController {
  protected DesktopController desktop;

  public InternalController(AbstractView view, AbstractModel model,String formClazz, DesktopController mainController) throws ClassNotFoundException {
    super(view, model, formClazz, mainController);
  }


  public DesktopController getDesktop() {
    return desktop;
  }

  public void setDesktop(DesktopController desktop) {
    this.desktop = desktop;
  }

  @Override
  protected void initModel() {

  }

  @Override
  protected Void doInBackground() throws Exception {
    super.doInBackground();
    getMainController().createInternalFrame(this);
    return null;
  }

  @Override
  protected void done() {
    super.done();
    try (InputStream resourceStream = getClass().getResourceAsStream(getClass().getSimpleName()+".xml")) {
      if(resourceStream!=null)
        createMenu(resourceStream);
    } catch (IOException e) {
      getLogger().info("missing file");
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {

  }
}
