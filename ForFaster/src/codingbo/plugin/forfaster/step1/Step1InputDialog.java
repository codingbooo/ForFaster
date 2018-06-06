package codingbo.plugin.forfaster.step1;

import javax.swing.*;
import java.awt.event.*;

public class Step1InputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfFrom;
    private JTextField tfTo;
    private JTextField tfToFileName;
    private JTextField tfToDirPrefix;
    private JLabel LabelTips;
    private OnOkListener listener;

    public Step1InputDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        String from = tfFrom.getText();
        String to = tfTo.getText();
        String toDirPrefix = tfToDirPrefix.getText();
        String toFileName = tfToFileName.getText();

        if (!textIsEmpty(from)
                || !textIsEmpty(to)
                || !textIsEmpty(toFileName)) {
            LabelTips.setText("字段不能为空");

            return;
        }

        System.out.println("from = " + from);
        System.out.println("to = " + to);
        System.out.println("toDirPrefix = " + toDirPrefix);
        System.out.println("toFileName = " + toFileName);

        if (listener != null) {
            listener.onOk(from, to, toDirPrefix, toFileName);
        }

        dispose();
    }


    private boolean textIsEmpty(String txt) {
        return txt != null && txt.length() > 0;
    }

    public void setOkListener(OnOkListener listener) {
        this.listener = listener;
    }


    public interface OnOkListener {
        void onOk(String from, String to, String toDirPrefix, String toFileName);
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void main(String[] args) {
        Step1InputDialog dialog = new Step1InputDialog();
        dialog.setOkListener(Step1::copyFile);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
