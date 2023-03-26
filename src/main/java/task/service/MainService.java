package task.service;

import lombok.extern.log4j.Log4j;
import task.view.AllEquipmentsByWellView;
import task.view.EquipmentView;
import task.view.WellView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

@Log4j
public class MainService {

    private XMLService xmlService = new XMLService();

    public void openEquipmentView(Connection connection) {
        log.debug("Call method openEquipmentView()");
        EquipmentView equipmentView = new EquipmentView(connection);
        equipmentView.setTitle("Add new well and equipments");
        equipmentView.setSize(470,150);
        placeOnCenterScreen(equipmentView);
    }

    public void openWellView(Connection connection) {
        log.debug("Call method openWellView()");
        WellView wellView = new WellView(connection);
        wellView.setTitle("Enter well names");
        wellView.setSize(400,120);
        placeOnCenterScreen(wellView);
    }

    public void openAllEquipmentsByWellView(ArrayList<String> resultList) {
        log.debug("Call method openAllEquipmentsByWellView()");
        AllEquipmentsByWellView allEquipmentsByWellView = new AllEquipmentsByWellView(resultList);
        allEquipmentsByWellView.setTitle("Information about wells");
        allEquipmentsByWellView.setSize(400,200);
        placeOnCenterScreen(allEquipmentsByWellView);
    }

    public void openSaveXMLDialog(Connection connection) {
        log.debug("Call method openSaveXMLDialog()");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
        fileChooser.addChoosableFileFilter(xmlFilter);
        fileChooser.setFileFilter(xmlFilter);

        int result = fileChooser.showSaveDialog(null);

        if (result == fileChooser.APPROVE_OPTION) {
            xmlService.saveToXML(connection, fileChooser.getSelectedFile());
            JOptionPane.showMessageDialog(null, "The file was created successfully!");
        }
    }

    private void placeOnCenterScreen(JDialog dialog) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth()/2 - dialog.getWidth()) / 2);
        int y = (int) ((dimension.getHeight()/2 - dialog.getHeight()) / 2);
        dialog.setLocation(x, y);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}
