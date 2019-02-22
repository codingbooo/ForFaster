package codingbo.plugin.forfaster.step1;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;

/**
 * <html>
 * <body> 复制图标功能
 * <br> 将一个本地图片文件处理成分辨率为192*192, 144*144, 96*96, 72*72, 48*48的图标文件,复制到指定的文件夹的xxxhdpi,xxhdpi,xhdpi,hdpi,mdpi文件夹中
 * <br> From : 待复制文件绝对路径(如:D:\test\icon.png)
 * <br> To: 目标文件绝对路径(如:D:\test\to\)
 * <br> ToDirPrefix: 目标文件路径前缀(如: mipmap或drawable)
 * <br> ToFileName: 目标文件名称(例如:ic_launch.png)
 * <br>
 * <body>
 * </html>
 */
public class Step1InputDialog extends JDialog {
    public static final String STR_CONFIRM = "确定";
    public static final String STR_REVOKE = "撤销";
    public static final String STR_FINISH = "完成";

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfFrom;
    private JTextField tfTo;
    private JTextField tfToFileName;
    private JTextField tfToDirPrefix;
    private JLabel LabelTips;
    private JCheckBox cbCutRound;

    public Step1InputDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        cbCutRound.setSelected(true);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onFinish());

        // call onFinish() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onFinish();
            }
        });
        // call onFinish() on ESCAPE
        contentPane.registerKeyboardAction(e -> onFinish(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (STR_CONFIRM.equals(buttonOK.getText())) {
            replace();
        } else {
            revoke();
        }
    }

    private void revoke() {
        setStatus("撤销中...");
        ImageUtils.Companion.revoke();
        setStatus("撤销完成!请重新操作");
        buttonOK.setText(STR_CONFIRM);
    }

    private void replace() {
        String from = tfFrom.getText().trim();
        String to = tfTo.getText();
        String toDirPrefix = tfToDirPrefix.getText();
        String toFileName = tfToFileName.getText();
        boolean cutRound = cbCutRound.isSelected();
        if (textIsEmpty(from)
                || textIsEmpty(to)
                || textIsEmpty(toFileName)) {
            setStatus("字段不能为空");
            return;
        }

        System.out.println("from = " + from);
        System.out.println("to = " + to);
        System.out.println("toDirPrefix = " + toDirPrefix);
        System.out.println("toFileName = " + toFileName);
        setStatus("图片生成中...");
        ImageUtils.Companion.bigBang(from, to, toDirPrefix, toFileName, cutRound, new ImageUtils.Callback() {
            @Override
            public void finish() {
                setStatus("完成");
                buttonOK.setText(STR_REVOKE);
            }

            @Override
            public void failed(@Nullable String msg) {
                setStatus("失败:" + msg);
            }
        });
    }

    private void setStatus(String content) {
        LabelTips.setText(content);
    }

    private boolean textIsEmpty(String txt) {
        return txt == null || txt.length() <= 0;
    }

    private void onFinish() {
        cleanCache();
        dispose();
    }

    private void cleanCache() {
        //清除缓存等数据
        setStatus("删除缓存中...");
        ImageUtils.Companion.deleteOldFiles();
        setStatus("删除缓存完成");
    }

    public static void main(String[] args) {
        Step1InputDialog dialog = new Step1InputDialog();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
