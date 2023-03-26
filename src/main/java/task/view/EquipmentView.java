package task.view;

import task.model.Well;
import task.service.EquipmentService;
import task.view.filter.DocumentFilterOnlyLetters;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.event.*;
import java.sql.Connection;

public class EquipmentView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textAmountEquipment;
    private JTextField textWellName;
    private EquipmentService equipmentService = new EquipmentService();
    private Connection connection;

    public EquipmentView(Connection connection) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.connection = connection;
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        textAmountEquipment.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                } else {
                    JOptionPane.showMessageDialog(null, "Only numbers are allowed!");
                    textAmountEquipment.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (textAmountEquipment.getText().length() > 0) {
                    int intVal = Integer.parseInt(textAmountEquipment.getText());
                    if (intVal > 50  || intVal < 1) {
                        JOptionPane.showMessageDialog(null, "Enter a number from 1 to 50!");
                        textAmountEquipment.setText("");
                    }
                }
            }
        });
        ((AbstractDocument) textWellName.getDocument()).setDocumentFilter(new DocumentFilterOnlyLetters());
    }

    private void onOK() {
        if (equipmentService.checkEquipmentFields(textAmountEquipment.getText(), textWellName.getText())) {
            Well well = equipmentService.createWellEquipment(connection, textAmountEquipment.getText(), textWellName.getText());
            if (well != null) {
                dispose();
            }
        }
    }

    private void onCancel() {
        dispose();
    }
}
