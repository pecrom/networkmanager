package cz.pecrom.controller;

import cz.pecrom.controller.main.*;
import cz.pecrom.ui.menu.*;

import javax.swing.*;
import javax.xml.bind.*;
import java.awt.event.*;
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
public abstract class AbstractController extends SwingWorker<Void, Void> {
  protected Class viewClazz;
  protected JComponent view;
  protected String resourcePath = null;
  protected JMenuBar menuBar = null;
  protected static Logger logger = null;
  protected DesktopController mainController = null;

  public AbstractController(String viewClazz, DesktopController mainController) throws ClassNotFoundException {
    setViewClazz(Class.forName(viewClazz));
    setMainController(mainController);
    this.execute();
  }

  public AbstractController(String viewClazz, DesktopController mainController, String resourcePath) throws ClassNotFoundException {
    setViewClazz(Class.forName(viewClazz));
    setMainController(mainController);
    setResourcePath(resourcePath);
    this.execute();
  }

  public DesktopController getMainController() {
    return mainController;
  }


  public void setMainController(DesktopController mainController) {
    this.mainController = mainController;
  }

  public Class getViewClazz() {
    return viewClazz;
  }

  public void setViewClazz(Class viewClazz) {
    this.viewClazz = viewClazz;
  }

  public JComponent getView() {
    return view;
  }

  public void setView(JComponent view) {
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

      ((JInternalFrame) getView()).setJMenuBar(menuBar);
    } catch (JAXBException e) {
      getLogger().info("Cannot parse class");
    }
  }

  protected JMenu createMainMenu(JMenuBar menuBar, String label) {
    JMenu menu = new JMenu(label);
    menuBar.add(menu);
    return menu;
  }

  public String getResourcePath() {
    return resourcePath;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  protected void createAppMenuItems(JMenu parent, List<ApplicationItem> applicationItems) {
    if (!applicationItems.isEmpty())
      for (final ApplicationItem app : applicationItems) {
        JMenuItem appItem = new JMenuItem(app.getLabel());
        appItem.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              Class clazzController = Class.forName(app.getController());
              InternalController controller = (InternalController) clazzController.getDeclaredConstructor(String.class,DesktopController.class).newInstance(app.getView(), getMainController());
              controller.execute();
            } catch (ClassNotFoundException e1) {
              getLogger().info("Cannot instatiate new internal frame");
            } catch (InstantiationException | IllegalAccessException e1) {
              getLogger().info("Cannot instatiate new controller");
            } catch (NoSuchMethodException e1) {
              e1.printStackTrace();
            } catch (InvocationTargetException e1) {
              e1.printStackTrace();
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
    view = (JComponent) getViewClazz().newInstance();
    if (getResourcePath() != null) {
      try (InputStream resourceStream = getClass().getResourceAsStream(getResourcePath())) {
        createMenu(resourceStream);
      }
    }

    view.setVisible(true);
    view.requestFocus();
    return null;
  }
}
