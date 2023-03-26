package task;

import task.view.MainView;
import org.apache.log4j.BasicConfigurator;
import javax.swing.JFrame;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        MainView mainView = new MainView();
        mainView.setTitle("Main menu");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth()/2 - mainView.getWidth()) / 2);
        int y = (int) ((dimension.getHeight()/2 - mainView.getHeight()) / 2);
        mainView.setLocation(x, y);
        mainView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainView.setSize(400,200);
        mainView.setVisible(true);
    }
}
