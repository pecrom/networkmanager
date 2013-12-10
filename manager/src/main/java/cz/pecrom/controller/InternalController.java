package cz.pecrom.controller;

import cz.pecrom.controller.main.*;
import cz.pecrom.model.*;

import javax.swing.*;
import java.beans.*;
import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 15:45
 */
public class InternalController extends AbstractController {
  protected DesktopController desktop;
  protected List<AbstractAction> actions = null;

  public InternalController(String viewClazz, AbstractModel model,DesktopController mainController) throws ClassNotFoundException {
    super(viewClazz, model, mainController);
    actions = new ArrayList<>();
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

  public void addAction(AbstractAction action){
    actions.add(action);
  }

  public void removeAction(AbstractAction action){
    actions.remove(action);
  }

  public List<AbstractAction> getActions(){
    return actions;
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
