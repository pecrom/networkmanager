package cz.pecrom.controller;

import cz.pecrom.controller.main.*;
import cz.pecrom.model.*;
import cz.pecrom.ui.menu.*;
import cz.pecrom.view.*;

import javax.swing.*;
import javax.xml.bind.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 14:48
 */
public abstract class AbstractController extends SwingWorker<Void, Void> implements PropertyChangeListener {
  protected Class formClazz;
  protected AbstractView view;
  protected JMenuBar menuBar = null;
  protected static Logger logger = null;
  protected DesktopController mainController = null;
  protected AbstractModel model;


  public AbstractController(AbstractView view, AbstractModel model, String formClazz, DesktopController mainController) throws ClassNotFoundException {
    setFormClazz(Class.forName(formClazz));
    setMainController(mainController);
    setModel(model);
    setView(view);
    this.execute();
  }

  protected abstract void initModel();



  public AbstractModel getModel() {
    return model;
  }

  public void setModel(AbstractModel model) {
    this.model = model;
  }

  public DesktopController getMainController() {
    return mainController;
  }


  public void setMainController(DesktopController mainController) {
    this.mainController = mainController;
  }

  public Class getFormClazz() {
    return formClazz;
  }

  public void setFormClazz(Class formClazz) {
    this.formClazz = formClazz;
  }

  public AbstractView getView() {
    return view;
  }

  public void setView(AbstractView view) {
    this.view = view;
  }

  protected Logger getLogger() {
    if (logger == null)
      logger = Logger.getLogger("NetworkManager");
    return logger;
  }



  protected void createMenu(InputStream resource) {
    try {
      JAXBContext context = JAXBContext.newInstance(Menu.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      Menu menu = (Menu) unmarshaller.unmarshal(resource);
      menuBar = new JMenuBar();

      for (MenuItem item : menu.getMenuItem()) {
        JMenu mainMenuItem = createMainMenu(menuBar, item.getLabel());
        createMenuItems(mainMenuItem, item.getChildren());
        createAppMenuItems(mainMenuItem, item.getApplication());
      }
      SwingUtilities.getRootPane(getView().getContent()).setJMenuBar(menuBar);

    } catch (JAXBException e) {
      getLogger().info("Can not parse class");
    }
  }

  protected JMenu createMainMenu(JMenuBar menuBar, String label) {
    JMenu menu = new JMenu(label);
    menuBar.add(menu);
    return menu;
  }


  protected void createAppMenuItems(JMenu parent, List<ApplicationItem> applicationItems) {
    if (!applicationItems.isEmpty())
      for (final ApplicationItem app : applicationItems) {
        JMenuItem appItem = new JMenuItem(app.getLabel());
        appItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              Class clazzModel = Class.forName(app.getModel());
              AbstractModel model = (AbstractModel)  clazzModel.newInstance();

              Class clazzView = Class.forName(app.getView());
              AbstractView view = (AbstractView) clazzView.newInstance();

              Class clazzController = Class.forName(app.getController());
              getLogger().info("instatiating new internal frame");
              InternalController controller = (InternalController) clazzController.getDeclaredConstructor(AbstractView.class, AbstractModel.class, String.class, DesktopController.class).newInstance(view,model,app.getForm(), getMainController());
              view.setController(controller);
              controller.execute();

            } catch (ClassNotFoundException e1) {
              getLogger().info("Cannot instatiate new internal frame");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
              getLogger().info("Can not instatiate new controller");
            }
          }
        });
        parent.add(appItem);
      }

  }

  protected void createMenuItems(JMenu parent, List<MenuItem> menuItems) {
    if (!menuItems.isEmpty()) {
      for (MenuItem menuItem : menuItems) {
        JMenuItem item = new JMenuItem(menuItem.getLabel());
        parent.add(item);
        createMenuItems((JMenu) item, menuItem.getChildren());
        createAppMenuItems(parent, menuItem.getApplication());
      }
    }
  }

  @Override
  protected Void doInBackground() throws Exception {
    JComponent form = (JComponent) getFormClazz().getDeclaredConstructor().newInstance();
    getView().setContent(form);
    getView().getContent().setVisible(true);
    getView().getContent().requestFocus();
    view.addPropertyChangeListener(this);
    initModel();
    return null;
  }
}
