package codingbo.plugin.forfaster.step1;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import groovy.lang.GroovyShell;

import java.io.*;

/**
 * Created by bob on 18.5.29.
 */
public class Step1 extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
//        Messages.showMessageDialog("Hello World !", "forFaster1111", Messages.getInformationIcon());
        Step1InputDialog inputDialog = new Step1InputDialog();
        inputDialog.setOkListener((from, to, toFileName) -> {
            System.out.println("from:" + from);
            System.out.println("to:" + to);
            System.out.println("toFileName:" + toFileName);

            copyFile(from, to, toFileName);

        });
        inputDialog.setTitle("ForFaster_Step1");
        inputDialog.pack();
        inputDialog.setLocationRelativeTo(null);
        inputDialog.setVisible(true);
    }


    private static void copyFile(String from, String to, String toFileName) {

        copyFile(from + "/192.png", to + "/xxxdpi/" + toFileName);
        copyFile(from + "/144.png", to + "/xxdpi/" + toFileName);
        copyFile(from + "/96.png", to + "/xdpi/" + toFileName);
        copyFile(from + "/72.png", to + "/hdpi/" + toFileName);
        copyFile(from + "/48.png", to + "/mdpi/" + toFileName);

    }

    private static void copyFile(String from, String toFile) {
        File file = new File(toFile);
//        if (!file.exists()) {
//            if (!file.mkdirs()) {
//                return;
//            }
//        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                return;
            }
        }

        FileInputStream fileIs = null;
        FileOutputStream fileOs = null;
        try {
            fileIs = new FileInputStream(from);
            fileOs = new FileOutputStream(toFile);

            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = fileIs.read(bytes)) > 0) {
                fileOs.write(bytes, 0, len);
            }
            fileOs.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileIs != null) {
                try {
                    fileIs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOs != null) {
                try {
                    fileOs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        copyFile("D:\\test","D:\\test\\test2","ic_launch11.png");
    }
}
