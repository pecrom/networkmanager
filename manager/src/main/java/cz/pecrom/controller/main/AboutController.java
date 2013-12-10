package cz.pecrom.controller.main;

import cz.pecrom.controller.*;
import cz.pecrom.model.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 16:02
 */
public class AboutController extends InternalController {

  public AboutController(String viewClazz, AbstractModel model, DesktopController mainController) throws ClassNotFoundException {
    super(viewClazz, model, mainController);
  }

  @Override
  protected void initModel() {

  }
}
