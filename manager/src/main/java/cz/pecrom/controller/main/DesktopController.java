package cz.pecrom.controller.main;

import cz.pecrom.controller.*;
import cz.pecrom.ui.menu.*;
import cz.pecrom.view.main.*;

import javax.swing.*;
import javax.xml.bind.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: pecrom
 * Date: 8.12.13
 * Time: 14:56
 */
public class DesktopController extends AbstractController {
  protected JFrame mainFrame = null;
  public DesktopController(String clazz) throws ClassNotFoundException {
    super(clazz, null);
    setMainController(this);
  }

  public void createInternalFrame(AbstractController controller){
    ((Desktop_Form)getView()).addInternalFrame(controller.getView());
    getLogger().info("instatiating new internal frame");
  }

  protected JFrame getMainFrame() {
    return mainFrame;
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
      getMainFrame().setJMenuBar(menuBar);
    } catch (JAXBException e) {
      getLogger().info("Cannot parse class");
    }
  }


  @Override
  protected Void doInBackground() throws Exception {
    view = (JComponent) getViewClazz().newInstance();
    view.setVisible(true);
    view.requestFocus();
    mainFrame = new JFrame();

    mainFrame.add(view);
    System.out.println(getClass().getSimpleName()+".xml");
    try(InputStream is =getClass().getResourceAsStream(getClass().getSimpleName()+".xml")){
      createMenu(is);
    }
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainFrame.setSize(1280,760);
    return null;
  }
}
