package cz.pecrom.controller.main;

import cz.pecrom.controller.*;
import cz.pecrom.model.*;
import cz.pecrom.view.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 16:02
 */
public class AboutController extends InternalController {

  public AboutController(AbstractView view, AbstractModel model, String formClazz, DesktopController mainController) throws ClassNotFoundException {
    super(view, model, formClazz, mainController);
  }

  @Override
  protected void initModel() {

  }
}
