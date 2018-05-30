package codingbo.plugin.forfaster.step1;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import groovy.lang.GroovyShell;
import kotlin.collections.ArraysKt;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bob on 18.5.29.
 */
public class Step1 extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
//        Messages.showMessageDialog("Hello World !", "forFaster1111", Messages.getInformationIcon());
        Step1InputDialog inputDialog = new Step1InputDialog();
//        inputDialog.setOkListener((from, to, toFileName) -> {
//            copyFile(from, to, toFileName);
//        });
        inputDialog.setOkListener(Step1::copyFile);
        inputDialog.setTitle("ForFaster_Step1");
        inputDialog.pack();
        inputDialog.setLocationRelativeTo(null);
        inputDialog.setVisible(true);
    }


    private static void copyFile(String from, String to, String toFileName) {
//        copyFile(from + "/192.png", to + "/xxxhdpi/" + toFileName);
//        copyFile(from + "/144.png", to + "/xxhdpi/" + toFileName);
//        copyFile(from + "/96.png", to + "/xhdpi/" + toFileName);
//        copyFile(from + "/72.png", to + "/hdpi/" + toFileName);
//        copyFile(from + "/48.png", to + "/mdpi/" + toFileName);

        HashMap<String, String> files = new HashMap<>();
        files.put("/192.png", "/xxxhdpi/");
        files.put("/144.png", "/xxhdpi/");
        files.put("/96.png", "/xhdpi/");
        files.put("/72.png", "/hdpi/");
        files.put("/48.png", "/mdpi/");

        files.forEach((key, value) -> copyFile(from + key, to + value + toFileName));
    }

    private static void copyFile(String from, String toFile) {
        File file = new File(toFile);
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
            int len;
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
        copyFile("D:\\test", "D:\\test\\test2", "ic_launch11.png");
    }
}
