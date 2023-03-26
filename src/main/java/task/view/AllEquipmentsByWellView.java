package task.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

public class AllEquipmentsByWellView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private Object[][] array = null;
    private Object[] columnsHeader = new String[]{"Well Name", "Number of equipment"};
    private ArrayList<String> resultList;

    public AllEquipmentsByWellView(ArrayList<String> resultList) {
        this.resultList = resultList;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        array = new String[resultList.size()][2];
        for (int i = 0; i < resultList.size(); i++) {
            String[] temp = resultList.get(i).split(",");
            array[i] = temp;
        }
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        for (int i = 0; i < array.length; i++)
            tableModel.addRow(array[i]);
        resultTable = new JTable(tableModel);
    }
}
