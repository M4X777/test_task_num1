package task.view;

import lombok.extern.log4j.Log4j;
import task.config.DBConfig;
import task.service.MainService;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;

@Log4j
public class MainView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton radioButtonAddEquipment;
    private JRadioButton radioButtonInformationWells;
    private JRadioButton radioButtonCreateXML;
    private int radioButtonTrigger = 0;
    private MainService mainService = new MainService();
    private Connection connection = DBConfig.getSQLiteConnection();

    public MainView() {
        log.debug("Call method MainView() in MainView");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonAddEquipment);
        group.add(radioButtonInformationWells);
        group.add(radioButtonCreateXML);

        radioButtonAddEquipment.addActionListener(e -> radioButtonTrigger = 1);
        radioButtonInformationWells.addActionListener(e -> radioButtonTrigger = 2);
        radioButtonCreateXML.addActionListener(e -> radioButtonTrigger = 3);
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        log.debug("Call method onOk() in MainView");
        switch (radioButtonTrigger) {
            case 1:
                mainService.openEquipmentView(connection);
                break;
            case 2:
                mainService.openWellView(connection);
                break;
            case 3:
                mainService.openSaveXMLDialog(connection);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Please, select one function!");
                break;
        }
    }

    private void onCancel() {
        log.debug("Close MainView");
        DBConfig.closeSQLiteConnection();
        dispose();
    }
}
