package task.view;

import task.service.WellService;
import task.view.filter.DocumentFilterLettersCommas;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.event.*;
import java.sql.Connection;

public class WellView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textNameWells;
    private WellService wellService = new WellService();
    private Connection connection;

    public WellView(Connection connection) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.connection = connection;
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        ((AbstractDocument) textNameWells.getDocument()).setDocumentFilter(new DocumentFilterLettersCommas());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (wellService.checkWellFields(textNameWells.getText())) {
            if (wellService.parseWellNames(connection, textNameWells.getText()) != 0)
                dispose();
        }
    }

    private void onCancel() {
        dispose();
    }
}
